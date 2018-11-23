package click.seichi.gigantic.popup.pops

import click.seichi.gigantic.popup.PopUp
import org.bukkit.ChatColor
import java.math.BigDecimal

/**
 * @author tar0ss
 */
object SpellPops {

    val STELLA_CLAIR = { amount: BigDecimal ->
        PopUp(
                "${ChatColor.AQUA}${amount.setScale(1)} Mana",
                PopUp.PopPattern.POP_LONG
        )
    }

}