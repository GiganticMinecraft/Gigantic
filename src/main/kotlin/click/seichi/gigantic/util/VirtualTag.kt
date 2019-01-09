package click.seichi.gigantic.util

import click.seichi.gigantic.Gigantic
import com.comphenix.protocol.PacketType
import com.comphenix.protocol.wrappers.WrappedDataWatcher
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.util.Vector
import java.util.*

/**
 * @author unicroak
 */
class VirtualTag(var location: Location,
                 private val text: String,
                 private val id: Int = Random.nextInt(Int.MAX_VALUE),
                 private val uuid: UUID = UUID.randomUUID()) {

    companion object {
        private const val BIT_IS_SMALL = 0x01
        private const val BIT_NO_BASE_PLATE = 0x08
        private const val BIT_SET_MARKER = 0x10
        private const val BIT_INVISIBLE = 0x20
        private const val ARMOR_STAND_ID = 30
        private const val ARMOR_STAND_HEIGHT = 2.25

        private val protocolManager = Gigantic.PROTOCOL_MG
    }

    fun sendSpawnPacket(player: Player) {
        val packet = protocolManager.createPacket(PacketType.Play.Server.SPAWN_ENTITY_LIVING)

        packet.integers.apply {
            write(0, id)
            write(1, ARMOR_STAND_ID)
        }

        packet.doubles.apply {
            write(0, location.x)
            write(1, location.y - ARMOR_STAND_HEIGHT)
            write(2, location.z)
        }

        packet.uuiDs.apply {
            write(0, uuid)
        }

        packet.dataWatcherModifier.values[0].apply {
            setObject(createDataWatcherObject<Byte>(0), BIT_INVISIBLE)
            setObject(createDataWatcherObject<Byte>(2), BIT_IS_SMALL + BIT_NO_BASE_PLATE + BIT_SET_MARKER)
            setObject(createDataWatcherObject<String>(2), text)
            setObject(createDataWatcherObject<Boolean>(1), true) // SILENT
            setObject(createDataWatcherObject<Boolean>(2), true) // NO_GRAVITY
        }

        protocolManager.sendServerPacket(player, packet)
    }

    fun sendMovePacket(player: Player, delta: Vector) {
        val packet = protocolManager.createPacket(PacketType.Play.Server.REL_ENTITY_MOVE)

        packet.integers.apply {
            write(0, id)
        }

        packet.shorts.apply {
            write(0, (delta.x * 4096).toShort())
            write(1, (delta.y * 4096).toShort())
            write(2, (delta.z * 4096).toShort())
        }

        protocolManager.sendServerPacket(player, packet)
    }

    fun sendTeleportPacket(player: Player) {
        val packet = protocolManager.createPacket(PacketType.Play.Server.ENTITY_TELEPORT)

        packet.integers.apply {
            write(0, id)
        }

        packet.doubles.apply {
            write(0, location.x)
            write(1, location.y - ARMOR_STAND_HEIGHT)
            write(2, location.z)
        }

        protocolManager.sendServerPacket(player, packet)
    }

    fun sendDestroyPacket(player: Player) {
        val packet = protocolManager.createPacket(PacketType.Play.Server.ENTITY_DESTROY)

        packet.integers.apply {
            write(0, 1)
        }

        packet.integerArrays.apply {
            write(0, arrayOf(1).toIntArray())
        }

        protocolManager.sendServerPacket(player, packet)
    }

    private inline fun <reified T : Any> createDataWatcherObject(index: Int) =
            WrappedDataWatcher.WrappedDataWatcherObject(index, WrappedDataWatcher.Registry.get(T::class.java))

}