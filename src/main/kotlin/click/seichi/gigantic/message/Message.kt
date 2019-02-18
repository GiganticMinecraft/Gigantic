package click.seichi.gigantic.message

import org.bukkit.Bukkit
import org.bukkit.entity.Player

/**
 * @author unicroak
 * @author tar0ss
 *
 */
interface Message {

    fun sendTo(player: Player)

    fun broadcast() {
        Bukkit.getServer().onlinePlayers
                .filterNotNull()
                .filter { it.isValid }
                .forEach {
                    sendTo(it)
                }
    }

}