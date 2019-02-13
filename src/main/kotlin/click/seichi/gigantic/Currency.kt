package click.seichi.gigantic

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.effect.GiganticEffect
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.player.Defaults
import org.bukkit.entity.Player

/**通貨種類
 * @author tar0ss
 */
enum class Currency {
    // マイクラデフォ
    DEFAULT {
        override fun getAmount(player: Player): Int {
            return 0
        }
    },
    // 投票
    VOTE_POINT {
        override fun getAmount(player: Player): Int {
            return player.getOrPut(Keys.VOTE).times(Defaults.VOTE_POINT_PER_VOTE)
        }
    },
    // Spade 通貨
    POMME {
        override fun getAmount(player: Player): Int {
            return player.getOrPut(Keys.POMME)
        }
    },
    // 寄付金
    DONATE_POINT {
        override fun getAmount(player: Player): Int {
            return player.getOrPut(Keys.DONATION).div(Defaults.DONATITON_PER_DONATE_POINT)
        }
    },
    ;

    abstract fun getAmount(player: Player): Int

    fun calcRemainAmount(player: Player): Int {
        // エフェクトで減算
        return GiganticEffect.values()
                .filter { this == it.currency }
                .filter { it.isBought(player) }
                .fold(getAmount(player)) { source: Int, effect ->
                    source - effect.amount
                }
    }
}