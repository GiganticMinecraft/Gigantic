package click.seichi.gigantic.item

import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent

/**
 * @author tar0ss
 */
interface HandItem : Button {

    // プレイヤーが行動したときに呼び出される
    fun onInteract(player: Player, event: PlayerInteractEvent)

}