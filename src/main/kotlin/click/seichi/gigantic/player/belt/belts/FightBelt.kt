package click.seichi.gigantic.player.belt.belts

import click.seichi.gigantic.extension.setTitle
import click.seichi.gigantic.player.belt.Belt
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object FightBelt : Belt() {
    override val hookedItemMap: Map<Int, HookedItem> = mapOf(
            0 to object : HookedItem {
                override fun getItemStack(player: Player): ItemStack? {
                    return ItemStack(Material.DIAMOND_SWORD).apply {
                        setTitle("剣")
                    }
                }

                override fun onClick(player: Player, event: InventoryClickEvent) {
//                    player.sendMessage("hello world!!")
                }

                override fun onInteract(player: Player, event: PlayerInteractEvent) {
//                    player.sendMessage("hello world!!")
                }
            }
    )
}