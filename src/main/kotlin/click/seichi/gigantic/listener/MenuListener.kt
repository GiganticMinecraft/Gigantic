package click.seichi.gigantic.listener

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.isBeltSlot
import click.seichi.gigantic.menu.BookMenu
import click.seichi.gigantic.menu.Menu
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

/**
 * @author tar0ss
 */
class MenuListener : Listener {

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val player = event.whoClicked as? org.bukkit.entity.Player ?: return
        if (player.gameMode != GameMode.SURVIVAL && player.gameMode != GameMode.SPECTATOR) return
        val holder = event.inventory.holder
        event.isCancelled = true

        when (event.clickedInventory) {
            event.view.bottomInventory -> {
                // Belt
                if (event.isBeltSlot) player.getOrPut(Keys.BELT).getButton(event.slot)?.onClick(player, event)
                // Bag
                else player.getOrPut(Keys.BAG).getButton(event.slot)?.onClick(player, event)
                return
            }
        }

        // Menu
        when (holder) {
            is BookMenu -> holder.getButton(player, event.slot)?.onClick(player, event)
            is Menu -> holder.getButton(event.slot)?.onClick(player, event)
        }
    }

}