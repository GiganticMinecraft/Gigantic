package click.seichi.gigantic.message.messages

import click.seichi.gigantic.message.LocalizedText
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object MenuMessages {
    val BACK_BUTTON = { menuTitle: String ->
        LocalizedText(
                Locale.JAPANESE to "$menuTitle${ChatColor.RESET}${ChatColor.WHITE}に戻る"
        )
    }

    val NEXT_BUTTON = LocalizedText(
            Locale.JAPANESE to "次へ"
    )

    val PREV_BUTTON = LocalizedText(
            Locale.JAPANESE to "前へ"
    )
}