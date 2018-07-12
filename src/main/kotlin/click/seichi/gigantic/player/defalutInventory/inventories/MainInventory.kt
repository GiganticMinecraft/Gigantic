package click.seichi.gigantic.player.defalutInventory.inventories

import click.seichi.gigantic.extension.getHead
import click.seichi.gigantic.extension.setTitle
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.language.LocalizedText
import click.seichi.gigantic.player.defalutInventory.DefaultInventory
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import java.util.*

/**
 * @author tar0ss
 */
object MainInventory : DefaultInventory() {
    override val slotItemMap: Map<Int, SlotItem> = mapOf(
            9 to object : SlotItem {
                override fun getItemStack(player: Player): ItemStack? {
                    return player.getHead().apply {
                        setTitle(
                                LocalizedText(
                                        Locale.JAPANESE to "プロフィール"
                                ).asSafety(player.wrappedLocale)
                        )
                    }
                }

                override fun onClick(player: Player, event: InventoryClickEvent) {
                    player.sendMessage("hello world!!")
                }
            }
    )
}