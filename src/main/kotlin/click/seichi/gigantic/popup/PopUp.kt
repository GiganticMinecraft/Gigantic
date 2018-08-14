package click.seichi.gigantic.popup

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.schedule.Scheduler
import click.seichi.gigantic.util.Random
import io.reactivex.Observable
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Entity
import java.util.concurrent.TimeUnit

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
                customName = text
            }
        }.run {
            Bukkit.getScheduler().runTaskLater(Gigantic.PLUGIN, {
                remove()
            }, duration)
        }
    }

    fun follow(entity: Entity,
               meanX: Double = 0.0,
               meanY: Double = 0.0,
               meanZ: Double = 0.0,
               diffX: Double = 0.0,
               diffY: Double = 0.0,
               diffZ: Double = 0.0
    ) {
        val uniqueId = entity.uniqueId ?: return
        entity.world.spawn(entity.location.clone().add(
                meanX + Random.nextDouble() * diffX,
                meanY + Random.nextDouble() * diffY,
                meanZ + Random.nextDouble() * diffZ
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
                customName = text
            }
        }.run {
            Observable.interval(50L, TimeUnit.MILLISECONDS)
                    .observeOn(Scheduler(Gigantic.PLUGIN, Bukkit.getScheduler()))
                    .take(duration)
                    .subscribe({
                        val e = Bukkit.getEntity(uniqueId) ?: return@subscribe
                        teleport(
                                e.location.clone().add(
                                        meanX + Random.nextDouble() * diffX,
                                        meanY + Random.nextDouble() * diffY,
                                        meanZ + Random.nextDouble() * diffZ
                                )
                        )
                    }, {}, {
                        remove()
                    })
        }
    }

}