package click.seichi.gigantic.bag

import click.seichi.gigantic.extension.setItemAsync
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.menu.Menu
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
abstract class Bag {

    private val buttonMap = mutableMapOf<Int, Button>()

    private val slotRange = (9 until 36)

    protected fun registerButton(slot: Int, button: Button) {
        buttonMap[slot] = button
    }

    fun carry(player: Player) {
        val holder = player.openInventory.topInventory.holder
        if (holder is Menu) holder.reopen(player)
        forEachIndexed(player) { index, itemStack ->
            player.inventory.setItemAsync(index, itemStack)
        }
    }

    private fun forEachIndexed(player: Player, action: (Int, ItemStack?) -> Unit) {
        slotRange.map { it to buttonMap[it]?.toShownItemStack(player) }
                .toMap()
                .forEach(action)
    }

    fun getButton(slot: Int): Button? {
        return buttonMap[slot]
    }

}