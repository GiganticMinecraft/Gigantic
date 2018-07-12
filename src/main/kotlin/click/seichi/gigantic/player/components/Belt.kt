package click.seichi.gigantic.player.components

import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

sealed class Belt {
    object MINE_BELT : Belt() {
        override val hookedItemMap: Map<Int, HookedItem> = mapOf(
                // TODO implements
        )

    }

    protected abstract val hookedItemMap: Map<Int, HookedItem>

    fun forEachIndexed(player: Player, action: (Int, ItemStack?) -> Unit) {
        hookedItemMap
                .map { it.key to it.value.getItemStack(player) }
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
