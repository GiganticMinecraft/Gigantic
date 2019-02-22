package click.seichi.gigantic.message.messages

import click.seichi.gigantic.message.ChatMessage
import click.seichi.gigantic.message.ChatMessageProtocol
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.relic.Relic
import org.bukkit.ChatColor
import java.util.*


/**
 * @author tar0ss
 */
object GiganticEventMessages {

    val DROPPED_RELIC = { relic: Relic ->
        ChatMessage(ChatMessageProtocol.CHAT,
                LocalizedText(
                        Locale.JAPANESE.let {
                            it to "${ChatColor.WHITE}" +
                                    "運営より レリック「" +
                                    "${ChatColor.YELLOW}${ChatColor.BOLD}" +
                                    relic.getName(it) +
                                    "${ChatColor.WHITE}" +
                                    "」を1つ受け取りました。"
                        }
                ))
    }
}