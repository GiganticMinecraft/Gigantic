package click.seichi.gigantic.breaker

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.find
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.manipulate
import click.seichi.gigantic.extension.offer
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
        }

        player.find(CatalogPlayerCache.MANA)?.let {
            PlayerMessages.MANA_DISPLAY(it).sendTo(player)
        }

        player.offer(Keys.IS_UPDATE_PROFILE, true)
        player.getOrPut(Keys.BAG).carry(player)

        onCastToBlock(player, block)
    }

    fun onCastToBlock(player: Player, block: Block)

}