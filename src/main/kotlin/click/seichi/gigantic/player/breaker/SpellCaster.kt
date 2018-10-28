package click.seichi.gigantic.player.breaker

import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.manipulate
import click.seichi.gigantic.message.messages.PlayerMessages
import org.bukkit.block.Block
import org.bukkit.entity.Player
import java.math.BigDecimal

/**
 * @author tar0ss
 */
interface SpellCaster {

    fun calcConsumeMana(player: Player, block: Block): BigDecimal

    fun cast(player: Player, base: Block)

    fun castToBlock(player: Player, block: Block) {
        player.manipulate(CatalogPlayerCache.MANA) {
            it.decrease(calcConsumeMana(player, block))
            PlayerMessages.MANA_DISPLAY(it).sendTo(player)
        }
        onCastToBlock(player, block)
    }

    fun onCastToBlock(player: Player, block: Block)

}