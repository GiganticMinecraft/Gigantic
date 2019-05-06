package click.seichi.gigantic.util.virtualtag

import org.bukkit.entity.Player
import org.bukkit.util.Vector

/**
 * @author unicroak
 */
interface VirtualTag {

    fun show(player: Player)

    fun moveTo(player: Player, delta: Vector)

    fun destroy(player: Player)

}