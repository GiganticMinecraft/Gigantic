package click.seichi.gigantic.menu

import click.seichi.gigantic.button.Button
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.NOT_AVAILABLE
import click.seichi.gigantic.extension.find
import click.seichi.gigantic.extension.manipulate
import click.seichi.gigantic.sound.sounds.MenuSounds
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory

/**
 * @author tar0ss
 */
abstract class BookMenu : Menu() {

    abstract val maxPage: Int

    open val pageChangeSound = MenuSounds.PAGE_CHANGE

    override fun open(player: Player, playSound: Boolean) {
        open(player, 1, true, playSound)
    }

    fun changePage(player: Player, page: Int, playSound: Boolean = true) {
        if (page !in 1..maxPage) return
        open(player, page, false, playSound)
    }

    fun nextPage(player: Player, playSound: Boolean = true) {
        val nextPage = player.find(CatalogPlayerCache.MENU_DATA)?.page?.plus(1) ?: return
        if (nextPage !in 1..maxPage) return
        open(player, nextPage, false, playSound)
    }

    fun prevPage(player: Player, playSound: Boolean = true) {
        val prevPage = player.find(CatalogPlayerCache.MENU_DATA)?.page?.minus(1) ?: return
        if (prevPage !in 1..maxPage) return
        open(player, prevPage, false, playSound)
    }

    fun hasNextPage(player: Player): Boolean {
        val nextPage = player.find(CatalogPlayerCache.MENU_DATA)?.page?.plus(1) ?: return false
        return nextPage in 1..maxPage
    }

    fun hasPrevPage(player: Player): Boolean {
        val prevPage = player.find(CatalogPlayerCache.MENU_DATA)?.page?.minus(1) ?: return false
        return prevPage in 1..maxPage
    }

    private fun open(player: Player, page: Int, isFirstOpen: Boolean, playSound: Boolean) {
        player.manipulate(CatalogPlayerCache.MENU_DATA) {
            it.page = page
        }
        player.openInventory(createInventory(player))
        if (playSound) {
            if (isFirstOpen)
                openSound.playOnly(player)
            else
                pageChangeSound.playOnly(player)
        }
    }

    override fun createInventory(player: Player): Inventory {
        val page = player.find(CatalogPlayerCache.MENU_DATA)?.page ?: 0
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
        return getButton(player.find(CatalogPlayerCache.MENU_DATA)?.page ?: 1, slot)
    }

    protected abstract fun getButton(page: Int, slot: Int): Button?

}