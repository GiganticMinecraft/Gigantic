package click.seichi.gigantic.menu.menus.teleport

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.offer
import click.seichi.gigantic.extension.setItem
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.item.items.menu.BackButton
import click.seichi.gigantic.item.items.menu.NextButton
import click.seichi.gigantic.item.items.menu.PrevButton
import click.seichi.gigantic.item.items.menu.TeleportButtons
import click.seichi.gigantic.menu.BookMenu
import click.seichi.gigantic.menu.menus.TeleportMenu
import click.seichi.gigantic.message.messages.menu.TeleportMessages
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

/**
 * @author tar0ss
 */
object TeleportToPlayerMenu : BookMenu() {

    override val size: Int
        get() = 54

    private const val numOfPlayerPerPage = 45

    init {
        registerButton(numOfPlayerPerPage, BackButton(this, TeleportMenu))
        registerButton(numOfPlayerPerPage + 3, PrevButton(this))
        registerButton(numOfPlayerPerPage + 5, NextButton(this))
    }

    override fun getMaxPage(player: Player): Int {
        return player.getOrPut(Keys.MENU_PLAYER_LIST).size.minus(1).div(numOfPlayerPerPage).plus(1).coerceAtLeast(1)
    }

    override fun onOpen(player: Player, page: Int, isFirst: Boolean) {
        player.offer(
                Keys.MENU_PLAYER_LIST,
                Bukkit.getOnlinePlayers().toMutableList().apply {
                    remove(player)
                }
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
                    inventory.setItem(index, TeleportButtons.TELEPORT_PLAYER(to).toShownItemStack(player))
                }
        getButtonMap().forEach { slot, button ->
            inventory.setItem(player, slot, button)
        }
        return inventory
    }

    override fun getTitle(player: Player, page: Int): String {
        return "${TeleportMessages.TELEPORT_TO_PLAYER_TITLE.asSafety(player.wrappedLocale)} $page/${getMaxPage(player)}"
    }

    override fun getButton(player: Player, page: Int, slot: Int): Button? {
        val playerList = player.getOrPut(Keys.MENU_PLAYER_LIST)
        val index = (page - 1) * numOfPlayerPerPage + slot
        return getButtonMap()[slot] ?: TeleportButtons.TELEPORT_PLAYER(playerList.getOrNull(index) ?: return null)
    }

}