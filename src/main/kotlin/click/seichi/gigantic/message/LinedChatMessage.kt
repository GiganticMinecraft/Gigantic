package click.seichi.gigantic.message

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

    // チャットが終了するまでの時間を計算
    override val duration = messageText.asSafety(Gigantic.DEFAULT_LOCALE)
            .split(NEW_LINE_SYMBOL)
            .size.times(interval)

    override fun sendTo(player: Player) = messageText.asSafety(player.wrappedLocale)
            .split(NEW_LINE_SYMBOL)
            .mapIndexed { index, text -> text to interval * index }
            .forEach {
                Bukkit.getScheduler().scheduleSyncDelayedTask(
                        Gigantic.PLUGIN,
                        { if (player.isValid) protocol.sendTo(player, it.first) },
                        it.second
                )
            }

}