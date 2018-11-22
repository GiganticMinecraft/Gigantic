package click.seichi.gigantic.message.messages

import click.seichi.gigantic.message.ChatMessage
import click.seichi.gigantic.message.ChatMessageProtocol
import click.seichi.gigantic.message.LocalizedText
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object EffectMessages {

    val COMBO = ChatMessage(ChatMessageProtocol.ACTION_BAR,
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.GREEN}コンボ数によりダメージ増加"
            )
    )

}