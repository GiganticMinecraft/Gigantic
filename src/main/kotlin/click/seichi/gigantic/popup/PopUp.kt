package click.seichi.gigantic.popup

import click.seichi.gigantic.Gigantic
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.ArmorStand

/**
 * @author tar0ss
 */
class PopUp(
        private val text: String,
        private val delay: Long
) {

    fun pop(location: Location) {
        location.world.spawn(location, ArmorStand::class.java) {
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
            }, delay)
        }
    }
}