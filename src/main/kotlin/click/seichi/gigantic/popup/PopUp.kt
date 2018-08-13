package click.seichi.gigantic.popup

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.util.Random
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.ArmorStand

/**
 * @author tar0ss
 */
class PopUp(
        private val text: String,
        private val duration: Long
) {

    fun pop(location: Location, diffX: Double = 0.0, diffY: Double = 0.0, diffZ: Double = 0.0) {
        location.world.spawn(location.clone().add(
                Random.nextDouble() * diffX,
                Random.nextDouble() * diffY,
                Random.nextDouble() * diffZ
        ), ArmorStand::class.java) {
            it.run {
                isVisible = false
                setBasePlate(false)
                setArms(true)
                isMarker = true
                isInvulnerable = true
                canPickupItems = false
                setGravity(false)
                isCustomNameVisible = true
                customName = "$text"
            }
        }.run {
            Bukkit.getScheduler().runTaskLater(Gigantic.PLUGIN, {
                remove()
            }, duration)
        }
    }
}