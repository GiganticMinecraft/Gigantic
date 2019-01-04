package click.seichi.gigantic.listener.packet

import click.seichi.gigantic.Gigantic
import com.comphenix.protocol.PacketType
import com.comphenix.protocol.events.ListenerPriority
import com.comphenix.protocol.events.PacketAdapter
import com.comphenix.protocol.events.PacketEvent

/**
 * @author unicroak
 */
class ExperienceOrbSpawn : PacketAdapter(
        Gigantic.PLUGIN,
        ListenerPriority.LOW,
        PacketType.Play.Server.SPAWN_ENTITY_EXPERIENCE_ORB
) {

    override fun onPacketSending(event: PacketEvent) {
        event.packet.getEntityModifier(event).readSafely(0)?.remove()
        event.isCancelled = true
    }

}