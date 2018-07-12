package click.seichi.gigantic.player.components

import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.menu.menus.MainMenu
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
sealed class DefaultInventory(private val menu: Menu) {
    object MAIN : DefaultInventory(MainMenu)

    fun forEachIndexed(player: Player, action: (Int, ItemStack?) -> Unit) {
        menu.getInventory(player).forEachIndexed(action)
    }

    fun getButton(player: Player, slot: Int) = menu.getButton(slot)

}