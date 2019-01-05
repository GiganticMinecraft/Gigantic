package click.seichi.gigantic.message.messages.command

import click.seichi.gigantic.message.LocalizedText
import java.util.*

/**
 * @author tar0ss
 */
object TellMessages {

    val CONSOLE = LocalizedText(
            Locale.JAPANESE to "ゲーム内で実行してください"
    )

    val NO_PLAYER = LocalizedText(
            Locale.JAPANESE to "プレイヤーが存在しません"
    )

    val NO_ID = LocalizedText(
            Locale.JAPANESE to "返信先が見つかりません"
    )

    val TELL_PREFIX = LocalizedText(
            Locale.JAPANESE to "にささやかれました:"
    )

    val NOT_FOLLOWED = LocalizedText(
            Locale.JAPANESE to "フォローされていないため送信できませんでした"
    )

}