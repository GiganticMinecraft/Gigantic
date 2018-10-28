package click.seichi.gigantic.message

import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class HealthMessage(
        health: Double
) : Message {

    private val nextHealth = when {
        health > 19.5 && 20.0 > health -> 19.5
        health > 0.0 && 0.5 > health -> 0.5
        else -> health
    }

    override fun sendTo(player: Player) {
        player.health = nextHealth.coerceIn(0.0, 20.0)
    }
}