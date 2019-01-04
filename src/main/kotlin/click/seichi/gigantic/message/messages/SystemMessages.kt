package click.seichi.gigantic.message.messages

import click.seichi.gigantic.message.LocalizedText
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object SystemMessages {

    private val SQL = "${ChatColor.YELLOW}" +
            "[SQL]" +
            "${ChatColor.WHITE}"

    val REGULAR_PLAYER_CACHE_SAVE = LocalizedText(
            Locale.JAPANESE to SQL +
                    "定期保存開始..."
    )

    val PEOPLE = LocalizedText(
            Locale.JAPANESE to "人"
    )

    val TARGET = LocalizedText(
            Locale.JAPANESE to SQL +
                    "対象プレイヤー: "
    )

    val SAVE_COMPLETE = LocalizedText(
            Locale.JAPANESE to SQL +
                    "保存成功: "
    )

    val SAVE_FAIL = LocalizedText(
            Locale.JAPANESE to SQL +
                    "保存失敗: "
    )

    val REGULAR_PLAYER_CACHE_SAVE_COMPLETE = LocalizedText(
            Locale.JAPANESE to SQL +
                    "...定期保存終了"
    )

}