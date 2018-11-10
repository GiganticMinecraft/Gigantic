package click.seichi.gigantic.popup.pops

import click.seichi.gigantic.popup.PopUp
import org.bukkit.ChatColor

/**
 * @author tar0ss
 */
object PlayerPops {

    val LEVEL_UP = PopUp(
            "${ChatColor.AQUA} レベルアップ",
            popPattern = PopUp.PopPattern.STILL,
            duration = 60L
    )

}