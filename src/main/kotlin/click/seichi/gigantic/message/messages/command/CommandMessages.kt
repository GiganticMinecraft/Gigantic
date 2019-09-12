package click.seichi.gigantic.message.messages.command

import click.seichi.gigantic.message.LocalizedText
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object CommandMessages {


    val NO_USER = { playerName: String ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.RED}" +
                        "指定されたユーザーは見つかりません．\n" +
                        "player name:$playerName"
        )
    }

    val CONSOLE = LocalizedText(
            Locale.JAPANESE to "ゲーム内で実行してください"
    )

    val NO_PLAYER = LocalizedText(
            Locale.JAPANESE to "プレイヤーが存在しません"
    )

}