package click.seichi.gigantic.popup.pops

import click.seichi.gigantic.popup.PopUp
import org.bukkit.ChatColor

/**
 * @author tar0ss
 */
object BattlePops {

    val BATTLE_DAMAGE = { damage: Long ->
        PopUp(
                "${ChatColor.RED}$damage ATK",
                PopUp.PopPattern.POP_LONG
        )
    }

}