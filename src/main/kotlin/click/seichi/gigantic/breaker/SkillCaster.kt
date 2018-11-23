package click.seichi.gigantic.breaker

import org.bukkit.block.Block
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
interface SkillCaster {
    fun cast(player: Player, base: Block)

    fun castToBlock(player: Player, block: Block) {
        onCastToBlock(player, block)
    }

    fun onCastToBlock(player: Player, block: Block)

}