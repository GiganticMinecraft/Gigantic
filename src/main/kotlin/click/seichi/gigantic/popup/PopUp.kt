package click.seichi.gigantic.popup

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.extension.noised
import click.seichi.gigantic.util.NoiseData
import org.bukkit.Location
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Entity

/**
 * @author tar0ss
 * @author unicroak
 */
sealed class PopUp(private val text: String?,
                   private val popping: (ArmorStand) -> Unit) {

    abstract val lifeTime: Long

    open fun pop(location: Location, noise: NoiseData = NoiseData()): ArmorStand {
        return location.world.spawn(location.noised(noise), ArmorStand::class.java) {
            it.modelAsPop(text, popping)
            Gigantic.PLUGIN.apply { server.scheduler.runTaskLater(this@apply, { _ -> if (it.isValid) it.remove() }, lifeTime) }
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
                ArmorStand::class.java
        ) { it.modelAsPop(text, popping) }

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

    private fun ArmorStand.modelAsPop(text: String?, popping: (ArmorStand) -> Unit) {
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

        popping(this)
    }

}

class StillPopUp(text: String? = null,
                 popping: (ArmorStand) -> Unit = {},
                 override val lifeTime: Long) : PopUp(text, popping)

class SimplePopUp(text: String? = null,
                  popping: (ArmorStand) -> Unit = {}) : PopUp(text, popping) {

    override val lifeTime = 5L

}

class LongPopUp(text: String? = null,
                popping: (ArmorStand) -> Unit = {}) : PopUp(text, popping) {

    override val lifeTime = 15L

    override fun pop(location: Location, noise: NoiseData): ArmorStand {
        return super.pop(location, noise).also { armorStand ->
            Gigantic.PLUGIN.apply { server.scheduler.runTaskLater(this@apply, { _ -> armorStand.setGravity(false) }, 5L) }
        }
    }

}