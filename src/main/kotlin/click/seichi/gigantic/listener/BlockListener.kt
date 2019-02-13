package click.seichi.gigantic.listener

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockFromToEvent
import org.bukkit.event.block.BlockSpreadEvent

/**
 * @author unicroak
 */
class BlockListener : Listener {

    // 液体の流れを止める
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun onBlockFromTo(event: BlockFromToEvent) {
        event.block ?: return
        event.isCancelled = true
    }

    // 芝生増殖防止
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    fun cancelSpreadGrass(event: BlockSpreadEvent) {
        if (event.newState.type != Material.GRASS_BLOCK) return
        if (event.source.type != Material.GRASS_BLOCK) return
        event.isCancelled = true
    }

}