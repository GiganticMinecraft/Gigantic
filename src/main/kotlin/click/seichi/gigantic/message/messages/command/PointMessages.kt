package click.seichi.gigantic.message.messages.command

import click.seichi.gigantic.message.LocalizedText
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object PointMessages {

    val DETECT_VOTE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.GREEN}" +
                    "投票検知"
    )

    val DETECT_DONATE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.GREEN}" +
                    "寄付検知"
    )

    val COMPLETE_STORE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.GREEN}" +
                    "データベース処理終了"
    )


}