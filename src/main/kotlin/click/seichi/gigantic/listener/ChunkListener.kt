package click.seichi.gigantic.listener

import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.world.ChunkLoadEvent

/**
 * @author tar0ss
 */
class ChunkListener : Listener {

    // 残ったコンボ表示を消す
    @EventHandler
    fun onLoad(event: ChunkLoadEvent) {
        event.chunk.entities.filter { it.type != EntityType.PLAYER }.forEach { it.remove() }
    }


}