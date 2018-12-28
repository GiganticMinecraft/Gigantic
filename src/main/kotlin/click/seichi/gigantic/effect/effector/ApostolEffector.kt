package click.seichi.gigantic.effect.effector

import org.bukkit.block.Block
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
interface ApostolEffector {

    fun apostolBreak(player: Player, breakBlockSet: Set<Block>)
}