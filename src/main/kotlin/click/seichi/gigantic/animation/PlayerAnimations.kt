package click.seichi.gigantic.animation

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