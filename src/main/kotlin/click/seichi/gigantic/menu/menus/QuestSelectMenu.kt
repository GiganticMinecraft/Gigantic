package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.item.items.menu.NextButton
import click.seichi.gigantic.item.items.menu.PrevButton
import click.seichi.gigantic.item.items.menu.QuestButtons
import click.seichi.gigantic.menu.BookMenu
import click.seichi.gigantic.message.messages.menu.QuestMenuMessages
import click.seichi.gigantic.quest.Quest
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

/**
 * @author tar0ss
 */
object QuestSelectMenu : BookMenu() {

    override val size: Int
        get() = 18

    private const val numOfContentsPerPage = 9

    private val nextButton = NextButton(this)
    private val prevButton = PrevButton(this)

    override fun getMaxPage(player: Player): Int {
        return Quest.getOrderedList(player).size.minus(1).div(numOfContentsPerPage).plus(1).coerceAtLeast(1)
    }

    override fun setItem(inventory: Inventory, player: Player, page: Int): Inventory {
        val questList = Quest.getOrderedList(player)
        val start = (page - 1) * numOfContentsPerPage
        val end = page * numOfContentsPerPage
        (start until end)
                .filter { questList.getOrNull(it) != null }
                .map { it % numOfContentsPerPage to questList[it] }
                .toMap()
                .forEach { index, quest ->
                    inventory.setItem(index, QuestButtons.QUEST(quest.getClient(player)
                            ?: return@forEach).toShownItemStack(player))
                }
        inventory.setItem(numOfContentsPerPage + 3, prevButton.toShownItemStack(player))
        inventory.setItem(numOfContentsPerPage + 5, nextButton.toShownItemStack(player))

        return inventory
    }

    override fun getTitle(player: Player, page: Int): String {
        return "${QuestMenuMessages.SELECT_MENU_TITLE.asSafety(player.wrappedLocale)} $page/${getMaxPage(player)}"
    }

    override fun getButton(player: Player, page: Int, slot: Int): Button? {
        val questList = Quest.getOrderedList(player)
        val index = (page - 1) * numOfContentsPerPage + slot
        val quest = questList.getOrNull(index) ?: return null
        return when (slot) {
            numOfContentsPerPage + 3 -> prevButton
            numOfContentsPerPage + 5 -> nextButton
            else -> QuestButtons.QUEST(quest.getClient(player) ?: return null)
        }
    }

}