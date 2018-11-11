package click.seichi.gigantic.monster.ai.ais

import click.seichi.gigantic.extension.isCrust
import click.seichi.gigantic.monster.ai.SoulMonsterAI
import click.seichi.gigantic.util.Random
import org.bukkit.Chunk
import org.bukkit.block.Block
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class SimpleSoulMonsterAI : SoulMonsterAI {

    override fun getAttackBlockSet(chunk: Chunk, target: Player, attackTimes: Int): Set<Block> {
        val blocks = mutableSetOf<Block>()
        var count = 0
        while (blocks.size < attackTimes && count++ < 10) {
            var y = 255
            var block: Block? = null
            while ((block?.isCrust?.not() != false) && y-- > 0) {
                block = chunk.getBlock(Random.nextInt(15), y, Random.nextInt(15))
            }
            block ?: continue
            if (target.location.distance(block.location) >= 5.0) continue
            if (!block.isCrust) continue
            blocks.add(block)
        }
        return blocks
    }

}