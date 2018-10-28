package click.seichi.gigantic.popup

import org.bukkit.ChatColor
import java.math.BigDecimal

/**
 * @author tar0ss
 */
object SpellPops {

    val HEAL = { amount: Long ->
        PopUp(
                "${ChatColor.LIGHT_PURPLE}$amount HP",
                PopUp.PopPattern.POP_LONG
        )
    }

    val STELLA_CLAIR = { amount: BigDecimal ->
        PopUp(
                "${ChatColor.AQUA}${amount.setScale(1)} Mana",
                PopUp.PopPattern.POP_LONG
        )
    }

}