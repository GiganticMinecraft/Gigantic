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
            3 -> ChatColor.GOLD
            4 -> ChatColor.GREEN
            5 -> ChatColor.LIGHT_PURPLE
            6 -> ChatColor.DARK_GREEN
            7 -> ChatColor.DARK_AQUA
            8 -> ChatColor.DARK_RED
            9 -> ChatColor.DARK_PURPLE
            10 -> ChatColor.DARK_BLUE
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