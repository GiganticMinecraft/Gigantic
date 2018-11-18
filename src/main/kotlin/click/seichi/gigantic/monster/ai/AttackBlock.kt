package click.seichi.gigantic.monster.ai

import click.seichi.gigantic.battle.BattlePlayer
import org.bukkit.block.Block

/**
 * @author tar0ss
 */
data class AttackBlock(
        val target: BattlePlayer,
        val block: Block,
        val elapsedTick: Long
)
