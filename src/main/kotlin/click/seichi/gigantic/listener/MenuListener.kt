package click.seichi.gigantic.listener

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.isBeltSlot
import click.seichi.gigantic.menu.BookMenu
import click.seichi.gigantic.menu.Menu
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerInteractEvent

/**
 * @author tar0ss
 */
class MenuListener : Listener {

    val menuActionSet = setOf(
            InventoryAction.PICKUP_ALL,
            InventoryAction.PICKUP_SOME,
            InventoryAction.PICKUP_HALF,
            InventoryAction.PICKUP_ONE
    )

    // メニュー、ベルト、バッグに使用
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onInventoryClick(event: InventoryClickEvent) {
        val player = event.whoClicked as? org.bukkit.entity.Player ?: return
        if (player.gameMode != GameMode.SURVIVAL && player.gameMode != GameMode.SPECTATOR) return
        event.isCancelled = true
        if (!menuActionSet.contains(event.action)) return
        val holder = event.inventory.holder


        when (event.clickedInventory) {
            event.view.bottomInventory -> {
                // Belt
                if (event.isBeltSlot) player.getOrPut(Keys.BELT).findItem(event.slot)?.onClick(player, event)
                // Bag
                else player.getOrPut(Keys.BAG).getButton(event.slot)?.onClick(player, event)
            }
            else -> {
                // Menu
                when (holder) {
                    is BookMenu -> holder.getButton(player, event.slot)?.onClick(player, event)
                    is Menu -> holder.getButton(event.slot)?.onClick(player, event)
                }
            }
        }
    }

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        val player = event.player ?: return
        if (player.gameMode != GameMode.SURVIVAL) return
        val belt = player.getOrPut(Keys.BELT)
        if (event.action == Action.PHYSICAL) return
        val slot = player.inventory.heldItemSlot
        if (belt.findItem(slot)?.onInteract(player, event) == true) return
        if (belt.offHandItem?.onInteract(player, event) == true) return
    }

}