package click.seichi.gigantic.button.buttons

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.button.Button
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.menu.menus.QuestSelectMenu
import click.seichi.gigantic.menu.menus.SkillMenu
import click.seichi.gigantic.menu.menus.SpecialThanksMenu
import click.seichi.gigantic.menu.menus.SpellMenu
import click.seichi.gigantic.message.messages.BagMessages
import click.seichi.gigantic.message.messages.menu.ProfileMessages
import click.seichi.gigantic.message.messages.menu.SkillMenuMessages
import click.seichi.gigantic.message.messages.menu.SpellMenuMessages
import click.seichi.gigantic.quest.Quest
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
        override fun getItemStack(player: Player): ItemStack? {
            return player.getHead().apply {
                setDisplayName(BagMessages.PROFILE.asSafety(player.wrappedLocale))
                val level = player.find(CatalogPlayerCache.LEVEL) ?: return@apply
                val aptitude = player.find(CatalogPlayerCache.APTITUDE) ?: return@apply
                val health = player.find(CatalogPlayerCache.HEALTH) ?: return@apply
                val mineCombo = player.find(CatalogPlayerCache.MINE_COMBO) ?: return@apply
                val mana = player.find(CatalogPlayerCache.MANA) ?: return@apply
                val isUpdate = player.getOrPut(Keys.IS_UPDATE_PROFILE)
                val lore = mutableListOf<String>()
                if (isUpdate) {
                    lore.add(ProfileMessages.NEED_UPDATE.asSafety(player.wrappedLocale))
                } else {
                    lore.addAll(listOf(
                            ProfileMessages.PROFILE_LEVEL(level).asSafety(player.wrappedLocale),
                            ProfileMessages.PROFILE_EXP(level).asSafety(player.wrappedLocale),
                            ProfileMessages.PROFILE_HEALTH(health).asSafety(player.wrappedLocale)
                    )
                    )
                    if (Achievement.MANA_STONE.isGranted(player)) {
                        lore.add(ProfileMessages.PROFILE_MANA(mana).asSafety(player.wrappedLocale))
                    }

                    lore.addAll(listOf(
                            ProfileMessages.PROFILE_MAX_COMBO(mineCombo).asSafety(player.wrappedLocale),
                            *ProfileMessages.PROFILE_WILL_APTITUDE(aptitude).map { it.asSafety(player.wrappedLocale) }.toTypedArray())
                    )
                }
                setLore(*lore.toTypedArray())
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
            val isUpdate = player.getOrPut(Keys.IS_UPDATE_PROFILE)
            if (!isUpdate) return
            player.offer(Keys.IS_UPDATE_PROFILE, false)
            PlayerSounds.TOGGLE.playOnly(player)
            player.getOrPut(Keys.BAG).carry(player)
        }

    }

    val SKILL = object : Button {

        override fun getItemStack(player: Player): ItemStack? {
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

        override fun getItemStack(player: Player): ItemStack? {
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
        override fun getItemStack(player: Player): ItemStack? {
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
            val afkLocation = player.find(CatalogPlayerCache.AFK_LOCATION) ?: return
            when (player.gameMode) {
                GameMode.SURVIVAL -> {
                    player.gameMode = GameMode.SPECTATOR
                    afkLocation.saveLocation(player.location)
                    // 見えなくなるバグのため
                    player.showPlayer(Gigantic.PLUGIN, player)
                    player.getOrPut(Keys.BAG).carry(player)
                }
                GameMode.SPECTATOR -> {
                    player.gameMode = GameMode.SURVIVAL
                    player.teleport(afkLocation.getLocation())
                    player.getOrPut(Keys.BAG).carry(player)
                    PlayerSounds.TELEPORT_AFK.play(player.location)
                }
                else -> {
                }
            }
        }

    }

    val SPECIAL_THANKS = object : Button {

        override fun getItemStack(player: Player): ItemStack? {
            return ItemStack(Material.MUSIC_DISC_13).apply {
                setDisplayName("${ChatColor.AQUA}${ChatColor.UNDERLINE}"
                        + BagMessages.SPECIAL_THANKS_TITLE.asSafety(player.wrappedLocale))
                clearLore()
                itemMeta = itemMeta.apply {
                    addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                    addItemFlags(ItemFlag.HIDE_ENCHANTS)
                    addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
                }
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
            if (event.inventory.holder === SpecialThanksMenu) return
            SpecialThanksMenu.open(player)
        }

    }

    val QUEST = object : Button {

        override fun getItemStack(player: Player): ItemStack? {
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


    val SOUL_MONSTER = object : Button {

        override fun getItemStack(player: Player): ItemStack? {
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

}