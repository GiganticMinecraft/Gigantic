package click.seichi.gigantic.animation

import org.bukkit.Particle

/**
 * @author tar0ss
 */
object SkillAnimations {
    val MINE_BURST_ON_BREAK = Animation(0) { location, _ ->
        location.world.spawnParticle(Particle.SPELL_INSTANT, location, 10)
    }
}