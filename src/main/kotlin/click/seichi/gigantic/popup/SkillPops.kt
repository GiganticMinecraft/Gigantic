package click.seichi.gigantic.popup

import click.seichi.gigantic.cache.manipulator.manipulators.MineCombo
import org.bukkit.ChatColor

/**
 * @author tar0ss
 */
object SkillPops {

    val MINE_COMBO = { combo: MineCombo ->
        val color = when (combo.currentCombo) {
            in 0..9 -> ChatColor.WHITE
            in 10..29 -> ChatColor.GREEN
            in 30..69 -> ChatColor.AQUA
            in 70..149 -> ChatColor.BLUE
            in 150..349 -> ChatColor.YELLOW
            in 350..799 -> ChatColor.DARK_AQUA
            in 800..1199 -> ChatColor.LIGHT_PURPLE
            else -> ChatColor.GOLD
        }
        PopUp(
                "$color${ChatColor.BOLD}${combo.currentCombo} Combo",
                10L
        )
    }

    val HEAL = { amount: Long ->
        PopUp(
                "${ChatColor.LIGHT_PURPLE}$amount 回復",
                10L
        )
    }

}