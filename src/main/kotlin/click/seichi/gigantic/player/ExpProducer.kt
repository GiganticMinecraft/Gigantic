package click.seichi.gigantic.player

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
    RAID_BOSS(
            { player ->
                player.find(CatalogPlayerCache.MINE_BLOCK)?.get(MineBlockReason.RAID_BOSS) ?: 0L
            }
    )
    ;

    private fun produce(player: Player) = producing(player)

    companion object {
        fun calcExp(player: Player): Long =
                ExpProducer.values().map { it.produce(player) }.sum()

    }

}


