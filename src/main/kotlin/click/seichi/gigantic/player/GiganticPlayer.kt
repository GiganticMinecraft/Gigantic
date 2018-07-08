package click.seichi.gigantic.player

import click.seichi.gigantic.player.components.PlayerContainer
import org.bukkit.Bukkit
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class GiganticPlayer(override val isFirstJoin: Boolean = false) : PlayerContainer() {

    val player: Player
        get() = Bukkit.getServer().getPlayer(uniqueId)

}