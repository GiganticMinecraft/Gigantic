package click.seichi.gigantic.item.items.menu

import click.seichi.gigantic.extension.addLore
import click.seichi.gigantic.extension.clearLore
import click.seichi.gigantic.extension.setDisplayName
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.message.messages.menu.RelicMenuMessages
import click.seichi.gigantic.relic.Relic
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object RelicButtons {

    val RELIC: (Relic) -> Button = { relic ->
        object : Button {
            override fun findItemStack(player: Player): ItemStack? {
                return relic.getIcon().apply {
                    setDisplayName("${ChatColor.WHITE}${ChatColor.BOLD}" +
                            relic.getName(player.wrappedLocale))
                    clearLore()
                    addLore("${ChatColor.GREEN}" +
                            RelicMenuMessages.NUM.asSafety(player.wrappedLocale) +
                            " : " + relic.getDroppedNum(player))
                    // 説明文
                    relic.getLore(player.wrappedLocale)?.map { "${ChatColor.GRAY}" + it }
                            ?.let {
                                addLore(*it.toTypedArray())
                            }
                }
            }

            override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
                return false
            }

        }
    }

}