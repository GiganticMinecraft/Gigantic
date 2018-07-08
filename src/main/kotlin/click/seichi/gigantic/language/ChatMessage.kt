package click.seichi.gigantic.language

import click.seichi.gigantic.extension.wrappedLocale
import org.bukkit.entity.Player

/**
 * @author unicroak
 */
open class ChatMessage(protected val protocol: ChatMessageProtocol, protected val messageText: LocalizedText) : Message {

    override fun sendTo(player: Player) = protocol.sendTo(player, messageText.asSafety(player.wrappedLocale))

}