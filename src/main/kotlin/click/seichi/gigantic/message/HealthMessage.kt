package click.seichi.gigantic.message

import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class HealthMessage(
        private val health: Double
) : Message {
    override fun sendTo(player: Player) {
        val nextHealth = when {
            health > 19.5 && 20.0 > health -> 19.5
            health > 0.0 && 0.5 > health -> 0.5
            else -> health
        }
        player.health = nextHealth
    }
}