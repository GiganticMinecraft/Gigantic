package click.seichi.gigantic.item.items

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.head.Head
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.menu.menus.*
import click.seichi.gigantic.message.messages.BagMessages
import click.seichi.gigantic.message.messages.menu.EffectMenuMessages
import click.seichi.gigantic.message.messages.menu.ProfileMessages
import click.seichi.gigantic.message.messages.menu.SkillMenuMessages
import click.seichi.gigantic.message.messages.menu.SpellMenuMessages
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

/**
 * @author tar0ss
 */
object BagButtons {

    val PROFILE = object : Button {
        override fun findItemStack(player: Player): ItemStack? {
            return player.getHead().apply {
                setDisplayName(BagMessages.PROFILE.asSafety(player.wrappedLocale))
                val lore = mutableListOf<String>()
                lore.addAll(listOf(
                        ProfileMessages.UPDATE.asSafety(player.wrappedLocale),
                        ProfileMessages.PROFILE_LEVEL(player.wrappedLevel).asSafety(player.wrappedLocale),
                        ProfileMessages.PROFILE_EXP(player.wrappedLevel, player.wrappedExp).asSafety(player.wrappedLocale),
                        ProfileMessages.PROFILE_HEALTH(player.wrappedHealth, player.wrappedMaxHealth).asSafety(player.wrappedLocale)
                )
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
            PlayerSounds.TOGGLE.playOnly(player)
            player.updateBag()
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