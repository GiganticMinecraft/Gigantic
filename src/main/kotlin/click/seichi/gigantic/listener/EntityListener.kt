package click.seichi.gigantic.listener

import org.bukkit.entity.Mob
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntitySpawnEvent

/**
 * @author tar0ss
 */
class EntityListener : Listener {

    @EventHandler
    fun onSpawnEntity(event: EntitySpawnEvent) {
        if (event.entity !is Mob) return
        event.isCancelled = true
    }
}