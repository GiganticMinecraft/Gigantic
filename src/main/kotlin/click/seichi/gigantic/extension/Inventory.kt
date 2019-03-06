package click.seichi.gigantic.extension

import click.seichi.gigantic.item.Button
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */


fun Inventory.setItem(player: Player, slot: Int, button: Button) {
    setItem(slot, button.toShownItemStack(player))
}

fun Inventory.setItemAsync(player: Player, slot: Int, button: Button) {
    setItemAsync(slot, button.toShownItemStack(player))
}

fun Inventory.setItemAsync(slot: Int, itemStack: ItemStack?) {
    runTaskAsync { setItem(slot, itemStack) }
}