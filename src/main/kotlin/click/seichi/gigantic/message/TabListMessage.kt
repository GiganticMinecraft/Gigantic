package click.seichi.gigantic.message

import click.seichi.gigantic.extension.wrappedLocale
import net.minecraft.server.v1_14_R1.IChatBaseComponent
import net.minecraft.server.v1_14_R1.PacketPlayOutPlayerListHeaderFooter
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class TabListMessage(
        val headerString: LocalizedText,
        val footerString: LocalizedText
) : Message {

    override fun sendTo(player: Player) {
        val cPlayer = player as CraftPlayer
        val connection = cPlayer.handle.playerConnection
        val packet = PacketPlayOutPlayerListHeaderFooter()

        PacketPlayOutPlayerListHeaderFooter::class.java.getDeclaredField("header").apply {
            val component = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" +
                    headerString.asSafety(player.wrappedLocale) +
                    "\"}")
            isAccessible = true
            set(packet, component)
            isAccessible = !isAccessible
        }

        PacketPlayOutPlayerListHeaderFooter::class.java.getDeclaredField("footer").apply {
            val component = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" +
                    footerString.asSafety(player.wrappedLocale) +
                    "\"}")
            isAccessible = true
            set(packet, component)
            isAccessible = !isAccessible
        }

        connection.sendPacket(packet)

    }
}