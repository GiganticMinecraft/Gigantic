package click.seichi.gigantic.player

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.message.messages.HomeMessages
import org.bukkit.Bukkit
import org.bukkit.Location
import java.util.*

/**
 * @author tar0ss
 */
class Home(
        val id: Int,
        val serverId: Int,
        val worldId: UUID,
        val x: Double,
        val y: Double,
        val z: Double,
        var name: String = HomeMessages.DEFAULT_NAME.asSafety(Gigantic.DEFAULT_LOCALE) + "${id.plus(1)}",
        var teleportOnSwitch: Boolean = false
) {

    constructor(
            id: Int,
            location: Location
    ) : this(
            id,
            Gigantic.SERVER_ID,
            location.world!!.uid,
            location.x,
            location.y,
            location.z
    )


    val isValid by lazy { serverId == Gigantic.SERVER_ID && Bukkit.getServer().getWorld(worldId) != null }

    val location by lazy { Location(Bukkit.getServer().getWorld(worldId), x, y, z) }
}
