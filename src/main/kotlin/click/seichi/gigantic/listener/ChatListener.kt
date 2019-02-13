package click.seichi.gigantic.listener

import click.seichi.gigantic.extension.isMute
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

/**
 * @author tar0ss
 */
class ChatListener : Listener {

    @EventHandler
    fun onChat(event: AsyncPlayerChatEvent) {
        // ミュート処理
        val recipients = event.recipients
        val player = event.player
        recipients.removeIf { it.isMute(player.uniqueId) }
    }
}