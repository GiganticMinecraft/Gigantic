package click.seichi.gigantic.listener

import org.bukkit.event.Listener

/**
 * @author tar0ss
 */
class ChunkListener : Listener {

// not working 原因不明
//    @EventHandler
//    fun onPopulate(event: ChunkPopulateEvent) {
//        val world = event.world ?: return
//        val chunk = event.chunk ?: return
//        (0..15).forEach { x ->
//            (0..15).forEach { z ->
//                (0..1).forEach loopY@{ y ->
//                    val block = chunk.getBlock(x, y, z) ?: return@loopY
//                    block.type = Material.BEDROCK
//                }
//            }
//        }
//    }

}