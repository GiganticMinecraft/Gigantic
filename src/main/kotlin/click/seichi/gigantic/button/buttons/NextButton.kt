package click.seichi.gigantic.button.buttons

import click.seichi.gigantic.button.Button
import click.seichi.gigantic.extension.setDisplayName
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.head.Head
import click.seichi.gigantic.menu.BookMenu
import click.seichi.gigantic.message.messages.MenuMessages
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
class NextButton(private val menu: BookMenu) : Button {
    override fun getItemStack(player: Player): ItemStack? {
        if (!menu.hasNextPage(player)) return null
        return Head.PUMPKIN_RIGHT_ARROW.toItemStack().apply {
            setDisplayName(
                    MenuMessages.NEXT_BUTTON.asSafety(player.wrappedLocale)
            )
        }
    }

    override fun onClick(player: Player, event: InventoryClickEvent) {
        menu.nextPage(player)
    }
}