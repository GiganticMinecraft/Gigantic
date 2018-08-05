package click.seichi.gigantic.listener

import click.seichi.gigantic.data.keys.Keys
import click.seichi.gigantic.extension.find
import click.seichi.gigantic.extension.isBeltSlot
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

        when (holder) {
            is Menu -> {
                when (event.clickedInventory) {
                    event.view.topInventory -> holder.getButton(event.slot)?.onClick(player, event)
                    event.view.bottomInventory -> {
                        if (event.isBeltSlot) player.find(Keys.BELT)?.getButton(event.slot)?.onClick(player, event)
                        else player.find(Keys.BAG)?.getButton(event.slot)?.onClick(player, event)
                    }
                }
            }
            player.inventory.holder -> {
                if (event.isBeltSlot) player.find(Keys.BELT)?.getButton(event.slot)?.onClick(player, event)
                else player.find(Keys.BAG)?.getButton(event.slot)?.onClick(player, event)
            }
        }
    }

}