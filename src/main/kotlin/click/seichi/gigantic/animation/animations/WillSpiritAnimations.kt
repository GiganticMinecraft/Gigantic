package click.seichi.gigantic.animation.animations

import click.seichi.gigantic.animation.Animation
import click.seichi.gigantic.extension.spawnColoredParticle
import click.seichi.gigantic.extension.spawnColoredParticleSpherically
import click.seichi.gigantic.sound.sounds.WillSpiritSounds
import click.seichi.gigantic.util.NoiseData
import click.seichi.gigantic.will.WillRenderingData
import org.bukkit.Color
import org.bukkit.Particle

/**
 * @author tar0ss
 */
object WillSpiritAnimations {

    val RENDER = { renderingData: WillRenderingData, color: Color, lifeExpectancy: Long ->
        Animation(0) { location, _ ->
            if ((renderingData.beatTiming != 0 && lifeExpectancy % renderingData.beatTiming != 0L)) return@Animation
            location.world?.spawnColoredParticleSpherically(
                    location,
                    color,
                    if (lifeExpectancy < 10 * 20) {
                        renderingData.min
                    } else {
                        renderingData.max
                    },
                    renderingData.radius
            )
        }
    }

    val PRE_SENSE = { color: Color ->
        Animation(65) { location, tick ->
            if (tick == 0L) {
                WillSpiritSounds.PRE_SENSE.play(location)
            }

            if (tick == 60L) {
                WillSpiritSounds.DEATH.play(location)
            }

            if (tick > 60L)
                location.world?.spawnParticle(Particle.END_ROD, location, 5, 0.0, 0.0, 0.0, 0.06)
            else
                location.world?.spawnColoredParticleSpherically(
                        location,
                        color,
                        3,
                        0.14
                )
        }
    }

    val PRE_SENSE_COMPLETE = { color: Color ->
        Animation(125) { location, tick ->
            if (tick == 0L) {
                WillSpiritSounds.PRE_SENSE.play(location)
            }

            if (tick == 60L) {
                WillSpiritSounds.PRE_SENSE_COMPLETE.play(location)
            }

            if (tick > 60L)
                location.world?.spawnColoredParticleSpherically(
                        location,
                        color,
                        15,
                        1.5
                )
            else
                location.world?.spawnColoredParticleSpherically(
                        location,
                        color,
                        3,
                        0.14
                )
        }
    }

    val SENSE = { color: Color ->
        Animation(0) { location, _ ->
            location.world?.spawnColoredParticle(
                    location,
                    color,
                    noiseData = NoiseData(0.01)
            )
        }
    }

}