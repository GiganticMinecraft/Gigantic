package click.seichi.gigantic.item

import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent

/**
 * @author tar0ss
 */
interface HandItem : Button {

    /**
     * プレイヤーが行動したときに呼び出される
     *
     * @param player プレイヤー
     * @param event クリック時の[PlayerInteractEvent]
     * @return 何かしら実行した場合TRUE,それ以外はFALSE
     */
    fun tryInteract(player: Player, event: PlayerInteractEvent): Boolean = false

}