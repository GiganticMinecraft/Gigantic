package click.seichi.gigantic.item.items.menu

import click.seichi.gigantic.extension.setDisplayName
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.message.messages.menu.RelicGeneratorMenuMessages
import click.seichi.gigantic.will.Will
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object RelicGeneratorButtons {

    val SELECT_ETHEL: (Will) -> Button = { will ->
        object : Button {
            override fun findItemStack(player: Player): ItemStack? {
                return ItemStack(will.material).apply {
                    setDisplayName("${ChatColor.RESET}" +
                            will.chatColor + "${ChatColor.BOLD}" +
                            will.getName(player.wrappedLocale) +
                            RelicGeneratorMenuMessages.SELECT_WILL.asSafety(player.wrappedLocale))
                }
            }

            override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
                //TODO implements
                return true
            }
        }
    }

}