package click.seichi.gigantic.item.items.menu

import click.seichi.gigantic.extension.setDisplayName
import click.seichi.gigantic.head.Head
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.menu.Menu
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import java.util.*

/**
 * @author tar0ss
 */
class OfflinePlayerHeadButton(
        private val uniqueId: UUID,
        private val name: String,
        private val menu: Menu
) : Button {

    override fun toShownItemStack(player: Player): ItemStack? {
        return Head.getOfflinePlayerHead(uniqueId).apply {
            setDisplayName(name)
        }
    }

    override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
        if (Head.isLoad(uniqueId)) return false
        Head.load(uniqueId)
        menu.reopen(player)
        return true
    }

}