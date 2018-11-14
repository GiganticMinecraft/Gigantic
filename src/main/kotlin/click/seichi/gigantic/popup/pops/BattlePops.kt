package click.seichi.gigantic.popup.pops

import click.seichi.gigantic.popup.PopUp
import org.bukkit.ChatColor
import java.math.BigDecimal

/**
 * @author tar0ss
 */
object BattlePops {

    val BATTLE_DAMAGE = { damage: BigDecimal ->
        PopUp(
                "${ChatColor.RED}${damage.setScale(1)} ATK",
                PopUp.PopPattern.POP_LONG
        )
    }

}