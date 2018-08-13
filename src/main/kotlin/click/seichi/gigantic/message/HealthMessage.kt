package click.seichi.gigantic.message

import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class HealthMessage(
        private val health: Double
) : Message {
    override fun sendTo(player: Player) {
        player.health = health.coerceIn(0.0, 20.0)
    }
}