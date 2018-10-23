package click.seichi.gigantic.message

import click.seichi.gigantic.Gigantic
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityRegainHealthEvent

/**
 * @author tar0ss
 */
class RegainHealthMessage(
        private val amount: Double
) : Message {

    override fun sendTo(player: Player) {
        if (amount <= 0) return
        val event = EntityRegainHealthEvent(player, amount, EntityRegainHealthEvent.RegainReason.CUSTOM)
        Gigantic.PLUGIN.server.pluginManager.callEvent(event)
        if (event.isCancelled) return
        val nextHealth = player.health + amount
        player.health = nextHealth.coerceIn(0.0, 20.0)
    }

}