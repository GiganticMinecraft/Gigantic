package click.seichi.gigantic.message.messages

import click.seichi.gigantic.extension.toRainbow
import click.seichi.gigantic.util.Random
import org.bukkit.ChatColor
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * @author tar0ss
 */
object PopUpMessages {

    val BATTLE_DAMAGE = { damage: Long -> "${ChatColor.RED}$damage ATK" }

    val MINE_COMBO = { combo: Long, rank: Int ->
        val color = when (rank) {
            1 -> ChatColor.WHITE
            2 -> ChatColor.GRAY
            3 -> ChatColor.BLUE
            4 -> ChatColor.AQUA
            5 -> ChatColor.LIGHT_PURPLE
            6 -> ChatColor.RED
            7 -> ChatColor.GREEN
            8 -> ChatColor.GOLD
            9 -> ChatColor.YELLOW
            10 -> ChatColor.WHITE
            else -> ChatColor.WHITE
        }

        when (rank) {
            in 1..9 -> "$color$combo Combo"
            10 -> "${Random.nextChatColor()}$combo Combo"
            else -> "$combo Combo".toRainbow()
        }
    }

    val HEAL = { amount: Double -> "${ChatColor.LIGHT_PURPLE}${amount.toBigDecimal().setScale(1, RoundingMode.HALF_UP)} HP" }

    val STELLA_CLAIR = { amount: BigDecimal -> "${ChatColor.AQUA}${amount.setScale(1, RoundingMode.HALF_UP)} Mana" }

}