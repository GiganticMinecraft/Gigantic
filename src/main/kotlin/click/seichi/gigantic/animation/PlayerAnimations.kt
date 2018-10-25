package click.seichi.gigantic.animation

import click.seichi.gigantic.extension.launchFireWorks

/**
 *
 * @author tar0ss
 */
object PlayerAnimations {

//    val LEVEL_UP = Animation(60) { location, _ ->
//        location.world.spawnParticle(Particle.REDSTONE, location, 10, Particle.DustOptions(Random.nextColor(), 1.0F))
//    }

    val LAUNCH_FIREWORK = Animation(1) { location, _ ->
        location.launchFireWorks()
    }


}