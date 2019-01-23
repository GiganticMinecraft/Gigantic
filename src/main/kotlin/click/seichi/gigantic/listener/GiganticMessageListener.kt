package click.seichi.gigantic.listener

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.force
import org.bukkit.entity.Player
import org.bukkit.plugin.messaging.PluginMessageListener


/**
 * @author tar0ss
 */
class GiganticMessageListener : PluginMessageListener {

    override fun onPluginMessageReceived(channel: String, player: Player, message: ByteArray) {
        if (channel != "BungeeCord") return
        if (!player.isValid) return

        val input = message.inputStream().reader()
        val subchannel = input.readText()
        if (subchannel == "GetServer") {
            val serverName = input.readText()
            player.force(Keys.SERVER_NAME, serverName)
            return
        }
    }

}