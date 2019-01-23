package click.seichi.gigantic.message.messages

import click.seichi.gigantic.message.LocalizedText
import java.util.*

/**
 * @author tar0ss
 */
object HomeMessages {

    val DEFAULT_NAME = LocalizedText(
            Locale.JAPANESE to "ホーム"
    )

    val NO_HOME = LocalizedText(
            Locale.JAPANESE to "ホームが設定されていません"
    )

    val COMPLETE = LocalizedText(
            Locale.JAPANESE to "名前を変更しました"
    )

}