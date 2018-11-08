package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.button.Button
import click.seichi.gigantic.button.buttons.menu.NextButton
import click.seichi.gigantic.button.buttons.menu.PrevButton
import click.seichi.gigantic.button.buttons.menu.TeleportButtons
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.menu.BookMenu
import click.seichi.gigantic.message.messages.menu.TeleportMessages
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

/**
 * @author tar0ss
 */
object TeleportToPlayerMenu : BookMenu() {

    override val size: Int
        get() = 54

    private const val numOfPlayerPerPage = 45


    private val nextButton = NextButton(this)
    private val prevButton = PrevButton(this)

    private fun getTeleportPlayerList(player: Player): List<Player> {
        return Bukkit.getOnlinePlayers().toMutableList().apply {
            remove(player)
        }
    }

    override fun getMaxPage(player: Player): Int {
        return Bukkit.getOnlinePlayers().size.div(numOfPlayerPerPage).plus(1)
    }

    override fun setItem(inventory: Inventory, player: Player, page: Int): Inventory {
        val playerList = getTeleportPlayerList(player)
        val start = (page - 1) * numOfPlayerPerPage
        val end = page * numOfPlayerPerPage
        (start until end)
                .filter { playerList.getOrNull(it) != null }
                .map { it % numOfPlayerPerPage to playerList[it] }
                .toMap()
                .forEach { index, to ->
                    inventory.setItem(index, TeleportButtons.TELEPORT_PLAYER(to).getItemStack(player))
                }
        inventory.setItem(numOfPlayerPerPage + 3, prevButton.getItemStack(player))
        inventory.setItem(numOfPlayerPerPage + 5, nextButton.getItemStack(player))

        return inventory
    }

    override fun getTitle(player: Player, page: Int): String {
        return "${ChatColor.BLACK}" +
                "${TeleportMessages.TELEPORT_TO_PLAYER_TITLE.asSafety(player.wrappedLocale)} $page/${getMaxPage(player)}"
    }

    override fun getButton(player: Player, page: Int, slot: Int): Button? {
        val playerList = getTeleportPlayerList(player)
        val index = (page - 1) * numOfPlayerPerPage + slot
        val to = playerList.getOrNull(index) ?: return null
        return when (slot) {
            numOfPlayerPerPage + 3 -> prevButton
            numOfPlayerPerPage + 5 -> nextButton
            else -> TeleportButtons.TELEPORT_PLAYER(to)
        }
    }

}