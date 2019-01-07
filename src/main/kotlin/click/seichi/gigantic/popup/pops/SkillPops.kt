package click.seichi.gigantic.popup.pops

import click.seichi.gigantic.extension.toRainbow
import click.seichi.gigantic.popup.LongPopUp
import click.seichi.gigantic.popup.SimplePopUp
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

        val comboText = when (rank) {
            in 1..9 -> "$color$combo Combo"
            10 -> "${Random.nextChatColor()}$combo Combo"
            else -> "$combo Combo".toRainbow()
        }

        SimplePopUp(comboText)
    }

    val HEAL = { amount: Double ->
        LongPopUp("${ChatColor.LIGHT_PURPLE}${amount.toBigDecimal().setScale(1, RoundingMode.HALF_UP)} HP")
    }

}