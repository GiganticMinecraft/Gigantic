package click.seichi.gigantic.listener

import click.seichi.gigantic.config.Config
import click.seichi.gigantic.player.Defaults
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.util.Vector

/**
 * @author tar0ss
 */
class ElytraListener : Listener {

    private val speed = Config.ELYTRA_SPEED_MULTIPLIER.times(Defaults.ELYTRA_BASE_SPEED)


    @EventHandler(ignoreCancelled = true)
    fun onMove(event: PlayerMoveEvent) {
        val player = event.player ?: return
        if (!player.isGliding) return
        // 飛行中の加速
        val unitVector = Vector(0.0, player.location.direction.y, 0.0)
        player.velocity = player.velocity.add(unitVector.multiply(speed))
    }

}