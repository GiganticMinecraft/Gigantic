package click.seichi.gigantic.message

import click.seichi.gigantic.player.Defaults
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.roundToInt

/**
 * @author tar0ss
 */
class ManaMessage(
        mana: BigDecimal,
        maxMana: BigDecimal,
        // 棒の本数(double値)
        amount: Double
) : Message {

    private val maxNum = Defaults.MANA_BAR_NUM

    private val nextNum = when {
        amount > maxNum - 1 && maxNum > amount -> maxNum - 1
        amount > maxNum -> maxNum
        amount > 0.00 && 1.00 > amount -> 1
        else -> amount.coerceAtLeast(0.00).roundToInt()
    }

    private val ratio = mana.divide(maxMana, 10, RoundingMode.HALF_UP).toDouble().coerceAtLeast(0.00)

    private val remainNumString = (1..nextNum).joinToString(
            prefix = when {
                ratio == 0.00 && mana == 0.toBigDecimal() -> ChatColor.RED
                mana > maxMana -> ChatColor.GOLD
                ratio in 0.00..0.20 -> ChatColor.DARK_BLUE
                ratio in 0.20..0.80 -> ChatColor.BLUE
                ratio in 0.80..0.99 -> ChatColor.LIGHT_PURPLE
                ratio in 1.00..Double.MAX_VALUE -> ChatColor.WHITE
                else -> ChatColor.YELLOW
            }.toString(),
            separator = ""
    ) { Defaults.MANA_CHAR }

    private val lostNumString = (1..(maxNum - nextNum)).joinToString(
            prefix = if (ratio == 0.00 && mana == 0.toBigDecimal()) "${ChatColor.RED}" else "${ChatColor.GRAY}",
            separator = ""
    ) { Defaults.MANA_LOST_CHAR }

    private val spaceString = (1..(50 - maxNum)).joinToString(separator = "") { " " }

    override fun sendTo(player: Player) {
        player.sendTitle(
                "",
                "$remainNumString$lostNumString$spaceString",
                0,
                800,
                10
        )
    }
}