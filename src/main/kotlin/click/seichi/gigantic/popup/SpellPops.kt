package click.seichi.gigantic.popup

import org.bukkit.ChatColor

/**
 * @author tar0ss
 */
object SpellPops {

    val HEAL = { amount: Long ->
        PopUp(
                "${ChatColor.LIGHT_PURPLE}$amount HP",
                PopUp.PopPattern.POP
        )
    }

    val STELLA_CLAIR = { amount: Long ->
        PopUp(
                "${ChatColor.AQUA}$amount Mana",
                PopUp.PopPattern.POP
        )
    }

}