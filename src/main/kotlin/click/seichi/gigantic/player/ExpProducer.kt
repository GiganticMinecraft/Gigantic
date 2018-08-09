package click.seichi.gigantic.player

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.find
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
enum class ExpProducer(private val producing: (Player) -> Long) {
    MINE_BLOCK(
            { player ->
                player.find(Keys.EXP) ?: 0L
            }
    )
    ;

    private fun produce(player: Player) = producing(player)

    companion object {
        fun calcExp(player: Player): Long =
                ExpProducer.values().map { it.produce(player) }.sum()

    }

}


