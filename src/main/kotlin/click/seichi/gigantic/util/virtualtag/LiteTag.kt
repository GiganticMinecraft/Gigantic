package click.seichi.gigantic.util.virtualtag

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.util.Random
import com.comphenix.protocol.PacketType
import com.comphenix.protocol.wrappers.WrappedChatComponent
import com.comphenix.protocol.wrappers.WrappedDataWatcher
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.util.Vector
import java.util.*

/**
 * @author unicroak
 */
class LiteTag(var location: Location,
              private val text: String,
              private val id: Int = Random.nextInt(Int.MAX_VALUE),
              private val uuid: UUID = UUID.randomUUID()) : VirtualTag {

    companion object {
        private const val BIT_IS_SMALL = 0x01
        private const val BIT_NO_BASE_PLATE = 0x08
        private const val BIT_SET_MARKER = 0x10
        private const val BIT_INVISIBLE = 0x20
        private const val ARMOR_STAND_ID = 1

        private val protocolManager = Gigantic.PROTOCOL_MG
    }

    override fun show(player: Player) {
        val packet = protocolManager.createPacket(PacketType.Play.Server.SPAWN_ENTITY_LIVING)

        packet.integers.apply {
            write(0, id)
            write(1, ARMOR_STAND_ID)
        }

        packet.doubles.apply {
            write(0, location.x)
            write(1, location.y)
            write(2, location.z)
        }

        packet.uuiDs.apply {
            write(0, uuid)
        }

        packet.dataWatcherModifier.writeDefaults()
        packet.dataWatcherModifier.write(
                0,
                WrappedDataWatcher().apply {
                    setObject(createDataWatcherObject(2, WrappedDataWatcher.Registry.getChatComponentSerializer(true)), Optional.of(WrappedChatComponent.fromText(text).handle))
                    setObject(createDataWatcherObject<Byte>(0), (BIT_INVISIBLE).toByte())
                    setObject(createDataWatcherObject<Byte>(11), (BIT_IS_SMALL + BIT_NO_BASE_PLATE + BIT_SET_MARKER).toByte())
                    setObject(createDataWatcherObject<Boolean>(3), true) // NAME_VISIBLE
                    setObject(createDataWatcherObject<Boolean>(4), true) // SILENT
                }
        )

        protocolManager.sendServerPacket(player, packet)
    }

    override fun moveTo(player: Player, delta: Vector) {
        val packet = protocolManager.createPacket(PacketType.Play.Server.REL_ENTITY_MOVE)

        packet.integers.apply {
            write(0, id)
            write(1, (delta.x * 4096).toInt())
            write(2, (delta.y * 4096).toInt())
            write(3, (delta.z * 4096).toInt())
        }

        protocolManager.sendServerPacket(player, packet)
    }

    override fun destroy(player: Player) {
        val packet = protocolManager.createPacket(PacketType.Play.Server.ENTITY_DESTROY)

        packet.integerArrays.apply {
            write(0, arrayOf(id).toIntArray())
        }

        protocolManager.sendServerPacket(player, packet)
    }

    private inline fun <reified T : Any> createDataWatcherObject(index: Int) =
            WrappedDataWatcher.WrappedDataWatcherObject(index, WrappedDataWatcher.Registry.get(T::class.java))


    private fun createDataWatcherObject(index: Int, serializer: WrappedDataWatcher.Serializer): WrappedDataWatcher.WrappedDataWatcherObject =
            WrappedDataWatcher.WrappedDataWatcherObject(index, serializer)

}