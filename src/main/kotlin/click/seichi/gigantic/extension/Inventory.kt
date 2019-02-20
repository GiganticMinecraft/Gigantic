package click.seichi.gigantic.extension

import click.seichi.gigantic.item.Button
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

/**
 * @author tar0ss
 */


fun Inventory.setItem(player: Player, slot: Int, button: Button) {
    setItem(slot, button.toShownItemStack(player))
}