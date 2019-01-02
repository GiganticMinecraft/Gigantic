package click.seichi.gigantic.popup.pops

import click.seichi.gigantic.extension.toRainbow
import click.seichi.gigantic.popup.PopUp
import click.seichi.gigantic.util.Random
import org.bukkit.ChatColor
import java.math.RoundingMode

/**
 * @author tar0ss
 */
object SkillPops {

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
            in 1..9 -> {
                PopUp(
                        "$color$combo Combo",
                        PopUp.PopPattern.POP
                )
            }
            10 -> {
                PopUp(
                        "${Random.nextChatColor()}$combo Combo",
                        PopUp.PopPattern.POP
                )
            }
            else -> {
                PopUp(
                        "$combo Combo".toRainbow(),
                        PopUp.PopPattern.POP
                )
            }
        }

    }

    val HEAL = { amount: Double ->
        PopUp(
                "${ChatColor.LIGHT_PURPLE}${amount.toBigDecimal().setScale(1, RoundingMode.HALF_UP)} HP",
                PopUp.PopPattern.POP_LONG
        )
    }

}