package click.seichi.gigantic.item.items

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.database.dao.DonateHistory
import click.seichi.gigantic.database.dao.user.User
import click.seichi.gigantic.database.table.DonateHistoryTable
import click.seichi.gigantic.event.events.SenseEvent
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.menu.menus.*
import click.seichi.gigantic.menu.menus.shop.DonateGiftEffectShopMenu
import click.seichi.gigantic.menu.menus.shop.VoteGiftEffectShopMenu
import click.seichi.gigantic.message.messages.BagMessages
import click.seichi.gigantic.message.messages.MenuMessages
import click.seichi.gigantic.message.messages.WillMessages
import click.seichi.gigantic.message.messages.menu.*
import click.seichi.gigantic.player.Defaults
import click.seichi.gigantic.player.DonateTicket
import click.seichi.gigantic.quest.Quest
import click.seichi.gigantic.sound.sounds.PlayerSounds
import click.seichi.gigantic.sound.sounds.WillSpiritSounds
import click.seichi.gigantic.util.Random
import click.seichi.gigantic.will.Will
import click.seichi.gigantic.will.WillGrade
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import org.jetbrains.exposed.sql.transactions.transaction
import java.math.BigDecimal
import java.util.*

/**
 * @author tar0ss
 */
object BagButtons {

    val PROFILE = object : Button {
        override fun toShownItemStack(player: Player): ItemStack? {
            return player.getHead().apply {
                setDisplayName(BagMessages.PROFILE.asSafety(player.wrappedLocale))
                val lore = mutableListOf<String>()

                lore.add(when {
                    isCoolTime(player) -> ProfileMessages.UPDATING
                    else -> ProfileMessages.UPDATE
                }.asSafety(player.wrappedLocale))

                lore.add("")

                lore.addAll(listOf(
                        ProfileMessages.PROFILE_LEVEL(player.wrappedLevel),
                        ProfileMessages.PROFILE_EXP(player.wrappedLevel, player.wrappedExp),
                        ProfileMessages.PROFILE_VOTE_POINT(player.vote),
                        // TODO Pomme実装後に実装
//                        ProfileMessages.PROFILE_POMME(player.pomme),
                        ProfileMessages.PROFILE_DONATION(player.donation)
                ).map { it.asSafety(player.wrappedLocale) }
                )
                if (Achievement.MANA_STONE.isGranted(player)) {
                    lore.add(ProfileMessages.PROFILE_MANA(player.mana.coerceAtLeast(BigDecimal.ZERO), player.maxMana).asSafety(player.wrappedLocale))
                }

                lore.add(
                        ProfileMessages.PROFILE_MAX_COMBO(player.maxCombo).asSafety(player.wrappedLocale)
                )

                if (Achievement.FIRST_RELIC.isGranted(player)) {
                    lore.add(ProfileMessages.PROFILE_RELIC_NUM(player.relics).asSafety(player.wrappedLocale))
                    lore.add(ProfileMessages.PROFILE_RELIC_COMPLETE_RATIO(player.relicTypes).asSafety(player.wrappedLocale))
                }

                if (Will.values().filter { it.grade == WillGrade.BASIC }
                                .firstOrNull { player.hasAptitude(it) } != null) {
                    lore.addAll(listOf(
                            *ProfileMessages.PROFILE_WILL_APTITUDE_BASIC(player).map { it.asSafety(player.wrappedLocale) }.toTypedArray()
                    ))
                }
                if (Will.values().filter { it.grade == WillGrade.ADVANCED }
                                .firstOrNull { player.hasAptitude(it) } != null) {
                    lore.addAll(listOf(
                            *ProfileMessages.PROFILE_WILL_APTITUDE_ADVANCED(player).map { it.asSafety(player.wrappedLocale) }.toTypedArray()
                    ))
                }
                if (Will.values().filter { it.grade == WillGrade.SPECIAL }
                                .firstOrNull { player.hasAptitude(it) } != null) {
                    lore.addAll(listOf(
                            *ProfileMessages.PROFILE_WILL_APTITUDE_SPECIAL(player).map { it.asSafety(player.wrappedLocale) }.toTypedArray()
                    ))
                }

                setLore(*lore.toTypedArray())

            }
        }

        private val coolTimeSet = mutableSetOf<UUID>()

        fun isCoolTime(player: Player) = coolTimeSet.contains(player.uniqueId)

        fun setCoolTime(uniqueId: UUID, isCoolTime: Boolean) {
            if (isCoolTime) {
                coolTimeSet.add(uniqueId)
            } else {
                coolTimeSet.remove(uniqueId)
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            if (isCoolTime(player)) return true
            PlayerSounds.TOGGLE.playOnly(player)
            val uniqueId = player.uniqueId
            setCoolTime(uniqueId, true)
            player.updateBag()
            // 投票，ポム，寄付系のポイントをデータベースから取得後更新
            object : BukkitRunnable() {
                override fun run() {
                    var votePoint: Int? = null
                    var pomme: Int? = null
                    var donation: Int? = null
                    transaction {
                        val user = User.findById(uniqueId)!!
                        votePoint = user.vote
                        pomme = user.pomme
                        donation = user.donation
                    }
                    object : BukkitRunnable() {
                        override fun run() {
                            setCoolTime(uniqueId, false)
                            if (!player.isValid) return
                            votePoint?.let {
                                player.force(Keys.VOTE, it)
                            }
                            pomme?.let {
                                player.force(Keys.POMME, it)
                            }
                            donation?.let {
                                player.force(Keys.DONATION, it)
                            }
                            player.updateBag()
                        }
                    }.runTaskLater(Gigantic.PLUGIN, Defaults.PROFILE_UPDATE_TIME * 20)
                }
            }.runTaskAsynchronously(Gigantic.PLUGIN)
            return true
        }

    }

    val DONATE_HISTORY = object : Button {

        override fun toShownItemStack(player: Player): ItemStack? {
            return itemStackOf(Material.PAPER) {
                setDisplayName("${ChatColor.AQUA}${ChatColor.UNDERLINE}" +
                        DonateHistoryMessages.TITLE.asSafety(player.wrappedLocale))
                hideAllFlag()
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            if (event.inventory.holder === DonateHistoryMenu) return false
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
                                .notForUpdate()
                                .map { DonateTicket(it.createdAt, it.amount) }
                                .toList()
                                .sortedByDescending {
                                    it.date.millis
                                }.let {
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
                    }.runTaskLater(Gigantic.PLUGIN, Defaults.DONATE_HISTORY_LOAD_TIME * 20)
                }
            }.runTaskAsynchronously(Gigantic.PLUGIN)
            return true
        }

    }

    val SKILL = object : Button {

        override fun toShownItemStack(player: Player): ItemStack? {
            return itemStackOf(Material.BONE) {
                setDisplayName("${ChatColor.AQUA}${ChatColor.UNDERLINE}" +
                        SkillMenuMessages.TITLE.asSafety(player.wrappedLocale))
                hideAllFlag()
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            if (event.inventory.holder === SkillMenu) return false
            SkillMenu.open(player)
            return true
        }

    }

    val SPELL = object : Button {

        override fun toShownItemStack(player: Player): ItemStack? {
            if (!Achievement.MANA_STONE.isGranted(player)) return null
            return itemStackOf(Material.LAPIS_LAZULI) {
                setDisplayName("${ChatColor.AQUA}${ChatColor.UNDERLINE}"
                        + SpellMenuMessages.TITLE.asSafety(player.wrappedLocale))
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            if (!Achievement.MANA_STONE.isGranted(player)) return false
            if (event.inventory.holder === SpellMenu) return false
            SpellMenu.open(player)
            return true
        }

    }

    val AFK = object : Button {
        override fun toShownItemStack(player: Player): ItemStack? {
            return when (player.gameMode) {
                GameMode.SPECTATOR -> itemStackOf(Material.POPPY) {
                    setDisplayName(
                            BagMessages.BACK_FROM_REST.asSafety(player.wrappedLocale)
                    )
                }
                else -> itemStackOf(Material.DANDELION) {
                    setDisplayName(
                            BagMessages.REST.asSafety(player.wrappedLocale)
                    )
                }
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            val afkLocation = player.getOrPut(Keys.AFK_LOCATION)
            when (player.gameMode) {
                GameMode.SURVIVAL -> {
                    player.gameMode = GameMode.SPECTATOR
                    player.offer(Keys.AFK_LOCATION, player.location)
                    // 見えなくなるバグのため
                    player.showPlayer(Gigantic.PLUGIN, player)
                    player.updateBag()
                    PlayerSounds.TELEPORT_AFK.play(player.location)
                    return true
                }
                GameMode.SPECTATOR -> {
                    player.gameMode = GameMode.SURVIVAL
                    if (afkLocation != null) {
                        player.teleportSafely(afkLocation)
                    }
                    player.closeInventory()
                    player.updateBag()
                    PlayerSounds.TELEPORT_AFK.play(player.location)
                    return true
                }
                else -> {
                    return false
                }
            }
        }

    }

    val SPECIAL_THANKS = object : Button {

        override fun toShownItemStack(player: Player): ItemStack? {
            return itemStackOf(Material.MUSIC_DISC_13) {
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

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            if (event.inventory.holder === SpecialThanksMenu) return false
            SpecialThanksMenu.open(player)
            return true
        }

    }

    val QUEST = object : Button {

        override fun toShownItemStack(player: Player): ItemStack? {
            if (!Achievement.QUEST.isGranted(player)) return null
            return itemStackOf(Material.WRITABLE_BOOK) {
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

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            if (Quest.getOrderedList(player).isEmpty()) return false
            if (event.inventory.holder === QuestSelectMenu) return false
            QuestSelectMenu.open(player)
            return true
        }

    }

    val RELIC = object : Button {

        override fun toShownItemStack(player: Player): ItemStack? {
            if (!Achievement.FIRST_RELIC.isGranted(player)) return null
            return itemStackOf(Material.ENDER_CHEST) {
                setDisplayName("${ChatColor.AQUA}${ChatColor.UNDERLINE}"
                        + BagMessages.RELIC.asSafety(player.wrappedLocale))
                clearLore()
                itemMeta = itemMeta.apply {
                    addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                    addItemFlags(ItemFlag.HIDE_ENCHANTS)
                    addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
                }
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            if (!Achievement.FIRST_RELIC.isGranted(player)) return false
            if (event.inventory.holder === RelicMenu) return false
            RelicMenu.open(player)
            return true
        }

    }

    val TELEPORT_DOOR = object : Button {

        override fun toShownItemStack(player: Player): ItemStack? {
            return itemStackOf(Material.DARK_OAK_DOOR) {
                setDisplayName("${ChatColor.AQUA}${ChatColor.UNDERLINE}"
                        + BagMessages.TELEPORT.asSafety(player.wrappedLocale))
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            if (event.inventory.holder === TeleportMenu) return false
            TeleportMenu.open(player)
            return true
        }

    }


    val EFFECT = object : Button {

        override fun toShownItemStack(player: Player): ItemStack? {
            return itemStackOf(Material.ENCHANTING_TABLE) {
                setDisplayName("${ChatColor.AQUA}${ChatColor.UNDERLINE}"
                        + EffectMenuMessages.TITLE.asSafety(player.wrappedLocale))
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            if (event.inventory.holder === EffectMenu) return false
            EffectMenu.open(player)
            return true
        }

    }

    val SHOP = object : Button {

        override fun toShownItemStack(player: Player): ItemStack? {
            return itemStackOf(Material.END_CRYSTAL) {
                setDisplayName(player, ShopMessages.SHOP)
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            if (event.inventory.holder === DonateGiftEffectShopMenu) return false
            if (event.inventory.holder === VoteGiftEffectShopMenu) return false
            DonateGiftEffectShopMenu.open(player)
            return true
        }

    }

    val FOLLOW_SETTING = object : Button {

        override fun toShownItemStack(player: Player): ItemStack? {
            return itemStackOf(Material.ROSE_BUSH) {
                setDisplayName("${ChatColor.AQUA}${ChatColor.UNDERLINE}"
                        + FollowSettingMenuMessages.TITLE.asSafety(player.wrappedLocale))
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            if (event.inventory.holder === FollowSettingMenu) return false
            FollowSettingMenu.open(player)
            return true
        }

    }

    val RELIC_GENERATOR = object : Button {

        override fun toShownItemStack(player: Player): ItemStack? {
            if (!Achievement.FIRST_WILL.isGranted(player)) return null
            return itemStackOf(Material.END_PORTAL_FRAME) {
                setDisplayName("${ChatColor.AQUA}${ChatColor.UNDERLINE}"
                        + RelicGeneratorMenuMessages.TITLE.asSafety(player.wrappedLocale))
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            if (!Achievement.FIRST_WILL.isGranted(player)) return false
            if (event.inventory.holder === RelicGeneratorMenu) return false
            RelicGeneratorMenu.open(player)
            return true
        }

    }

    val VOTE_BONUS = object : Button {

        override fun toShownItemStack(player: Player): ItemStack? {
            return itemStackOf(Material.GOLDEN_APPLE) {
                setDisplayName("${ChatColor.AQUA}${ChatColor.UNDERLINE}"
                        + BagMessages.VOTE_BONUS.asSafety(player.wrappedLocale))
                clearLore()

                // もし先ほど獲得した特典があればそれを表示
                player.getOrPut(Keys.GIVEN_WILL_SET)?.forEach { will ->
                    addLore(WillMessages.GET_ETHEL_TEXT(will, Defaults.VOTE_BONUS_ETHEL).asSafety(player.wrappedLocale))
                }

                // クリックしたときの動作説明
                val givenBonus = player.getOrPut(Keys.GIVEN_VOTE_BONUS)
                val voteNum = player.vote
                val bonus = voteNum.minus(givenBonus)
                if (bonus > 0) {
                    addLore("${ChatColor.GREEN}${ChatColor.UNDERLINE}${ChatColor.BOLD}"
                            + BagMessages.TAKE_BONUS.asSafety(player.wrappedLocale)
                            + "($bonus)")
                    setEnchanted(true)
                } else {
                    addLore("${ChatColor.GRAY}${ChatColor.UNDERLINE}${ChatColor.BOLD}"
                            + BagMessages.NO_BONUS.asSafety(player.wrappedLocale))
                    addLore("${ChatColor.RED}"
                            + BagMessages.TO_TAKE_BONUS.asSafety(player.wrappedLocale))
                }
                addLore(MenuMessages.LINE)
                // 説明文
                addLore(BagMessages.VOTE_BONUS_DESCRIPTION.asSafety(player.wrappedLocale))
                addLore(*BagMessages.VOTE_BONUS_FOR_ALL_PLAYER.map { it.asSafety(player.wrappedLocale) }.toTypedArray())
                if (Achievement.FIRST_WILL.isGranted(player)) {
                    addLore(*BagMessages.VOTE_BONUS_FOR_BASIC_WILL.map { it.asSafety(player.wrappedLocale) }.toTypedArray())
                } else {
                    addLore(BagMessages.VOTE_BONUS_FOR_BASIC_WILL_HIDE.asSafety(player.wrappedLocale))
                }
                if (Achievement.FIRST_ADVANCED_WILL.isGranted(player)) {
                    addLore(*BagMessages.VOTE_BONUS_FOR_ADVANCED_WILL.map { it.asSafety(player.wrappedLocale) }.toTypedArray())
                } else {
                    addLore(BagMessages.VOTE_BONUS_FOR_ADVANCED_WILL_HIDE.asSafety(player.wrappedLocale))
                }
                if (Achievement.FIRST_WILL.isGranted(player) || Achievement.FIRST_ADVANCED_WILL.isGranted(player)) {
                    addLore("")
                    addLore(*BagMessages.VOTE_BONUS_CAUTION.map { it.asSafety(player.wrappedLocale) }.toTypedArray())
                }
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            val givenBonus = player.getOrPut(Keys.GIVEN_VOTE_BONUS)
            val voteNum = player.vote
            val bonus = voteNum.minus(givenBonus)
            // ボーナスが無ければ終了
            if (bonus <= 0) return false
            // givenBonusを増やす
            player.offer(Keys.GIVEN_VOTE_BONUS, givenBonus + 1)

            val givenWillSet = mutableSetOf<Will>()
            // 特典付与処理
            // フリー特典は自動付与なので付ける必要なし．
            // 通常意志特典
            if (Achievement.FIRST_WILL.isGranted(player)) {
                val willSet = Will.values()
                        .filter { it.grade == WillGrade.BASIC }
                        .filter { player.hasAptitude(it) }
                        .shuffled(Random.generator)
                        .take(Defaults.VOTE_BONUS_BASIC_WILL_NUM)
                        .toSet()
                givenWillSet.addAll(willSet)
                willSet.forEach {
                    it.addEthel(player, Defaults.VOTE_BONUS_ETHEL)
                    Bukkit.getPluginManager().callEvent(SenseEvent(it, player, Defaults.VOTE_BONUS_ETHEL))
                }
            }
            // 高度意志特典
            if (Achievement.FIRST_ADVANCED_WILL.isGranted(player)) {
                val willSet = Will.values()
                        .filter { it.grade == WillGrade.ADVANCED }
                        .filter { player.hasAptitude(it) }
                        .shuffled(Random.generator)
                        .take(Defaults.VOTE_BONUS_ADVANCED_WILL_NUM)
                        .toSet()
                givenWillSet.addAll(willSet)
                willSet.forEach {
                    it.addEthel(player, Defaults.VOTE_BONUS_ETHEL)
                    Bukkit.getPluginManager().callEvent(SenseEvent(it, player, Defaults.VOTE_BONUS_ETHEL))
                }
            }
            player.offer(Keys.GIVEN_WILL_SET, givenWillSet)
            object : BukkitRunnable() {
                override fun run() {
                    if (!player.isValid) return
                    player.offer(Keys.GIVEN_WILL_SET, null)
                }
            }.runTaskLater(Gigantic.PLUGIN, 1L)
            player.updateBag()
            player.updateSideBar()
            WillSpiritSounds.SENSED.playOnly(player)
            return true
        }

    }

    val SETTINGS = object : Button {

        override fun toShownItemStack(player: Player): ItemStack? {
            return itemStackOf(Material.TRIPWIRE_HOOK) {
                setDisplayName("${ChatColor.AQUA}${ChatColor.UNDERLINE}"
                        + SettingMenuMessages.TITLE.asSafety(player.wrappedLocale))
                clearLore()
                itemMeta = itemMeta.apply {
                    addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                    addItemFlags(ItemFlag.HIDE_ENCHANTS)
                    addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
                }
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            if (event.inventory.holder === SettingMenu) return false
            SettingMenu.open(player)
            return true
        }

    }

    val WILL = object : Button {

        override fun toShownItemStack(player: Player): ItemStack? {
            if (!Achievement.FIRST_PRE_SENSE.isGranted(player)) return null
            return itemStackOf(Material.HEART_OF_THE_SEA) {
                setDisplayName("${ChatColor.AQUA}${ChatColor.UNDERLINE}"
                        + BagMessages.WILL.asSafety(player.wrappedLocale))
                clearLore()
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            if (!Achievement.FIRST_PRE_SENSE.isGranted(player)) return false
            if (event.inventory.holder === WillMenu) return false
            WillMenu.open(player)
            return true
        }

    }

    val TEXTURE = object : Button {
        override fun toShownItemStack(player: Player): ItemStack? {
            return (if (player.isNormalTexture)
                itemStackOf(Material.GLISTERING_MELON_SLICE) {
                    setDisplayName(player, BagMessages.NORMAL_TEXTURE)
                }
            else itemStackOf(Material.MELON_SLICE) {
                setDisplayName(player, BagMessages.LIGHT_TEXTURE)
            }).apply {
                setLore(*BagMessages.SERVER_RESOURCE_PACK.map { it.asSafety(player.wrappedLocale) }.toTypedArray())
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            player.transform(Keys.IS_NORMAL_TEXTURE) {
                !it
            }
            player.updateBag()
            PlayerSounds.TOGGLE.playOnly(player)

            if (player.isNormalTexture)
                player.setResourcePack(Gigantic.NORMAL_RESOURCE_PACK_URL)
            else
                player.setResourcePack(Gigantic.LIGHT_RESOURCE_PACK_URL)

            return true
        }

    }


    val RANKING = object : Button {

        override fun toShownItemStack(player: Player): ItemStack? {
            return itemStackOf(Material.FIREWORK_ROCKET) {
                setDisplayName(player, BagMessages.RANKING)
                sublime()
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            if (event.inventory.holder === RankingMenu) return false
            RankingMenu.open(player)
            return true
        }

    }
}