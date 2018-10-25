package click.seichi.gigantic.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockFormEvent
import org.bukkit.event.block.BlockFromToEvent

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
    fun onBlockForm(event: BlockFormEvent) {
        if (event.newState.isPlaced) {
            event.isCancelled = true
        }
    }

}