package click.seichi.gigantic.message.lang

import click.seichi.gigantic.message.LocalizedString
import click.seichi.gigantic.message.MessageProtocol
import click.seichi.gigantic.message.messages.Message
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object DatabaseLang {

    val PROFILE_LOADING_MESSAGE = Message(MessageProtocol.SUB_TITLE, LocalizedString(
            Locale.JAPANESE to "${ChatColor.BLUE}${ChatColor.BOLD}読み込み中...",
            Locale.ENGLISH to "${ChatColor.BLUE}${ChatColor.BOLD}Loading..."
    ))

    val PROFILE_LOAD_COMPLETED_MESSAGE = Message(MessageProtocol.SUB_TITLE, LocalizedString(
            Locale.JAPANESE to "${ChatColor.YELLOW}${ChatColor.BOLD}ロード完了",
            Locale.ENGLISH to "${ChatColor.YELLOW}${ChatColor.BOLD}Load is completed"
    ))
}