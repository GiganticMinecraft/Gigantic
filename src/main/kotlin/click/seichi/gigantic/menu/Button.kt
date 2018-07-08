package click.seichi.gigantic.menu

import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
interface Button {

    /**
     * Menuに表示されるItemStackを取得します
     *
     * @param player Menuを開いているPlayer
     */
    fun getItemStack(player: Player): ItemStack?

    /**
     * Buttonクリック時に実行されます
     *
     * @param event クリック時のInventoryClickEvent
     */
    fun onClick(player: Player, event: InventoryClickEvent)
}