package click.seichi.gigantic.listener

import click.seichi.gigantic.extension.openMenu
import click.seichi.gigantic.menu.Menu
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerInteractEvent

/**
 * @author tar0ss
 */
class MenuListener : Listener {

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {

        val player = event.whoClicked as? Player ?: return
        val menu = event.inventory.holder as? Menu ?: return

        event.isCancelled = true

        if (event.clickedInventory == event.view.topInventory) {
            menu.getButton(player, event.slot)?.onClick(player, event)
        }
    }

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        event.openMenu()
    }
}