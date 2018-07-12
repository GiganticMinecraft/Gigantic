package click.seichi.gigantic.listener

import click.seichi.gigantic.extension.gPlayer
import click.seichi.gigantic.extension.isBeltSlot
import click.seichi.gigantic.menu.Menu
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
        val gPlayer = player.gPlayer ?: return
        val holder = event.inventory.holder

        if (holder is Menu) {
            event.isCancelled = true
            if (event.clickedInventory == event.view.topInventory) {
                holder.getButton(event.slot)?.onClick(player, event)
            } else if (event.clickedInventory == event.view.bottomInventory) {
                if (event.isBeltSlot) {
                    gPlayer.belt.getHookedItem(event.slot)?.onClick(player, event)
                } else {
                    gPlayer.defaultInventory.getButton(player, event.slot)?.onClick(player, event)
                }
            }
        } else if (player.inventory.holder == holder) {
            // Eで開くインベントリの場合
            event.isCancelled = true
            if (event.isBeltSlot) {
                gPlayer.belt.getHookedItem(event.slot)?.onClick(player, event)
            } else {
                gPlayer.defaultInventory.getButton(player, event.slot)?.onClick(player, event)
            }
        }
    }

}