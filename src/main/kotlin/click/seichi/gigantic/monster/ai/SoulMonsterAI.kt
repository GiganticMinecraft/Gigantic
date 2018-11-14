package click.seichi.gigantic.monster.ai

import click.seichi.gigantic.extension.centralLocation
import click.seichi.gigantic.extension.isCrust
import click.seichi.gigantic.extension.isSurface
import click.seichi.gigantic.util.Random
import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
open class SoulMonsterAI {

    open fun searchSpawnLocation(spawner: Player, chunk: Chunk): Location {
        var spawnLocation = spawner.location
        while (spawner.location.distance(spawnLocation) <= 3.0) {
            spawnLocation = chunk.getBlock(Random.nextInt(15), 0, Random.nextInt(15)).let { block ->
                chunk.world.getHighestBlockAt(block.location).centralLocation.add(0.0, -0.5, 0.0)
            }
        }
        return spawnLocation
    }


    open fun searchDestination(chunk: Chunk, target: Player, currentLocation: Location): Location {
        var count = 0
        var targetLocation: Location
        do {
            targetLocation = chunk.getBlock(Random.nextInt(15), 0, Random.nextInt(15)).let { block ->
                chunk.world.getHighestBlockAt(block.location)
                        .getRelative(BlockFace.UP, 5)
                        .centralLocation
                        .add(0.0, -0.5, 0.0)
            }
            val distance = targetLocation.distance(currentLocation)
        } while ((distance < 5 || 12 <= distance) && count++ < 10)
        return targetLocation
    }

    open fun getAttackBlock(
            chunk: Chunk,
            alreadyAttackedBlockSet: Set<AttackBlock>,
            target: Player,
            elapsedTick: Long
    ): AttackBlock? {
        val blocks = mutableSetOf<Block>()
        (-3..3).forEach { x ->
            (-3..3).forEach { z ->
                (-1..5).forEach { y ->
                    target.location.block.getRelative(x, y, z).let {
                        blocks.add(it)
                    }
                }
            }
        }
        return blocks.filter { block ->
            block.isCrust && block.isSurface && block.chunk == chunk && alreadyAttackedBlockSet.find { it.block == block } == null
        }.map { it to it.y + Random.nextDouble() }.sortedByDescending { it.second }.firstOrNull()?.let { AttackBlock(target, it.first, elapsedTick) }
    }


}