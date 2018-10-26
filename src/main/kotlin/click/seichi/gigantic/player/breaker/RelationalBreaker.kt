package click.seichi.gigantic.player.breaker

import org.bukkit.block.Block
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
interface RelationalBreaker {

    fun breakRelations(player: Player, block: Block)

}