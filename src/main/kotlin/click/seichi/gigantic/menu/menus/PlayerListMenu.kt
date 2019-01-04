package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.item.items.menu.NextButton
import click.seichi.gigantic.item.items.menu.PlayerListMenuButtons
import click.seichi.gigantic.item.items.menu.PrevButton
import click.seichi.gigantic.menu.BookMenu
import click.seichi.gigantic.menu.RefineItem
import click.seichi.gigantic.message.messages.menu.TeleportMessages
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

/**
 * @author tar0ss
 */
object PlayerListMenu : BookMenu() {

    override val size: Int
        get() = 54

    private const val numOfPlayerPerPage = 45


    private val nextButton = NextButton(this)
    private val prevButton = PrevButton(this)

    override fun getMaxPage(player: Player): Int {
        return Bukkit.getOnlinePlayers().size.minus(1).div(numOfPlayerPerPage).plus(1).coerceAtLeast(1)
    }

    override fun setItem(inventory: Inventory, player: Player, page: Int): Inventory {
        // オンラインのプレイヤーを取得
        val onlineUniqueIdSet = Bukkit.getOnlinePlayers().map { it.uniqueId }.toSet()
        // 絞り込み条件を確認
        val checkOnline = player.getOrPut(Keys.REFINE_ITEM_MAP[RefineItem.ONLINE]!!)

//        val start = (page - 1) * numOfPlayerPerPage
//        val end = page * numOfPlayerPerPage
//        (start until end)
//                .filter { playerList.getOrNull(it) != null }
//                .map { it % numOfPlayerPerPage to playerList[it] }
//                .toMap()
//                .forEach { index, to ->
//                    inventory.setItem(index, TeleportButtons.TELEPORT_PLAYER(to).findItemStack(player))
//                }
        inventory.setItem(numOfPlayerPerPage + 1, PlayerListMenuButtons.REFINE.findItemStack(player))
        inventory.setItem(numOfPlayerPerPage + 3, prevButton.findItemStack(player))
        inventory.setItem(numOfPlayerPerPage + 5, nextButton.findItemStack(player))

        return inventory
    }

    override fun getTitle(player: Player, page: Int): String {
        return "${TeleportMessages.TELEPORT_TO_PLAYER_TITLE.asSafety(player.wrappedLocale)} $page/${getMaxPage(player)}"
    }

    override fun getButton(player: Player, page: Int, slot: Int): Button? {
//        val playerList = getTeleportPlayerList(player)
//        val index = (page - 1) * numOfPlayerPerPage + slot
//        val to = playerList.getOrNull(index) ?: return null
        return when (slot) {
            numOfPlayerPerPage + 1 -> PlayerListMenuButtons.REFINE
            numOfPlayerPerPage + 3 -> prevButton
            numOfPlayerPerPage + 5 -> nextButton
            else -> null/*TeleportButtons.TELEPORT_PLAYER(to)*/
        }
    }
}