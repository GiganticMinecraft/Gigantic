package click.seichi.gigantic.breaker

import org.bukkit.block.Block
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
interface Breaker {

    fun breakBlock(
            player: Player,
            block: Block
    )

}