package click.seichi.gigantic.menu

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.NOT_AVAILABLE
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.offer
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.sound.sounds.MenuSounds
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory

/**
 * @author tar0ss
 */
abstract class BookMenu : Menu() {

    open val pageChangeSound = MenuSounds.PAGE_CHANGE

    abstract fun getMaxPage(player: Player): Int

    override fun open(player: Player, playSound: Boolean) {
        open(player, 1, true, playSound)
    }

    fun changePage(player: Player, page: Int, playSound: Boolean = true) {
        if (page !in 1..getMaxPage(player)) return
        open(player, page, false, playSound)
    }

    fun nextPage(player: Player, playSound: Boolean = true) {
        val current = player.getOrPut(Keys.MENU_PAGE)
        val nextPage = current + 1
        if (nextPage !in 1..getMaxPage(player)) return
        player.offer(Keys.MENU_PAGE, nextPage)
        open(player, nextPage, false, playSound)
    }

    fun prevPage(player: Player, playSound: Boolean = true) {
        val current = player.getOrPut(Keys.MENU_PAGE)
        val prevPage = current - 1
        if (prevPage !in 1..getMaxPage(player)) return
        player.offer(Keys.MENU_PAGE, prevPage)
        open(player, prevPage, false, playSound)
    }

    fun hasNextPage(player: Player): Boolean {
        val current = player.getOrPut(Keys.MENU_PAGE)
        val nextPage = current + 1
        return nextPage in 1..getMaxPage(player)
    }

    fun hasPrevPage(player: Player): Boolean {
        val current = player.getOrPut(Keys.MENU_PAGE)
        val prevPage = current - 1
        return prevPage in 1..getMaxPage(player)
    }

    private fun open(player: Player, page: Int, isFirstOpen: Boolean, playSound: Boolean) {
        if (isFirstOpen) init(player)
        player.offer(Keys.MENU_PAGE, page)
        player.openInventory(createInventory(player))
        if (playSound) {
            if (isFirstOpen)
                openSound.playOnly(player)
            else
                pageChangeSound.playOnly(player)
        }
    }

    protected open fun init(player: Player) {}

    override fun createInventory(player: Player): Inventory {
        val page = player.getOrPut(Keys.MENU_PAGE)
        val title = getTitle(player, page)
        val inventory = when (type) {
            InventoryType.CHEST -> {
                Bukkit.createInventory(this, size, title)
            }
            else -> {
                Bukkit.createInventory(this, type, title)
            }
        }
        setItem(inventory, player, page)
        return inventory
    }

    override fun getTitle(player: Player): String {
        return String.NOT_AVAILABLE
    }

    abstract fun setItem(inventory: Inventory, player: Player, page: Int): Inventory

    abstract fun getTitle(player: Player, page: Int): String

    fun getButton(player: Player, slot: Int): Button? {
        return getButton(player, player.getOrPut(Keys.MENU_PAGE), slot)
    }

    protected abstract fun getButton(player: Player, page: Int, slot: Int): Button?

}