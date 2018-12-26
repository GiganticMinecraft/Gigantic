package click.seichi.gigantic.popup.pops

import click.seichi.gigantic.popup.PopUp
import org.bukkit.ChatColor

/**
 * @author tar0ss
 */
object SkillPops {

    val MINE_COMBO = { combo: Long, rank: Int ->
        val color = when (rank) {
            1 -> ChatColor.WHITE
            2 -> ChatColor.YELLOW
            3 -> ChatColor.GREEN
            4 -> ChatColor.LIGHT_PURPLE
            5 -> ChatColor.DARK_GREEN
            6 -> ChatColor.DARK_GRAY
            7 -> ChatColor.DARK_AQUA
            8 -> ChatColor.DARK_BLUE
            9 -> ChatColor.DARK_PURPLE
            10 -> ChatColor.DARK_RED
            else -> ChatColor.BLACK
        }
        PopUp(
                "$color$combo Combo",
                PopUp.PopPattern.POP
        )
    }

    val HEAL = { amount: Long ->
        PopUp(
                "${ChatColor.LIGHT_PURPLE}$amount HP",
                PopUp.PopPattern.POP_LONG
        )
    }

}