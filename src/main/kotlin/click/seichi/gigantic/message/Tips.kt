package click.seichi.gigantic.message

import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
enum class Tips(
        private val linedMessage: LinedChatMessage
) {


    ;

    fun sendTo(player: Player) = linedMessage.sendTo(player)
}