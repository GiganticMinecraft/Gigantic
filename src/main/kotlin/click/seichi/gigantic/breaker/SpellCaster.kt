package click.seichi.gigantic.breaker

import org.bukkit.block.Block
import org.bukkit.entity.Player
import java.math.BigDecimal

/**
 * @author tar0ss
 */
interface SpellCaster {

    fun calcConsumeMana(player: Player, breakBlockSet: Set<Block>): BigDecimal

    fun cast(player: Player, base: Block)

}