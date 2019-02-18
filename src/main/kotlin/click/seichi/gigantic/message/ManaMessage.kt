package click.seichi.gigantic.message

import org.bukkit.entity.Player
import kotlin.math.roundToInt

/**
 * @author tar0ss
 */
class ManaMessage(
        amount: Double
) : Message {

    private val maxNum = 20

    private val nextNum = when {
        amount > maxNum - 1 && maxNum > amount -> maxNum - 1
        amount > maxNum -> maxNum
        amount > 0.00 && 1.00 > amount -> 1
        else -> amount.coerceAtLeast(0.00).roundToInt()
    }

    override fun sendTo(player: Player) {
        player.foodLevel = nextNum
    }
}