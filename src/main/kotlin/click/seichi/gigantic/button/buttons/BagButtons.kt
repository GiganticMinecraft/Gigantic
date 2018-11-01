package click.seichi.gigantic.button.buttons

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.button.Button
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.menu.menus.ProfileMenu
import click.seichi.gigantic.menu.menus.RaidBattleMenu
import click.seichi.gigantic.menu.menus.SpecialThanksMenu
import click.seichi.gigantic.message.messages.MenuMessages
import click.seichi.gigantic.player.LockedFunction
import click.seichi.gigantic.raid.RaidManager
import click.seichi.gigantic.sound.sounds.PlayerSounds
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
                setDisplayName(
                        MenuMessages.PROFILE.asSafety(player.wrappedLocale)
                )
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
            if (event.inventory.holder === ProfileMenu) return
            ProfileMenu.open(player)
        }

    }

    val RAID_BATTLE = object : Button {

        override fun getItemStack(player: Player): ItemStack? {
            if (!LockedFunction.RAID_BATTLE.isUnlocked(player)) return null
            return RaidManager.getBattleList().firstOrNull()?.boss?.head?.toItemStack()?.apply {
                setDisplayName(
                        MenuMessages.RAID_BOSS.asSafety(player.wrappedLocale)
                )
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
            if (!LockedFunction.RAID_BATTLE.isUnlocked(player)) return
            if (event.inventory.holder === RaidBattleMenu) return
            RaidBattleMenu.open(player)
        }

    }

    val AFK = object : Button {
        override fun getItemStack(player: Player): ItemStack? {
            return when (player.gameMode) {
                GameMode.SPECTATOR -> ItemStack(Material.POPPY, 1).apply {
                    setDisplayName(
                            MenuMessages.BACK_FROM_REST.asSafety(player.wrappedLocale)
                    )
                }
                else -> ItemStack(Material.DANDELION, 1).apply {
                    setDisplayName(
                            MenuMessages.REST.asSafety(player.wrappedLocale)
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
                setDisplayName(MenuMessages.SPECIAL_THANKS_TITLE.asSafety(player.wrappedLocale))
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

}