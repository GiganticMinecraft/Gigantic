package click.seichi.gigantic.popup

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.util.VirtualTag
import org.bukkit.Location

/**
 * @author tar0ss
 * @author unicroak
 */
class PopUp(private val animation: PopAnimation,
            private val location: Location,
            text: String) {

    companion object {
        private const val SEND_DISTANCE = 32.0
    }

    private val virtualTag = VirtualTag(location, text)

    fun pop() {
        location.world.players
                .filter { player -> player.location.distanceSquared(location) < SEND_DISTANCE }
                .forEach { player ->
                    animation.animate(player, virtualTag, location)

                    Gigantic.PLUGIN.apply {
                        server.scheduler.runTaskLater(
                                this@apply,
                                { _ ->
                                    if (player.isValid) virtualTag.sendDestroyPacket(player)
                                },
                                animation.lifetime
                        )
                    }
                }
    }

}