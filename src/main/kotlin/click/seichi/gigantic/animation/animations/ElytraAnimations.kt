package click.seichi.gigantic.animation.animations

import click.seichi.gigantic.animation.Animation
import org.bukkit.Particle

/**
 * @author tar0ss
 */
object ElytraAnimations {

    val CHARGING = Animation(0) { location, _ ->
        location.world.spawnParticle(Particle.SMOKE_NORMAL, location, 1,
                0.2, 0.2, 0.2
        )
    }

    val READY_TO_JUMP = Animation(0) { location, _ ->
        location.world.spawnParticle(Particle.FLAME, location, 1,
                0.4, 0.1, 0.4
        )
    }

    val JUMP = Animation(0) { location, _ ->
        location.world.spawnParticle(Particle.CLOUD, location, 30,
                0.5, 0.5, 0.5
        )
    }
}