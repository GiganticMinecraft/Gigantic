package click.seichi.gigantic.item.items.menu

import click.seichi.gigantic.extension.setDisplayName
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.head.Head
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.MenuMessages
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
class BackButton(private val currentMenu: Menu, private val menu: Menu) : Button {

    override fun findItemStack(player: Player): ItemStack? {
        return Head.LEFT.toItemStack().apply {
            val title = menu.getTitle(player)
            setDisplayName(
                    MenuMessages.BACK_BUTTON(title).asSafety(player.wrappedLocale)
            )
        }
    }

    override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
        currentMenu.back(menu, player)
        return true
    }

}