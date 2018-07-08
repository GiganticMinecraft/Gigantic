package click.seichi.gigantic.player

import org.bukkit.Bukkit
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class GiganticPlayer(override val isFirstJoin: Boolean = false) : PlayerContainer() {

    val player: Player
        get() = Bukkit.getServer().getPlayer(uniqueId)

}