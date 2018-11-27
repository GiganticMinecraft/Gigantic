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
     * @param event クリック時のInventoryClickEvent
     */
    fun onClick(player: Player, event: InventoryClickEvent)
}