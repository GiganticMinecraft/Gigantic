package click.seichi.gigantic.message

import click.seichi.gigantic.Gigantic
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class WorldArmorStand(
        private val delay: Long,
        private val creator: (ArmorStand) -> Unit
) : Message {

    override fun sendTo(player: Player) {
        sendTo(player.location)
    }

    fun sendTo(location: Location) {
        spawn(location).run {
            Bukkit.getScheduler().runTaskLater(Gigantic.PLUGIN, {
                remove()
            }, delay)
        }
    }

    private fun spawn(location: Location) = location.world.spawn(location, ArmorStand::class.java) { creator(it) }
}