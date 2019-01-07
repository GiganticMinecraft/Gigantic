package click.seichi.gigantic.popup.pops

import click.seichi.gigantic.popup.LongPopUp
import org.bukkit.ChatColor
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * @author tar0ss
 */
object SpellPops {

    val STELLA_CLAIR = { amount: BigDecimal ->
        LongPopUp("${ChatColor.AQUA}${amount.setScale(1, RoundingMode.HALF_UP)} Mana")
    }

}