package click.seichi.gigantic.monster.ai

import org.bukkit.block.Block
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
data class AttackBlock(
        val target: Player,
        val block: Block,
        val elapsedTick: Long
)
