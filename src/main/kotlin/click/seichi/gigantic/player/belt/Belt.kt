package click.seichi.gigantic.player.belt

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.inventory.ItemStack

abstract class Belt {

    companion object {
        const val TOOL_SLOT = 0
    }

    protected abstract val hookedItemMap: Map<Int, HookedItem>

    protected abstract val hookedTool: HookedItem

    private val slotRange = (0 until 9)

    fun update(player: Player) {
        player.inventory.setItem(TOOL_SLOT, hookedTool.getItemStack(player) ?: ItemStack(Material.AIR))
        forEachIndexed(player) { index, itemStack ->
            player.inventory.setItem(index, itemStack ?: ItemStack(Material.AIR))
        }
    }

    fun update(player: Player, slot: Int) {
        player.inventory.setItem(slot, hookedItemMap[slot]?.getItemStack(player) ?: ItemStack(Material.AIR))
    }

    private fun forEachIndexed(player: Player, action: (Int, ItemStack?) -> Unit) {
        slotRange.map { it to if (it == TOOL_SLOT) hookedTool else hookedItemMap[it] }
                .map { it.first to it.second?.getItemStack(player) }
                .toMap()
                .forEach(action)
    }

    fun getHookedItem(slot: Int): HookedItem? {
        return hookedItemMap[slot]
    }

    interface HookedItem {

        fun getItemStack(player: Player): ItemStack?

        fun onClick(player: Player, event: InventoryClickEvent)

        fun onItemHeld(player: Player, event: PlayerItemHeldEvent)
    }
}
