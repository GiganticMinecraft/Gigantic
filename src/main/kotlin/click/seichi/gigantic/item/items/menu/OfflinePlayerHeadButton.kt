package click.seichi.gigantic.item.items.menu

import click.seichi.gigantic.head.Head
import click.seichi.gigantic.item.Button
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import java.util.*

/**
 * @author tar0ss
 */
class OfflinePlayerHeadButton(private val uuid: UUID) : Button {

    override fun findItemStack(player: Player): ItemStack? {
        return getItemStack()
    }

    fun getItemStack(): ItemStack? {
        return Head.getOfflinePlayerHead(uuid)?.clone()
    }

    override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
        return false
    }

}