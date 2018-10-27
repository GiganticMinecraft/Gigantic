package click.seichi.gigantic.listener

import org.bukkit.entity.FallingBlock
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockFromToEvent
import org.bukkit.event.entity.EntityChangeBlockEvent

/**
 * @author unicroak
 */
class BlockListener : Listener {

    @EventHandler
    fun onBlockFromTo(event: BlockFromToEvent) {
        event.block ?: return
        event.isCancelled = true
    }

    // 砂等の標準で落下するブロックをキャンセル（落下処理は別で一括管理）
    @EventHandler
    fun onFallBlock(event: EntityChangeBlockEvent) {
        val block = event.block ?: return
        val fallingBlock = event.entity as? FallingBlock ?: return
        val material = fallingBlock.blockData.material
        if (block.type == material) {
            event.isCancelled = true
        }
    }

}