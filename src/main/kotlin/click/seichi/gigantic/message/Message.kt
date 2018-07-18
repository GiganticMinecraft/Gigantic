package click.seichi.gigantic.message

import org.bukkit.entity.Player

/**
 * @author unicroak
 */
interface Message {

    fun sendTo(player: Player)

}