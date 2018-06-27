package click.seichi.gigantic.menu.button

import click.seichi.gigantic.extension.setTitle
import click.seichi.gigantic.menu.Menu
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
class BackButton(private val currentMenu: Menu, private val menu: Menu) : Menu.Button {

    override fun getItemStack(player: Player): ItemStack? {
        return ItemStack(Material.LADDER).apply {
            setTitle("に戻る", prefix = "${menu.getTitle(player)}${ChatColor.RESET}${ChatColor.WHITE}")
        }
    }

    override fun onClick(player: Player, event: InventoryClickEvent) {
        currentMenu.back(menu, player)
    }

}