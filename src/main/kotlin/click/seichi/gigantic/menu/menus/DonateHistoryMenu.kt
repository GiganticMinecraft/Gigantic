package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.setItemAsync
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.item.items.menu.DonateHistoryButtons
import click.seichi.gigantic.item.items.menu.NextButton
import click.seichi.gigantic.item.items.menu.PrevButton
import click.seichi.gigantic.menu.BookMenu
import click.seichi.gigantic.message.messages.menu.DonateHistoryMessages
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

/**
 * @author tar0ss
 */
object DonateHistoryMenu : BookMenu() {

    override val size: Int
        get() = 54

    private const val numOfContentsPerPage = 45

    init {
        registerButton(numOfContentsPerPage + 3, PrevButton(this))
        registerButton(numOfContentsPerPage + 5, NextButton(this))
    }

    override fun onOpen(player: Player, page: Int, isFirst: Boolean) {}

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
                    inventory.setItemAsync(player, index, DonateHistoryButtons.DONATE(ticket))
                }
        getButtonMap().forEach { slot, button ->
            inventory.setItemAsync(player, slot, button)
        }
        return inventory
    }

    override fun getTitle(player: Player, page: Int): String {
        return "${DonateHistoryMessages.TITLE.asSafety(player.wrappedLocale)} $page/${getMaxPage(player)}"
    }

    override fun getButton(player: Player, page: Int, slot: Int): Button? {
        return getButtonMap()[slot]
    }
}