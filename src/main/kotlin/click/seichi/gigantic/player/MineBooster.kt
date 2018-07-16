package click.seichi.gigantic.player

import click.seichi.gigantic.extension.gPlayer
import click.seichi.gigantic.player.components.MineBoost
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
enum class MineBooster(private val producing: (Player) -> Int) {
    MINE_COMBO({ player ->
        player.gPlayer?.run {
            if (!LockedFunction.MINE_BOOST.isUnlocked(level)) return@run null
            val combo = mineCombo.currentCombo
            when (combo) {
                in 0L..3L -> 0
                in 4L..9L -> 1
                in 10L..19L -> 2
                else -> 3
            }
        } ?: 0
    })
    ;

    private fun produce(player: Player) = producing(player)

    companion object {
        private val MAX = MineBoost.MAX_BOOST

        fun calcBoost(player: Player) = values().map { it.produce(player) }.sum()
    }

}