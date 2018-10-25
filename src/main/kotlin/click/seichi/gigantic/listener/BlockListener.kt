package click.seichi.gigantic.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
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

    //TODO 自然にブロックが落下するようにする //BlockPhisicsEvent

}