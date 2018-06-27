package click.seichi.gigantic.message.messages

import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.message.LocalizedString
import click.seichi.gigantic.message.MessageArgumentList
import click.seichi.gigantic.message.MessageProtocol
import click.seichi.gigantic.message.Transmittable
import org.bukkit.entity.Player

/**
 * @author unicroak
 * @author tar0ss
 */
class Message(private val protocol: MessageProtocol, private val message: (MessageArgumentList) -> LocalizedString) : Transmittable {

    constructor(protocol: MessageProtocol, message: LocalizedString) : this(protocol, { message })

    override fun sendTo(receiver: Player, vararg arguments: Any) = protocol.sending(receiver, message(MessageArgumentList(*arguments)).asSafety(receiver.wrappedLocale))

}