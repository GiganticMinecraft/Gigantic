package click.seichi.gigantic

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.player.Defaults
import org.bukkit.entity.Player

/**通貨種類
 * @author tar0ss
 */
enum class Currency {
    // 投票
    VOTE_POINT {
        override fun getTotalAmount(player: Player): Int {
            return player.getOrPut(Keys.VOTE).times(Defaults.VOTE_POINT_PER_VOTE)
        }
    },
    // Spade 通貨
    POMME {
        override fun getTotalAmount(player: Player): Int {
            return player.getOrPut(Keys.POMME)
        }
    },
    // 寄付金
    DONATE_POINT {
        override fun getTotalAmount(player: Player): Int {
            return player.getOrPut(Keys.DONATION).div(Defaults.DONATITON_PER_DONATE_POINT)
        }
    },
    ;

    // 累計獲得量
    abstract fun getTotalAmount(player: Player): Int

    // 現在残っている量
    fun calcRemainAmount(player: Player): Int {
        // 購入した商品で減算
        return player.getOrPut(Keys.PURCHASE_TICKET_LIST)
                .filter { !it.isCancelled }
                .filter { it.product.currency == this }
                .fold(getTotalAmount(player)) { source: Int, ticket ->
                    source - ticket.product.price.times(ticket.amount)
                }
    }
}