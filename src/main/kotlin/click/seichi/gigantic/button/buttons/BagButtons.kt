package click.seichi.gigantic.button.buttons

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.button.Button
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.find
import click.seichi.gigantic.extension.getHead
import click.seichi.gigantic.extension.setDisplayName
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.menu.menus.ProfileMenu
import click.seichi.gigantic.menu.menus.RaidBattleMenu
import click.seichi.gigantic.message.messages.MenuMessages
import click.seichi.gigantic.player.LockedFunction
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
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
            return ItemStack(Material.EYE_OF_ENDER).apply {
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
            return ItemStack(Material.RED_ROSE, 1, 8).apply {
                when (player.gameMode) {
                    GameMode.SURVIVAL -> setDisplayName(
                            MenuMessages.REST.asSafety(player.wrappedLocale)
                    )
                    GameMode.SPECTATOR -> setDisplayName(
                            MenuMessages.BACK_FROM_REST.asSafety(player.wrappedLocale)
                    )
                    else -> {
                    }
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
                    player.find(Keys.BAG)?.carry(player)
                }
                GameMode.SPECTATOR -> {
                    player.gameMode = GameMode.SURVIVAL
                    player.teleport(afkLocation.getLocation())
                    player.find(Keys.BAG)?.carry(player)
                }
                else -> {
                }
            }
        }
    }

}