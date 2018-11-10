package click.seichi.gigantic.animation.animations

import click.seichi.gigantic.animation.Animation
import click.seichi.gigantic.extension.launchFireWorks

/**
 *
 * @author tar0ss
 */
object PlayerAnimations {

    val LAUNCH_FIREWORK = Animation(1) { location, _ ->
        location.launchFireWorks()
    }


}