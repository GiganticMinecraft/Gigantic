package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.item.items.menu.BackButton
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.menu.TeleportMessages
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object TeleportToHomeMenu : Menu() {

    override val size: Int
        get() = 18

    override fun getTitle(player: Player): String {
        return TeleportMessages.TELEPORT_TO_HOME.asSafety(player.wrappedLocale)
    }

    init {
        registerButton(0, BackButton(this, TeleportMenu))
        (9..17).step(2).forEach { slot ->
            registerButton(slot, object : Button {
                override fun findItemStack(player: Player): ItemStack? {
                    return ItemStack(Material.RED_BED).apply {

                    }
                }

                override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
                    return true
                }
            })
        }
    }
}