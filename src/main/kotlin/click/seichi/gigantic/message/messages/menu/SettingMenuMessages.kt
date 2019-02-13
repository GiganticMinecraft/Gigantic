package click.seichi.gigantic.message.messages.menu

import click.seichi.gigantic.message.LocalizedText
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object SettingMenuMessages {

    val TITLE = LocalizedText(
            Locale.JAPANESE to "詳細設定"
    )

    val DISPLAY = LocalizedText(
            Locale.JAPANESE to "表示設定"
    )

    val EXP_TOGGLE = { toggle: Boolean ->
        Locale.JAPANESE to "${ChatColor.WHITE}" +
                "経験値獲得表示: " +
                if (toggle) {
                    "${ChatColor.GREEN}ON"
                } else {
                    "${ChatColor.RED}OFF"
                }
    }

}