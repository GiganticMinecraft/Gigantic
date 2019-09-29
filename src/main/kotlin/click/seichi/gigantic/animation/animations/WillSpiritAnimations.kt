package click.seichi.gigantic.animation.animations

import click.seichi.gigantic.animation.Animation
import click.seichi.gigantic.extension.spawnColoredParticle
import click.seichi.gigantic.extension.spawnColoredParticleSpherically
import click.seichi.gigantic.player.ToggleSetting
import click.seichi.gigantic.sound.sounds.WillSpiritSounds
import click.seichi.gigantic.spirit.spirits.WillSpirit
import click.seichi.gigantic.util.NoiseData
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Particle

/**
 * @author tar0ss
 */
object WillSpiritAnimations {

    val RENDER = { willSpirit: WillSpirit ->
        Animation(0) { location, _ ->
            val renderingData = willSpirit.willSize.renderingData
            val lifeExpectancy = willSpirit.lifeExpectancy
            val color = willSpirit.will.color
            if ((renderingData.beatTiming != 0 && lifeExpectancy % renderingData.beatTiming != 0L)) return@Animation

            Bukkit.getServer().onlinePlayers
                    .filter { it.isValid }
                    // targetと同じ場合はTRUE
                    .filter { ToggleSetting.SEE_OTHER_WILL_SPIRIT.getToggle(it) || (willSpirit.targetPlayer != null && willSpirit.targetPlayer == it) }
                    .forEach { player ->
                        player.spawnColoredParticleSpherically(
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

    val SENSE = { willSpirit: WillSpirit ->
        Animation(0) { location, _ ->
            val color = willSpirit.will.color

            Bukkit.getServer().onlinePlayers
                    .filter { it.isValid }
                    // targetと同じ場合はTRUE
                    .filter { ToggleSetting.SEE_OTHER_WILL_SPIRIT.getToggle(it) || (willSpirit.targetPlayer != null && willSpirit.targetPlayer == it) }
                    .forEach { player ->
                        player.spawnColoredParticle(
                                location,
                                color,
                                noiseData = NoiseData(0.01)
                        )
                    }
        }
    }

}