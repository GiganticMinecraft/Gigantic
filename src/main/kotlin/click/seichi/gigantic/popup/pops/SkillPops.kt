package click.seichi.gigantic.popup.pops

import click.seichi.gigantic.cache.manipulator.manipulators.MineCombo
import click.seichi.gigantic.popup.PopUp
import org.bukkit.ChatColor

/**
 * @author tar0ss
 */
object SkillPops {

    val MINE_COMBO = { combo: MineCombo ->
        val color = when (combo.currentCombo) {
            in 0..9 -> ChatColor.WHITE
            in 10..29 -> ChatColor.YELLOW
            in 30..69 -> ChatColor.GREEN
            in 70..149 -> ChatColor.LIGHT_PURPLE
            in 150..349 -> ChatColor.DARK_GREEN
            in 350..799 -> ChatColor.DARK_GRAY
            in 800..1199 -> ChatColor.DARK_BLUE
            else -> ChatColor.BLACK
        }
        PopUp(
                "$color${combo.currentCombo} Combo",
                PopUp.PopPattern.POP
        )
    }

    val HEAL = { amount: Long ->
        PopUp(
                "${ChatColor.LIGHT_PURPLE}$amount HP",
                PopUp.PopPattern.POP_LONG
        )
    }

    val KODAMA_DRAIN = { amount: Long ->
        PopUp(
                "${ChatColor.GREEN}$amount HP",
                PopUp.PopPattern.POP_LONG
        )
    }


}