package click.seichi.gigantic.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.ItemSpawnEvent

/**
 * @author tar0ss
 */
class ItemListener : Listener {

    // 全てのアイテムスポーンをキャンセル
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onItemSpawn(event: ItemSpawnEvent) {
        event.isCancelled = true
    }


}