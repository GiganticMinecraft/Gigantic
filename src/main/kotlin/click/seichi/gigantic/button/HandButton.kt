package click.seichi.gigantic.button

import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent

/**
 * @author tar0ss
 */
interface HandButton : Button {

    // プレイヤーが行動したときに呼び出される
    fun onInteract(player: Player, event: PlayerInteractEvent)

}