package click.seichi.gigantic.listener

import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.entity.Mob
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByBlockEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntitySpawnEvent

/**
 * @author tar0ss
 */
class EntityListener : Listener {

    // モブのスポーンをキャンセル
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onSpawnEntity(event: EntitySpawnEvent) {
        if (event.entity !is Mob) return
        event.isCancelled = true
    }

    // 花火のダメージをキャンセル
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onDamageByEntity(event: EntityDamageByEntityEvent) {
        if (event.damager.type != EntityType.FIREWORK) return
        event.isCancelled = true
    }

    // マグマブロックのダメージをキャンセル
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onDamageByEntity(event: EntityDamageByBlockEvent) {
        if (event.damager.type != Material.MAGMA_BLOCK) return
        event.isCancelled = true
    }

}