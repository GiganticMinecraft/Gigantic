package click.seichi.gigantic.extension

import click.seichi.gigantic.battle.BattleManager
import org.bukkit.Chunk
import org.bukkit.block.Block

/**
 * @author tar0ss
 */

fun Chunk.forEachBlock(action: (Block) -> Unit) {
    (0..15).forEach { x ->
        (0..15).forEach { z ->
            (0..256).forEach y@{ y ->
                val block = getBlock(x, y, z) ?: return@y
                action.invoke(block)
            }
        }
    }
}

val Chunk.isBattled: Boolean
    get() = BattleManager.getBattleSet().firstOrNull { it.chunk == this } != null