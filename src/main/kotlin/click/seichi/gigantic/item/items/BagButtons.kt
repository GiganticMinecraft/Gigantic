package click.seichi.gigantic.item.items

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.cache.key.DonateTicket
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.database.dao.DonateHistory
import click.seichi.gigantic.database.dao.User
import click.seichi.gigantic.database.table.DonateHistoryTable
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.head.Head
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.menu.menus.*
import click.seichi.gigantic.message.messages.BagMessages
import click.seichi.gigantic.message.messages.menu.*
import click.seichi.gigantic.player.Defaults
import click.seichi.gigantic.quest.Quest
import click.seichi.gigantic.relic.Relic
import click.seichi.gigantic.sound.sounds.PlayerSounds
import org.bukkit.ChatColor
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * @author tar0ss
 */
object BagButtons {

    val PROFILE = object : Button {
        override fun findItemStack(player: Player): ItemStack? {
            return player.getHead().apply {
                setDisplayName(BagMessages.PROFILE.asSafety(player.wrappedLocale))
                val lore = mutableListOf<String>()

                lore.add(when {
                    player.getOrPut(Keys.PROFILE_IS_UPDATING) -> ProfileMessages.UPDATING
                    else -> ProfileMessages.UPDATE
                }.asSafety(player.wrappedLocale))

                lore.add("")

                lore.addAll(listOf(
                        ProfileMessages.PROFILE_LEVEL(player.wrappedLevel),
                        ProfileMessages.PROFILE_EXP(player.wrappedLevel, player.wrappedExp),
                        ProfileMessages.PROFILE_HEALTH(player.wrappedHealth, player.wrappedMaxHealth),
                        ProfileMessages.PROFILE_VOTE_POINT(player.votePoint),
                        // TODO Pomme実装後に実装
//                        ProfileMessages.PROFILE_POMME(player.pomme),
                        ProfileMessages.PROFILE_DONATE_POINT(player.donatePoint)
                ).map { it.asSafety(player.wrappedLocale) }
                )
                if (Achievement.MANA_STONE.isGranted(player)) {
                    lore.add(ProfileMessages.PROFILE_MANA(player.mana, player.maxMana).asSafety(player.wrappedLocale))
                }

                player.manipulate(CatalogPlayerCache.APTITUDE) { aptitude ->
                    lore.addAll(listOf(
                            ProfileMessages.PROFILE_MAX_COMBO(player.maxCombo).asSafety(player.wrappedLocale),
                            *ProfileMessages.PROFILE_WILL_APTITUDE(player).map { it.asSafety(player.wrappedLocale) }.toTypedArray())
                    )
                }

                setLore(*lore.toTypedArray())

            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
            if (player.getOrPut(Keys.PROFILE_IS_UPDATING)) return
            PlayerSounds.TOGGLE.playOnly(player)
            player.offer(Keys.PROFILE_IS_UPDATING, true)
            player.updateBag()
            val uniqueId = player.uniqueId
            // 投票，ポム，寄付系のポイントをデータベースから取得後更新
            object : BukkitRunnable() {
                override fun run() {
                    var votePoint: Int? = null
                    var pomme: Int? = null
                    var donatePoint: Int? = null
                    transaction {
                        val user = User.findById(uniqueId)!!
                        votePoint = user.votePoint
                        pomme = user.pomme
                        donatePoint = user.donatePoint
                    }
                    object : BukkitRunnable() {
                        override fun run() {
                            if (!player.isValid) return

                            votePoint?.let {
                                player.force(Keys.VOTE_POINT, it)
                            }
                            pomme?.let {
                                player.force(Keys.POMME, it)
                            }
                            donatePoint?.let {
                                player.force(Keys.DONATE_POINT, it)
                            }
                            player.offer(Keys.PROFILE_IS_UPDATING, false)
                            player.updateBag()
                        }
                    }.runTaskLater(Gigantic.PLUGIN, Defaults.PROFILE_UPDATE_TIME * 20)
                }
            }.runTaskAsynchronously(Gigantic.PLUGIN)

        }

    }

    val DONATE_HISTORY = object : Button {

        override fun findItemStack(player: Player): ItemStack? {
            return ItemStack(Material.PAPER).apply {
                setDisplayName("${ChatColor.AQUA}${ChatColor.UNDERLINE}" +
                        DonateHistoryMessages.TITLE.asSafety(player.wrappedLocale))
                hideAllFlag()
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
            if (event.inventory.holder === DonateHistoryMenu) return
            //寄付履歴のリストを作成した後に表示
            // 一旦寄付履歴を全て削除
            player.offer(Keys.DONATE_TICKET_LIST, listOf())
            DonateHistoryMenu.open(player)
            val uniqueId = player.uniqueId

            //　非同期で寄付履歴リストを作成
            object : BukkitRunnable() {
                override fun run() {
                    val donateList: MutableList<DonateTicket> = mutableListOf()
                    transaction {
                        DonateHistory
                                .find { DonateHistoryTable.userId eq uniqueId }
                                .map { DonateTicket(it.createdAt, it.amount) }
                                .toList().let {
                                    donateList.addAll(it)
                                }
                    }
                    object : BukkitRunnable() {
                        override fun run() {
                            if (!player.isValid) return
                            //既に開いていなければ終了
                            val holder = player.openInventory.topInventory.holder
                            if (holder != DonateHistoryMenu) return
                            // 寄付履歴を保存
                            player.offer(Keys.DONATE_TICKET_LIST, donateList)
                            DonateHistoryMenu.reopen(player)
                        }
                    }.runTaskLater(Gigantic.PLUGIN, Defaults.DONATE_HISTORY_LOAD_TIME)
                }
            }.runTaskAsynchronously(Gigantic.PLUGIN)
        }

    }

    val SKILL = object : Button {

        override fun findItemStack(player: Player): ItemStack? {
            return ItemStack(Material.FLINT_AND_STEEL).apply {
                setDisplayName("${ChatColor.AQUA}${ChatColor.UNDERLINE}" +
                        SkillMenuMessages.TITLE.asSafety(player.wrappedLocale))
                hideAllFlag()
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
            if (event.inventory.holder === SkillMenu) return
            SkillMenu.open(player)
        }

    }

    val SPELL = object : Button {

        override fun findItemStack(player: Player): ItemStack? {
            if (!Achievement.MANA_STONE.isGranted(player)) return null
            return ItemStack(Material.LAPIS_LAZULI).apply {
                setDisplayName("${ChatColor.AQUA}${ChatColor.UNDERLINE}"
                        + SpellMenuMessages.TITLE.asSafety(player.wrappedLocale))
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
            if (!Achievement.MANA_STONE.isGranted(player)) return
            if (event.inventory.holder === SpellMenu) return
            SpellMenu.open(player)
        }

    }

    val AFK = object : Button {
        override fun findItemStack(player: Player): ItemStack? {
            return when (player.gameMode) {
                GameMode.SPECTATOR -> ItemStack(Material.POPPY, 1).apply {
                    setDisplayName(
                            BagMessages.BACK_FROM_REST.asSafety(player.wrappedLocale)
                    )
                }
                else -> ItemStack(Material.DANDELION, 1).apply {
                    setDisplayName(
                            BagMessages.REST.asSafety(player.wrappedLocale)
                    )
                }
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
            val afkLocation = player.getOrPut(Keys.AFK_LOCATION)
            when (player.gameMode) {
                GameMode.SURVIVAL -> {
                    player.gameMode = GameMode.SPECTATOR
                    player.offer(Keys.AFK_LOCATION, player.location)
                    // 見えなくなるバグのため
                    player.showPlayer(Gigantic.PLUGIN, player)
                    player.updateBag()
                }
                GameMode.SPECTATOR -> {
                    player.gameMode = GameMode.SURVIVAL
                    if (afkLocation != null) {
                        player.teleport(afkLocation)
                    }
                    player.closeInventory()
                    PlayerSounds.TELEPORT_AFK.play(player.location)
                }
                else -> {
                }
            }
        }

    }

    val SPECIAL_THANKS = object : Button {

        override fun findItemStack(player: Player): ItemStack? {
            return ItemStack(Material.MUSIC_DISC_13).apply {
                setDisplayName("${ChatColor.AQUA}${ChatColor.UNDERLINE}"
                        + BagMessages.SPECIAL_THANKS_TITLE.asSafety(player.wrappedLocale))
                clearLore()
                itemMeta = itemMeta.apply {
                    addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                    addItemFlags(ItemFlag.HIDE_ENCHANTS)
                    addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
                    addItemFlags(ItemFlag.HIDE_PLACED_ON)
                }
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
            if (event.inventory.holder === SpecialThanksMenu) return
            SpecialThanksMenu.open(player)
        }

    }

    val QUEST = object : Button {

        override fun findItemStack(player: Player): ItemStack? {
            if (!Achievement.QUEST.isGranted(player)) return null
            return ItemStack(Material.WRITABLE_BOOK).apply {
                if (Quest.getOrderedList(player).isEmpty()) {
                    setDisplayName("${ChatColor.GRAY}${ChatColor.UNDERLINE}"
                            + BagMessages.NO_QUEST.asSafety(player.wrappedLocale))
                } else {
                    setDisplayName("${ChatColor.AQUA}${ChatColor.UNDERLINE}"
                            + BagMessages.QUEST.asSafety(player.wrappedLocale))
                }
                clearLore()
                itemMeta = itemMeta.apply {
                    addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                    addItemFlags(ItemFlag.HIDE_ENCHANTS)
                    addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
                }
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
            if (Quest.getOrderedList(player).isEmpty()) return
            if (event.inventory.holder === QuestSelectMenu) return
            QuestSelectMenu.open(player)
        }

    }

    val RELIC = object : Button {

        override fun findItemStack(player: Player): ItemStack? {
            return Head.JEWELLERY_BOX.toItemStack().apply {
                if (Relic.getDroppedList(player).isEmpty()) {
                    setDisplayName("${ChatColor.GRAY}${ChatColor.UNDERLINE}"
                            + BagMessages.NO_RELIC.asSafety(player.wrappedLocale))
                } else {
                    setDisplayName("${ChatColor.AQUA}${ChatColor.UNDERLINE}"
                            + BagMessages.RELIC.asSafety(player.wrappedLocale))
                }
                clearLore()
                itemMeta = itemMeta.apply {
                    addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                    addItemFlags(ItemFlag.HIDE_ENCHANTS)
                    addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
                }
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
            if (Relic.getDroppedList(player).isEmpty()) return
            if (event.inventory.holder === RelicMenu) return
            RelicMenu.open(player)
        }

    }

    val TELEPORT_DOOR = object : Button {

        override fun findItemStack(player: Player): ItemStack? {
            return ItemStack(Material.DARK_OAK_DOOR).apply {
                setDisplayName("${ChatColor.AQUA}${ChatColor.UNDERLINE}"
                        + BagMessages.TELEPORT.asSafety(player.wrappedLocale))
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
            if (event.inventory.holder === TeleportMenu) return
            TeleportMenu.open(player)
        }

    }

    val TOOL_SWITCH_SETTING = object : Button {

        override fun findItemStack(player: Player): ItemStack? {
            return ItemStack(Material.LADDER).apply {
                setDisplayName("${ChatColor.AQUA}${ChatColor.UNDERLINE}"
                        + BagMessages.SWITCH_DETAIL.asSafety(player.wrappedLocale))
                setLore(BagMessages.SWITCH_DETAIL_LORE.asSafety(player.wrappedLocale))
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
            if (event.inventory.holder === ToolSwitchSettingMenu) return
            ToolSwitchSettingMenu.open(player)
        }

    }

    val EFFECT = object : Button {

        override fun findItemStack(player: Player): ItemStack? {
            if (!Achievement.EFFECT.isGranted(player)) return null
            return ItemStack(Material.ENCHANTING_TABLE).apply {
                setDisplayName("${ChatColor.AQUA}${ChatColor.UNDERLINE}"
                        + EffectMenuMessages.TITLE.asSafety(player.wrappedLocale))
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
            if (!Achievement.EFFECT.isGranted(player)) return
            if (event.inventory.holder === EffectMenu) return
            EffectMenu.open(player)
        }

    }

}