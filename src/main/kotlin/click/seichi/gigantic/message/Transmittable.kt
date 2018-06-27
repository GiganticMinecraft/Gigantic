package click.seichi.gigantic.message

import org.bukkit.entity.Player

/**
 * @author unicroak
 * @author tar0ss
 */
interface Transmittable {
    fun sendTo(receiver: Player, vararg arguments: Any)
}