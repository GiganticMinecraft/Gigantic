package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.follow
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.offer
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.item.items.menu.BackButton
import click.seichi.gigantic.item.items.menu.FollowSettingMenuButtons
import click.seichi.gigantic.item.items.menu.NextButton
import click.seichi.gigantic.item.items.menu.PrevButton
import click.seichi.gigantic.menu.BookMenu
import click.seichi.gigantic.message.messages.menu.FollowSettingMenuMessages
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

/**
 * @author tar0ss
 */
object FollowerMenu : BookMenu() {

    override val size: Int
        get() = 54

    private const val numOfPlayerPerPage = 45

    private val nextButton = NextButton(this)
    private val prevButton = PrevButton(this)
    private val backButton = BackButton(this, FollowSettingMenu)

    override fun getMaxPage(player: Player): Int {
        return player.getOrPut(Keys.PLAYER_LIST).size.minus(1).div(numOfPlayerPerPage).plus(1).coerceAtLeast(1)
    }

    override fun init(player: Player) {
        player.offer(
                Keys.PLAYER_LIST,
                Bukkit.getOnlinePlayers()
                        .filter { player != it }
                        .filter { it.follow(player.uniqueId) }
        )
    }

    override fun setItem(inventory: Inventory, player: Player, page: Int): Inventory {
        val playerList = player.getOrPut(Keys.PLAYER_LIST)
        val start = (page - 1) * numOfPlayerPerPage
        val end = page * numOfPlayerPerPage
        (start until end)
                .filter { playerList.getOrNull(it) != null }
                .map { it % numOfPlayerPerPage to playerList[it] }
                .toMap()
                .forEach { index, to ->
                    inventory.setItem(index, FollowSettingMenuButtons.FOLLOWER_PLAYER(to).findItemStack(player))
                }
        inventory.setItem(numOfPlayerPerPage, backButton.findItemStack(player))
        inventory.setItem(numOfPlayerPerPage + 3, prevButton.findItemStack(player))
        inventory.setItem(numOfPlayerPerPage + 5, nextButton.findItemStack(player))

        return inventory
    }

    override fun getTitle(player: Player, page: Int): String {
        return "${FollowSettingMenuMessages.FOLLOWER.asSafety(player.wrappedLocale)} $page/${getMaxPage(player)}"
    }

    override fun getButton(player: Player, page: Int, slot: Int): Button? {
        val playerList = player.getOrPut(Keys.PLAYER_LIST)
        val index = (page - 1) * numOfPlayerPerPage + slot
        return when (slot) {
            numOfPlayerPerPage -> backButton
            numOfPlayerPerPage + 3 -> prevButton
            numOfPlayerPerPage + 5 -> nextButton
            else -> FollowSettingMenuButtons.FOLLOWER_PLAYER(playerList.getOrNull(index) ?: return null)
        }
    }

}