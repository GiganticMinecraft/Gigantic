package click.seichi.gigantic.animation.animations.effect

import click.seichi.gigantic.animation.Animation
import org.bukkit.Particle

/**
 * @author tar0ss
 */
object GeneralBreakAnimations {

    val EXPLOSION = Animation(0) { location, _ ->
        location.world.spawnParticle(Particle.EXPLOSION_NORMAL, location, 1)
    }

    val BLIZZARD = Animation(0) { location, _ ->
        location.world.spawnParticle(Particle.SNOWBALL, location, 1)
    }

    val MAGIC = Animation(0) { location, _ ->
        location.world.spawnParticle(Particle.NOTE, location, 1)
    }

    val FLAME = Animation(0) { location, _ ->
        location.world.spawnParticle(Particle.FLAME, location, 2)
    }

    val WITCH_SCENT = Animation(0) { location, _ ->
        location.world.spawnParticle(Particle.SPELL_WITCH, location, 1)
    }

    val EXPEL = Animation(0) { location, _ ->
        location.world.spawnParticle(Particle.SPIT, location, 1)
    }

}