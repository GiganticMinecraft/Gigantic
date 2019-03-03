package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.item.items.menu.NextButton
import click.seichi.gigantic.item.items.menu.PrevButton
import click.seichi.gigantic.item.items.menu.WillButtons
import click.seichi.gigantic.menu.BookMenu
import click.seichi.gigantic.message.messages.WillMessages
import click.seichi.gigantic.will.Will
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

/**
 * @author tar0ss
 */
object WillMenu : BookMenu() {

    override val size: Int
        get() = 2 * 9

    private const val numOfContentsPerPage = 1 * 9

    private const val offset = 0

    init {
        registerButton(size - 6, PrevButton(this))
        registerButton(size - 4, NextButton(this))
    }

    override fun getMaxPage(player: Player): Int {
        return player.getOrPut(Keys.MENU_WILL_LIST).size.minus(1).div(numOfContentsPerPage).plus(1).coerceAtLeast(1)
    }

    override fun onOpen(player: Player, isFirst: Boolean) {
        player.offer(Keys.MENU_WILL_LIST,
                Will.values()
                        .filter { player.isProcessed(it) || player.hasAptitude(it) }
                        .toList()
        )
    }

    override fun setItem(inventory: Inventory, player: Player, page: Int): Inventory {
        val contentList = player.getOrPut(Keys.MENU_WILL_LIST)
        val start = (page - 1) * numOfContentsPerPage
        val end = page * numOfContentsPerPage
        (start until end)
                .filter { contentList.getOrNull(it) != null }
                .map { it % numOfContentsPerPage to contentList[it] }
                .toMap()
                .forEach { index, will ->
                    inventory.setItem(player, index + offset, WillButtons.WILL(will))
                }
        getButtonMap().forEach { slot, button ->
            inventory.setItem(player, slot, button)
        }
        return inventory
    }

    override fun getTitle(player: Player, page: Int): String {
        return "${WillMessages.WILL_MENU_TITLE.asSafety(player.wrappedLocale)} $page/${getMaxPage(player)}"
    }

    override fun getButton(player: Player, page: Int, slot: Int): Button? {
        val index = (page - 1) * numOfContentsPerPage + slot - offset
        return getButtonMap()[slot] ?: WillButtons.WILL(player.getOrPut(Keys.MENU_WILL_LIST)[index])
    }

}