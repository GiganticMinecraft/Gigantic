package click.seichi.gigantic.menu.menus.follow

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.item.items.menu.BackButton
import click.seichi.gigantic.item.items.menu.FollowSettingButtons
import click.seichi.gigantic.item.items.menu.NextButton
import click.seichi.gigantic.item.items.menu.PrevButton
import click.seichi.gigantic.menu.BookMenu
import click.seichi.gigantic.menu.menus.FollowSettingMenu
import click.seichi.gigantic.message.messages.menu.FollowSettingMenuMessages
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

/**
 * @author tar0ss
 */
object FollowMenu : BookMenu() {

    override val size: Int
        get() = 54

    private const val numOfPlayerPerPage = 45

    private val nextButton = NextButton(this)
    private val prevButton = PrevButton(this)
    private val backButton = BackButton(this, FollowSettingMenu)

    override fun getMaxPage(player: Player): Int {
        return player.getOrPut(Keys.MENU_PLAYER_LIST).size.minus(1).div(numOfPlayerPerPage).plus(1).coerceAtLeast(1)
    }

    override fun onOpen(player: Player, page: Int, isFirst: Boolean) {
        player.offer(
                Keys.MENU_PLAYER_LIST,
                Bukkit.getOnlinePlayers()
                        .filter { player != it }
                        .filter { player.isFollow(it.uniqueId) }
        )
    }

    override fun setItem(inventory: Inventory, player: Player, page: Int): Inventory {
        val playerList = player.getOrPut(Keys.MENU_PLAYER_LIST)
        val start = (page - 1) * numOfPlayerPerPage
        val end = page * numOfPlayerPerPage
        (start until end)
                .filter { playerList.getOrNull(it) != null }
                .map { it % numOfPlayerPerPage to playerList[it] }
                .toMap()
                .forEach { index, to ->
                    inventory.setItemAsync(player, index, FollowSettingButtons.FOLLOW_PLAYER(to))
                }
        inventory.setItemAsync(player, numOfPlayerPerPage, backButton)
        inventory.setItemAsync(player, numOfPlayerPerPage + 3, prevButton)
        inventory.setItemAsync(player, numOfPlayerPerPage + 5, nextButton)

        return inventory
    }

    override fun getTitle(player: Player, page: Int): String {
        return "${FollowSettingMenuMessages.FOLLOW.asSafety(player.wrappedLocale)} $page/${getMaxPage(player)}"
    }

    override fun getButton(player: Player, page: Int, slot: Int): Button? {
        val playerList = player.getOrPut(Keys.MENU_PLAYER_LIST)
        val index = (page - 1) * numOfPlayerPerPage + slot
        return when (slot) {
            numOfPlayerPerPage -> backButton
            numOfPlayerPerPage + 3 -> prevButton
            numOfPlayerPerPage + 5 -> nextButton
            else -> FollowSettingButtons.FOLLOW_PLAYER(playerList.getOrNull(index) ?: return null)
        }
    }

}