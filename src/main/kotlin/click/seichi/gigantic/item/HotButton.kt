package click.seichi.gigantic.item

import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerItemHeldEvent

/**
 * @author tar0ss
 */
interface HotButton : Button {

    // プレイヤーが持っている物を変更したときに実行される
    fun onItemHeld(player: Player, event: PlayerItemHeldEvent)

}