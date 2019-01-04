package click.seichi.gigantic.item

import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

/**
 * @author Mon_chi
 * @author tar0ss
 */
interface Button : Item {

    /**
     * クリック時に実行されます
     *
     * @param player プレイヤー
     * @param event クリック時の[InventoryClickEvent]
     * @return 何かしら実行した場合TRUE,それ以外はFALSE
     */
    fun onClick(player: Player, event: InventoryClickEvent): Boolean
}