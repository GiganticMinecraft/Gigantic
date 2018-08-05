package click.seichi.gigantic.button

import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerItemHeldEvent

/**
 * @author tar0ss
 */
interface HotButton : Button {

    // プレイヤーが持っている物を変更したときに実行される
    fun onItemHeld(player: Player, event: PlayerItemHeldEvent)

}