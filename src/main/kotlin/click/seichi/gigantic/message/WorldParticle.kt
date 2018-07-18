package click.seichi.gigantic.message

import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class WorldParticle(private val particle: Particle, private val count: Int) : Message {

    override fun sendTo(player: Player) {
        player.spawnParticle(particle, player.location, count)
    }

    fun sendTo(location: Location) {
        location.world.spawnParticle(particle, location, count)
    }
}