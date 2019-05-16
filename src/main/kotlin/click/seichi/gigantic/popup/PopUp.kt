package click.seichi.gigantic.popup

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.util.virtualtag.VanillaTag
import click.seichi.gigantic.util.virtualtag.VirtualTag
import org.bukkit.Location

/**
 * @author tar0ss
 * @author unicroak
 */
class PopUp(private val animation: PopAnimation,
            private val location: Location,
            private val text: String) {

    companion object {
        private const val SEND_DISTANCE = 32.0
    }

    fun pop() {
        location.world?.players
                ?.filter { player -> player.location.distanceSquared(location) < SEND_DISTANCE }
                ?.forEach { player ->
                    val virtualTag: VirtualTag = VanillaTag(location, text)

                    animation.animate(player, virtualTag, location)

                    Gigantic.PLUGIN.apply {
                        server.scheduler.runTaskLater(
                                this@apply,
                                { _ -> virtualTag.destroy() },
                                animation.lifetime
                        )
                    }
                }
    }

}