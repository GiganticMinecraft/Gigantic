package click.seichi.gigantic.message

import click.seichi.gigantic.Gigantic
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityRegainHealthEvent

/**
 * @author tar0ss
 */
class RegainHealthMessage(
        private val amount: Double,
        private val reason: EntityRegainHealthEvent.RegainReason
) : Message {

    override fun sendTo(player: Player) {
        val event = EntityRegainHealthEvent(player, amount, reason)
        Gigantic.PLUGIN.server.pluginManager.callEvent(
                event
        )
        if (event.isCancelled) return
        player.health += amount
    }

}