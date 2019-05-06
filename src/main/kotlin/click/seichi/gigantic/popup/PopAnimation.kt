package click.seichi.gigantic.popup

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.util.Random
import click.seichi.gigantic.util.virtualtag.VirtualTag
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.util.Vector

/**
 * @author unicroak
 * @author tar0ss
 */
sealed class PopAnimation(val lifetime: Long) {

    abstract fun animate(player: Player, virtualTag: VirtualTag, location: Location)

}

class StillAnimation(lifetime: Long) : PopAnimation(lifetime) {

    override fun animate(player: Player, virtualTag: VirtualTag, location: Location) {
        virtualTag.show()
    }

}

object SimpleAnimation : PopAnimation(5L) {

    override fun animate(player: Player, virtualTag: VirtualTag, location: Location) {
        virtualTag.show()
        virtualTag.push(Vector(
                Random.nextGaussian(variance = 0.03),
                0.24,
                Random.nextGaussian(variance = 0.03)
        ))
    }

}

object LongAnimation : PopAnimation(15L) {

    override fun animate(player: Player, virtualTag: VirtualTag, location: Location) {
        virtualTag.show()
        virtualTag.push(Vector(
                Random.nextGaussian(variance = 0.03),
                0.24,
                Random.nextGaussian(variance = 0.03)
        ))

        Gigantic.PLUGIN.apply {
            server.scheduler.runTaskLater(this@apply, { _ -> virtualTag.push(Vector(0.0, 1.0, 0.0)) }, 5L)
        }
    }

}