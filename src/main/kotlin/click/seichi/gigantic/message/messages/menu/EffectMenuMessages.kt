package click.seichi.gigantic.message.messages.menu

import click.seichi.gigantic.message.LocalizedText
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object EffectMenuMessages {

    val TITLE = LocalizedText(
            Locale.JAPANESE to "エフェクト"
    )

    val HAS_BOUGHT = LocalizedText(
            Locale.JAPANESE to "${ChatColor.AQUA}" +
                    "購入済み"
    )

    val CAN_BUY = LocalizedText(
            Locale.JAPANESE to "${ChatColor.GREEN}" +
                    "${ChatColor.UNDERLINE}" +
                    "クリックで購入"
    )

    val CANT_BUY = LocalizedText(
            Locale.JAPANESE to "${ChatColor.RED}" +
                    "${ChatColor.UNDERLINE}" +
                    "購入できない"
    )

    val VOTE_POINT = LocalizedText(
            Locale.JAPANESE to "${ChatColor.YELLOW}" +
                    "${ChatColor.BOLD}" +
                    "投票p"
    )

    val POMME = LocalizedText(
            Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                    "${ChatColor.BOLD}" +
                    "ポム"
    )

    val DONATE_POINT = LocalizedText(
            Locale.JAPANESE to "${ChatColor.GOLD}" +
                    "${ChatColor.BOLD}" +
                    "寄付p"
    )

    val BUY_TYPE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.WHITE}" +
                    " で購入できる"
    )

}