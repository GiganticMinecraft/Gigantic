package click.seichi.gigantic.animation.animations.effect

import click.seichi.gigantic.animation.Animation

/**
 * @author tar0ss
 */
object GeneralBreakAnimations {

    val EXPLOSION = Animation(0) { location, _ ->
        location.world.createExplosion(location, 0F, false)
    }

}