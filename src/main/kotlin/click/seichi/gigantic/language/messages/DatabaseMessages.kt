package click.seichi.gigantic.language.messages

import click.seichi.gigantic.language.ChatMessage
import click.seichi.gigantic.language.ChatMessageProtocol
import click.seichi.gigantic.language.LocalizedText
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object DatabaseMessages {

    val PLAYER_LOADING = ChatMessage(ChatMessageProtocol.SUB_TITLE, LocalizedText(
            Locale.JAPANESE to "${ChatColor.BLUE}${ChatColor.BOLD}読み込み中...",
            Locale.ENGLISH to "${ChatColor.BLUE}${ChatColor.BOLD}Loading..."
    ))

    val PLAYER_LOAD_COMPLETED = ChatMessage(ChatMessageProtocol.SUB_TITLE, LocalizedText(
            Locale.JAPANESE to "${ChatColor.YELLOW}${ChatColor.BOLD}ロード完了",
            Locale.ENGLISH to "${ChatColor.YELLOW}${ChatColor.BOLD}Load is completed"
    ))
}