package click.seichi.gigantic.animation.animations

import click.seichi.gigantic.animation.Animation
import click.seichi.gigantic.extension.spawnColoredParticle
import click.seichi.gigantic.util.Random
import org.bukkit.Color
import org.bukkit.Particle

/**
 * @author tar0ss
 */
object SkillAnimations {

    val MINE_BURST_ON_BREAK = Animation(0) { location, _ ->
        location.world?.spawnParticle(Particle.SPELL_MOB, location, 10,
                Random.nextGaussian(0.0, 0.5),
                Random.nextGaussian(0.0, 0.5),
                Random.nextGaussian(0.0, 0.5)
        )
    }

    val FLASH_FIRE = Animation(0) { location, _ ->
        location.world?.spawnParticle(Particle.SPELL_INSTANT, location, 200,
                Random.nextGaussian(0.0, 0.5),
                Random.nextGaussian(0.0, 0.5),
                Random.nextGaussian(0.0, 0.5)
        )

    }

    val HEAL = Animation(5) { location, ticks ->
        if (ticks == 0L)
            location.world?.spawnParticle(Particle.HEART, location, 1)
        location.world?.spawnColoredParticle(location, Color.fromRGB(204, 0, 0), 1)
    }

    val TOTEM_PIECE = Animation(60) { location, _ ->
        location.world?.spawnParticle(Particle.TOTEM, location.clone().add(
                0.0, -0.3, 0.0
        ), 1,
                Random.nextGaussian(0.0, 0.07),
                Random.nextGaussian(0.0, 0.07),
                Random.nextGaussian(0.0, 0.07), 0.1)
    }

}