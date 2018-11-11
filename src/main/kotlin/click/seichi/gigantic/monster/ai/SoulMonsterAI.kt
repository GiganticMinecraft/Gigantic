package click.seichi.gigantic.monster.ai

import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
interface SoulMonsterAI {

    fun searchNextTargetLocation(chunk: Chunk, target: Player, currentLocation: Location): Location

    fun getAttackBlockSet(chunk: Chunk, target: Player, attackTimes: Int): Set<Block>

}