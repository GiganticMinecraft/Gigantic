package click.seichi.gigantic.message

import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class LevelMessage(
        private val level: Int,
        private val exp: Float
) : Message {

    override fun sendTo(player: Player) {
        player.level = level
        player.exp = exp.coerceIn(0.0F, 1.0F)
    }
}