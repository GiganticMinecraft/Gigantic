package click.seichi.gigantic.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.ItemSpawnEvent
import org.bukkit.event.player.PlayerDropItemEvent

/**
 * @author tar0ss
 */
class ItemListener : Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onItemSpawn(event: ItemSpawnEvent) {
        event.isCancelled = true
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onDropItem(event: PlayerDropItemEvent) {
        event.isCancelled = true
    }
}