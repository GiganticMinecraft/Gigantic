package click.seichi.gigantic.animation.animations

import click.seichi.gigantic.animation.Animation
import org.bukkit.Particle

/**
 * @author tar0ss
 */
object ElytraAnimations {

    val JUMP = Animation(0) { location, _ ->
        location.world.spawnParticle(Particle.CLOUD, location, 30,
                0.5, 0.5, 0.5
        )
    }
}