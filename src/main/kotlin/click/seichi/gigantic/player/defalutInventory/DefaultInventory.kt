package click.seichi.gigantic.player.defalutInventory

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
abstract class DefaultInventory {

    protected abstract val slotItemMap: Map<Int, SlotItem>

    private val slotRange = (9 until 36)

    fun apply(player: Player) {
        forEachIndexed(player) { index, itemStack ->
            player.inventory.setItem(index, itemStack ?: ItemStack(Material.AIR))
        }
    }

    private fun forEachIndexed(player: Player, action: (Int, ItemStack?) -> Unit) {
        slotRange.map { it to slotItemMap[it]?.getItemStack(player) }
                .toMap()
                .forEach(action)
    }

    fun getSlotItem(slot: Int): SlotItem? {
        return slotItemMap[slot]
    }

    interface SlotItem {

        fun getItemStack(player: Player): ItemStack?

        fun onClick(player: Player, event: InventoryClickEvent)
    }
}