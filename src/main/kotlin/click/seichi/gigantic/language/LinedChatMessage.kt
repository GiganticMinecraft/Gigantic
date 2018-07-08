package click.seichi.gigantic.language

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.extension.wrappedLocale
import org.bukkit.Bukkit
import org.bukkit.entity.Player

/**
 * @author unicroak
 */
class LinedChatMessage(
        protocol: ChatMessageProtocol,
        messageText: LocalizedText,
        private val interval: Long = 0L
) : ChatMessage(protocol, messageText) {

    companion object {
        const val NEW_LINE_SYMBOL = "%NEW_LINE%"
    }

    private val instance by lazy { Gigantic.PLUGIN }

    override fun sendTo(player: Player) = messageText.asSafety(player.wrappedLocale)
            .split(NEW_LINE_SYMBOL)
            .mapIndexed { index, text -> text to interval * index }
            .forEach {
                Bukkit.getScheduler().runTaskLater(
                        instance,
                        { if (player.isOnline) protocol.sendTo(player, it.first) },
                        it.second
                )
            }

}