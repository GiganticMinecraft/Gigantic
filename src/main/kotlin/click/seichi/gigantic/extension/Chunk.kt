package click.seichi.gigantic.extension

import click.seichi.gigantic.battle.Battle
import click.seichi.gigantic.battle.BattleManager
import click.seichi.gigantic.util.Random
import org.bukkit.Chunk
import org.bukkit.GameRule
import org.bukkit.Location
import org.bukkit.block.Block
import kotlin.math.abs

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

fun Chunk.findBattle(): Battle? = BattleManager.findBattle(this)

val Chunk.isBattled: Boolean
    get() = findBattle() != null

val Chunk.isSpawnArea: Boolean
    get() = abs(x - world.spawnLocation.chunk.x) < world.getGameRuleValue(GameRule.SPAWN_RADIUS).div(16) &&
            abs(z - world.spawnLocation.chunk.z) < world.getGameRuleValue(GameRule.SPAWN_RADIUS).div(16)

fun Chunk.getSpawnableLocation(): Location {
    val x = Random.nextInt(15)
    val z = Random.nextInt(15)
    val block: Block = getBlock(x, 255, z)
    return block.surfaceBlock.centralLocation.add(0.0, 2.0, 0.0)
}