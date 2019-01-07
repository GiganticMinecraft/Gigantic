package click.seichi.gigantic.popup.pops

import click.seichi.gigantic.popup.LongPopUp
import org.bukkit.ChatColor

/**
 * @author tar0ss
 */
object BattlePops {

    val BATTLE_DAMAGE = { damage: Long ->
        LongPopUp("${ChatColor.RED}$damage ATK")
    }

}