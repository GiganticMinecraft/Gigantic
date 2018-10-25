package click.seichi.gigantic.event.events

import click.seichi.gigantic.skill.Miner
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.block.BlockBreakEvent


/**
 * @author BlackBracken
 */
class RelationalBlockBreakEvent(
        broken: Block,
        player: Player,
        val constructionType: Miner.ConstructionType,
        val depth: Int = 0
) : BlockBreakEvent(broken, player)