package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.setDisplayName
import click.seichi.gigantic.extension.setLore
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.item.items.menu.NextButton
import click.seichi.gigantic.item.items.menu.PrevButton
import click.seichi.gigantic.menu.BookMenu
import click.seichi.gigantic.message.messages.menu.DonateHistoryMessages
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object DonateHistoryMenu : BookMenu() {

    override val size: Int
        get() = 54

    private const val numOfContentsPerPage = 45

    private val nextButton = NextButton(this)
    private val prevButton = PrevButton(this)

    override fun getMaxPage(player: Player): Int {
        return player.getOrPut(Keys.DONATE_TICKET_LIST).size.minus(1).div(numOfContentsPerPage).plus(1).coerceAtLeast(1)
    }

    override fun setItem(inventory: Inventory, player: Player, page: Int): Inventory {
        val ticketList = player.getOrPut(Keys.DONATE_TICKET_LIST)
        val start = (page - 1) * numOfContentsPerPage
        val end = page * numOfContentsPerPage
        (start until end)
                .filter { ticketList.getOrNull(it) != null }
                .map { it % numOfContentsPerPage to ticketList[it] }
                .toMap()
                .forEach { index, ticket ->
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
                    val itemStack = ItemStack(material).apply {
                        setDisplayName("${ChatColor.YELLOW}" +
                                "${ChatColor.BOLD}" +
                                "${ticket.amount}円")
                        setLore("${ChatColor.WHITE}" +
                                ticket.date.toString("yyyy/MM/dd kk:mm:ss"))
                    }
                    inventory.setItem(index, itemStack)
                }
        inventory.setItem(numOfContentsPerPage + 3, prevButton.findItemStack(player))
        inventory.setItem(numOfContentsPerPage + 5, nextButton.findItemStack(player))

        return inventory
    }

    override fun getTitle(player: Player, page: Int): String {
        return "${DonateHistoryMessages.TITLE.asSafety(player.wrappedLocale)} $page/${getMaxPage(player)}"
    }

    override fun getButton(player: Player, page: Int, slot: Int): Button? {
        return when (slot) {
            numOfContentsPerPage + 3 -> prevButton
            numOfContentsPerPage + 5 -> nextButton
            else -> null
        }
    }
}