package click.seichi.gigantic.button

import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * @author Mon_chi
 * @author tar0ss
 */
interface Button {

    /**
     * ItemStackを取得します
     *
     * @param player Menuを開いているPlayer
     */
    fun getItemStack(player: Player): ItemStack?

    /**
     * クリック時に実行されます
     *
     * @param event クリック時のInventoryClickEvent
     */
    fun onClick(player: Player, event: InventoryClickEvent)
}