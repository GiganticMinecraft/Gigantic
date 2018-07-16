package click.seichi.gigantic.player.belt

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

abstract class Belt {

    protected abstract val hookedItemMap: Map<Int, HookedItem>

    private val slotRange = (0 until 9)

    fun apply(player: Player) {
        forEachIndexed(player) { index, itemStack ->
            player.inventory.setItem(index, itemStack ?: ItemStack(Material.AIR))
        }
    }

    fun apply(player: Player, slot: Int) {
        player.inventory.setItem(slot, hookedItemMap[slot]?.getItemStack(player) ?: ItemStack(Material.AIR))
    }

    private fun forEachIndexed(player: Player, action: (Int, ItemStack?) -> Unit) {
        slotRange.map { it to hookedItemMap[it]?.getItemStack(player) }
                .toMap()
                .forEach(action)
    }

    fun getHookedItem(slot: Int): HookedItem? {
        return hookedItemMap[slot]
    }

    interface HookedItem {

        fun getItemStack(player: Player): ItemStack?

        fun onClick(player: Player, event: InventoryClickEvent)

        fun onInteract(player: Player, event: PlayerInteractEvent)
    }
}
