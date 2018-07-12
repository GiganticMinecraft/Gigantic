package click.seichi.gigantic.extension

import org.bukkit.event.inventory.InventoryClickEvent

/**
 * @author tar0ss
 */

val InventoryClickEvent.isBeltSlot: Boolean
    get() = slot in (0 until 9)