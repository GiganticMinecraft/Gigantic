package click.seichi.gigantic.animation.animations.effect

import click.seichi.gigantic.animation.Animation
import org.bukkit.Effect
import org.bukkit.Material
import org.bukkit.Particle

/**
 * @author tar0ss
 */
object ApostolAnimations {

    val EXPLOSION = Animation(0) { location, _ ->
        location.world.createExplosion(location, 0F, false)
    }

    val BLIZZARD = Animation(0) { location, _ ->
        location.world.spawnParticle(Particle.BLOCK_CRACK, location, 1, Material.PACKED_ICE.createBlockData())
        location.world.playEffect(location, Effect.STEP_SOUND, Material.PACKED_ICE)
    }
}