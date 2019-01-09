package click.seichi.gigantic.popup

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.extension.noised
import click.seichi.gigantic.util.NoiseData
import click.seichi.gigantic.util.Random
import org.bukkit.Location
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Entity
import org.bukkit.util.Vector

/**
 * @author tar0ss
 * @author unicroak
 */
sealed class PopUp(private val text: String?,
                   private val customizeOnPop: ArmorStand.() -> Unit = {}) {
    // HACK: [customizeOnPop] should not exist

    abstract val lifeTime: Long

    fun pop(location: Location, noise: NoiseData = NoiseData()): ArmorStand {
        return location.world.spawn(location.noised(noise), ArmorStand::class.java) {
            it.buildAsPop(text)
            Gigantic.PLUGIN.apply { server.scheduler.runTaskLater(this@apply, { _ -> if (it.isValid) it.remove() }, lifeTime) }
            customizeOnPop(it)
        }
    }

    fun follow(entity: Entity,
               meanX: Double = 0.0,
               meanY: Double = 0.0,
               meanZ: Double = 0.0,
               noise: NoiseData = NoiseData()): ArmorStand {
        val entityLocation = entity.location
        val armorStand = entity.world.spawn(
                entityLocation.noised(noise).add(meanX, meanY, meanZ),
                ArmorStand::class.java)
        armorStand.buildAsPop(text)

        fun followEntity(ticks: Long) {
            if (!armorStand.isValid || ticks > lifeTime) {
                armorStand.remove()
            } else {
                armorStand.teleport(entityLocation.noised(noise).add(meanX, meanY, meanZ))
                Gigantic.PLUGIN.apply { server.scheduler.runTaskLater(this@apply, { _ -> followEntity(ticks + 1) }, 1L) }
            }
        }

        followEntity(0L)

        return armorStand
    }

    private fun ArmorStand.buildAsPop(text: String?) {
        isVisible = false
        setBasePlate(false)
        setArms(true)
        isMarker = true
        isInvulnerable = true
        canPickupItems = false
        setGravity(true)
        isSmall = true
        customName = text ?: ""
        isCustomNameVisible = text != null
    }

}

class StillPopUp(text: String? = null,
                 override val lifeTime: Long) : PopUp(text, { setGravity(false) })

class SimplePopUp(text: String? = null) : PopUp(text, {
    velocity = Vector(
            Random.nextGaussian(variance = 0.03),
            0.24,
            Random.nextGaussian(variance = 0.03)
    )
}) {

    override val lifeTime = 5L

}

class LongPopUp(text: String? = null) : PopUp(text, {
    velocity = Vector(
            Random.nextGaussian(variance = 0.03),
            0.24,
            Random.nextGaussian(variance = 0.03)
    )
    Gigantic.PLUGIN.apply { server.scheduler.runTaskLater(this@apply, { _ -> setGravity(false) }, 5L) }
}) {

    override val lifeTime = 15L

}