package click.seichi.gigantic.listener.packet

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.events.ListenerPriority
import com.comphenix.protocol.events.PacketAdapter
import com.comphenix.protocol.events.PacketEvent
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author unicroak
 */
class ExperienceOrbSpawn(instance: JavaPlugin) : PacketAdapter(
        instance,
        ListenerPriority.LOW,
        PacketType.Play.Server.SPAWN_ENTITY_EXPERIENCE_ORB
) {

    override fun onPacketSending(event: PacketEvent) {
        event.packet.getEntityModifier(event).readSafely(0)?.remove()
        event.isCancelled = true
    }

}