package click.seichi.gigantic.animation.animations

import click.seichi.gigantic.animation.Animation
import click.seichi.gigantic.extension.spawnColoredParticle
import click.seichi.gigantic.extension.spawnColoredParticleSpherically
import click.seichi.gigantic.util.NoiseData
import org.bukkit.Color
import org.bukkit.Particle

/**
 * @author tar0ss
 */
object MonsterSpiritAnimations {

    val AMBIENT_EXHAUST = { color: Color ->
        Animation(8) { location, _ ->
            location.world.spawnColoredParticle(
                    location,
                    color,
                    noiseData = NoiseData(0.01)
            )
        }
    }

    val AMBIENT = { color: Color ->
        Animation(1) { location, _ ->
            location.world.spawnColoredParticleSpherically(
                    location,
                    color,
                    count = 5,
                    radius = 2.5
            )
        }
    }

    val WAKE = Animation(1) { location, _ ->
        location.world.spawnParticle(
                Particle.SMOKE_NORMAL,
                location,
                2
        )
    }

    val ATTACK_READY = { color: Color ->
        Animation(20) { location, _ ->
            location.world.spawnColoredParticle(
                    location,
                    color,
                    noiseData = NoiseData(0.01)
            )
        }
    }

}