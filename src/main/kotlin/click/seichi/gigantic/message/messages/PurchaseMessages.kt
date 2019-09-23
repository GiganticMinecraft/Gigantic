package click.seichi.gigantic.message.messages

import click.seichi.gigantic.message.ChatMessage
import click.seichi.gigantic.message.ChatMessageProtocol
import click.seichi.gigantic.message.LocalizedText
import java.util.*

/**
 * @author tar0ss
 */
object PurchaseMessages {

    val CONSOLE_ERROR_INSERT_DATABASE = LocalizedText(
            Locale.JAPANESE to "データベースへの挿入に失敗しました。"
    )

    val USER_ERROR_INSERT_DATABASE = ChatMessage(
            ChatMessageProtocol.CHAT,
            LocalizedText(Locale.JAPANESE to "不明なエラーが発生しました。運営までご報告をお願いします。")
    )

}