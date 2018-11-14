package click.seichi.gigantic.player

import click.seichi.gigantic.cache.manipulator.MineBlockReason
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.find
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
enum class ExpProducer(private val producing: (Player) -> Long) {
    MINE_BLOCK(
            { player ->
                player.find(CatalogPlayerCache.MINE_BLOCK)?.get() ?: 0L
            }
    ),
    DEATH_PENALTY(
            { player ->
                player.find(CatalogPlayerCache.MINE_BLOCK)?.get(MineBlockReason.DEATH_PENALTY)?.times(-1L) ?: 0L
            }
    ),
    ;

    private fun produce(player: Player) = producing(player)

    companion object {
        fun calcExp(player: Player): Long =
                ExpProducer.values().map { it.produce(player) }.sum()

    }

}


