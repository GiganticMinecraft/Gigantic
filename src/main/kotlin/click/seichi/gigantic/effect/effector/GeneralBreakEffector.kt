package click.seichi.gigantic.effect.effector

import org.bukkit.block.Block
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
interface GeneralBreakEffector {

    fun generalBreak(player: Player, block: Block)
}