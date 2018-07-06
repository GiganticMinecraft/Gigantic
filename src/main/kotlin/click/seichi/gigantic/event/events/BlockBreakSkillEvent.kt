package click.seichi.gigantic.event.events

import click.seichi.gigantic.event.CustomEvent
import org.bukkit.block.Block
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class BlockBreakSkillEvent(val player: Player, val block: Block) : CustomEvent() {
}