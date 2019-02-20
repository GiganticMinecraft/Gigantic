package click.seichi.gigantic.message.messages.menu

import click.seichi.gigantic.message.LocalizedText
import org.bukkit.ChatColor
import org.joda.time.DateTime
import java.util.*

/**
 * @author tar0ss
 */
object DonateHistoryMessages {

    val TITLE = LocalizedText(
            Locale.JAPANESE to "寄付履歴"
    )

    val DONATE = { amount: Int ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.YELLOW}" +
                        "${ChatColor.BOLD}" +
                        "${amount}円"
        )
    }

    val DATE = { date: DateTime ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.WHITE}" +
                        date.toString("yyyy年MM月dd日 kk時mm分ss秒")
        )
    }
}