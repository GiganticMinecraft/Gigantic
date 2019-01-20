package click.seichi.gigantic.animation.animations

import click.seichi.gigantic.animation.Animation
import click.seichi.gigantic.extension.launchFireWorks
import click.seichi.gigantic.util.Random
import org.bukkit.Particle

/**
 *
 * @author tar0ss
 */
object PlayerAnimations {

    val LAUNCH_FIREWORK = Animation(0) { location, _ ->
        location.launchFireWorks()
    }

    val ON_CUT = Animation(0) { location, _ ->
        location.world.spawnParticle(Particle.VILLAGER_HAPPY, location, 1,
                Random.nextGaussian(0.0, 0.3),
                Random.nextGaussian(0.0, 0.3),
                Random.nextGaussian(0.0, 0.3)
        )
    }

    val ON_CONDENSE_WATER = Animation(0) { location, _ ->
        if (Random.nextDouble() > 0.15) return@Animation
        location.world.spawnParticle(Particle.WATER_SPLASH, location, 2,
                Random.nextGaussian(0.0, 0.3),
                Random.nextGaussian(0.0, 0.3),
                Random.nextGaussian(0.0, 0.3)
        )
    }

    val ON_CONDENSE_LAVA = Animation(0) { location, _ ->
        if (Random.nextDouble() > 0.2) return@Animation
        location.world.spawnParticle(Particle.LAVA, location, 1,
                Random.nextGaussian(0.0, 0.3),
                Random.nextGaussian(0.0, 0.3),
                Random.nextGaussian(0.0, 0.3)
        )
    }

}