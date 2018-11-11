package click.seichi.gigantic.monster.ai

import click.seichi.gigantic.extension.centralLocation
import click.seichi.gigantic.util.Random
import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
interface SoulMonsterAI {

    fun searchNextTargetLocation(chunk: Chunk, target: Player): Location {
        return chunk.getBlock(Random.nextInt(15), 0, Random.nextInt(15)).let { block ->
            chunk.world.getHighestBlockAt(block.location)
                    .getRelative(BlockFace.UP, 5)
                    .centralLocation
                    .add(0.0, -0.5, 0.0)
        }
    }

    // distance per 1tick
    fun getMovementSpeed(currentLocation: Location, nextLocation: Location): Double

}