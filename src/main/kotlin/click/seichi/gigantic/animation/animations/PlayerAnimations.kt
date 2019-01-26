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

}