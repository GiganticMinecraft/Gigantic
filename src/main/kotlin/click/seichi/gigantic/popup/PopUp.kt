package click.seichi.gigantic.popup

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.popup.PopUp.PopPattern
import click.seichi.gigantic.util.Random
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Entity
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector

/**
 * @author tar0ss
 */
class PopUp(
        private val text: String? = null,

        private val popPattern: PopPattern = PopPattern.STILL,
        /**
         * この値は[PopPattern]がSTILLの時のみ使用されます
         */
        private val duration: Long = 5L,
        private val popping: (ArmorStand) -> Unit = {}
) {
    enum class PopPattern {
        // 静止
        STILL,
        // 飛び出る
        POP,
        // 飛び出る（長く残る
        POP_LONG,
        ;
    }

    fun pop(location: Location, diffX: Double = 0.0, diffY: Double = 0.0, diffZ: Double = 0.0): ArmorStand {
        return location.world.spawn(location.clone().add(
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
                setGravity(true)
                if (text != null) {
                    isCustomNameVisible = true
                    customName = text
                } else {
                    isCustomNameVisible = false
                }
                isSmall = true
                when (popPattern) {
                    PopUp.PopPattern.STILL -> {
                        setGravity(false)
                    }
                    PopUp.PopPattern.POP ->
                        velocity = Vector(
                                Random.nextGaussian(variance = 0.03),
                                0.24,
                                Random.nextGaussian(variance = 0.03)
                        )
                    PopUp.PopPattern.POP_LONG ->
                        velocity = Vector(
                                Random.nextGaussian(variance = 0.03),
                                0.24,
                                Random.nextGaussian(variance = 0.03)
                        )
                }
                popping(this)
            }
        }.apply {
            if (popPattern == PopPattern.POP_LONG) {
                Bukkit.getScheduler().runTaskLater(Gigantic.PLUGIN, {
                    setGravity(false)
                }, 5L)
            }

            Bukkit.getScheduler().runTaskLater(Gigantic.PLUGIN, {
                if (!isValid) return@runTaskLater
                remove()
            }, when (popPattern) {
                PopUp.PopPattern.STILL -> duration
                PopUp.PopPattern.POP -> 5L
                PopUp.PopPattern.POP_LONG -> 15L
            }
            )
        }
    }

    fun follow(entity: Entity,
               meanX: Double = 0.0,
               meanY: Double = 0.0,
               meanZ: Double = 0.0,
               diffX: Double = 0.0,
               diffY: Double = 0.0,
               diffZ: Double = 0.0
    ): ArmorStand {
        return entity.world.spawn(entity.location.clone().add(
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
                if (text != null) {
                    isCustomNameVisible = true
                    customName = text
                } else {
                    isCustomNameVisible = false
                }
                popping(this)
            }
        }.apply {
            object : BukkitRunnable() {
                var t = 0L
                override fun run() {
                    if (isValid) {
                        remove()
                        cancel()
                        return
                    }
                    teleport(
                            entity.location.clone().add(
                                    meanX + Random.nextDouble() * diffX,
                                    meanY + Random.nextDouble() * diffY,
                                    meanZ + Random.nextDouble() * diffZ
                            )
                    )
                    t++
                    if (t > duration) {
                        remove()
                        cancel()
                    }
                }
            }.runTaskTimer(Gigantic.PLUGIN, 0L, 1L)
        }
    }

}