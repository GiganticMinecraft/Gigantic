package click.seichi.gigantic.item.items.menu

import click.seichi.gigantic.extension.itemStackOf
import click.seichi.gigantic.extension.setDisplayName
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.menu.BookMenu
import click.seichi.gigantic.message.messages.MenuMessages
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
class PrevButton(private val menu: BookMenu) : Button {

    override fun toShownItemStack(player: Player): ItemStack? {
        if (!menu.hasPrevPage(player)) return null

        return itemStackOf(Material.LANTERN).apply {
            setDisplayName(player, MenuMessages.PREV_BUTTON)
        }
    }

    override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
        menu.prevPage(player)
        return true
    }

}