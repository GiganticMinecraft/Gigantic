package click.seichi.gigantic.item.items.menu

import click.seichi.gigantic.extension.setDisplayName
import click.seichi.gigantic.extension.setLore
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.message.messages.menu.DonateHistoryMessages
import click.seichi.gigantic.player.DonateTicket
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object DonateHistoryButtons {

    val DONATE: (DonateTicket) -> Button = { ticket: DonateTicket ->
        object : Button {
            override fun toShownItemStack(player: Player): ItemStack? {
                val material = when (ticket.amount) {
                    in 1..99 -> Material.IRON_NUGGET
                    in 100..299 -> Material.IRON_INGOT
                    in 300..699 -> Material.IRON_BLOCK
                    in 700..1499 -> Material.GOLD_NUGGET
                    in 1500..2999 -> Material.GOLD_INGOT
                    in 3000..5999 -> Material.GOLD_BLOCK
                    in 6000..11999 -> Material.DIAMOND
                    in 12000..23999 -> Material.DIAMOND_BLOCK
                    else -> Material.NETHER_STAR
                }
                return ItemStack(material).apply {
                    setDisplayName(player, DonateHistoryMessages.DONATE(ticket.amount))
                    setLore(DonateHistoryMessages.DATE(ticket.date).asSafety(player.wrappedLocale))
                }
            }
        }
    }
}