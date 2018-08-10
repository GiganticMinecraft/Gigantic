package click.seichi.gigantic.button.buttons

import click.seichi.gigantic.button.HotButton
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.find
import click.seichi.gigantic.extension.setDisplayName
import click.seichi.gigantic.extension.setLore
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.message.messages.HookedItemMessages
import click.seichi.gigantic.player.LockedFunction
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object HotButtons {

    val MINE_BURST_BOOK = object : HotButton {

        override fun getItemStack(player: Player): ItemStack? {
            if (!LockedFunction.MINE_BURST.isUnlocked(player)) return null
            val mineBurst = player.find(CatalogPlayerCache.MINE_BURST) ?: return null
            return when {
                mineBurst.duringCoolTime() -> ItemStack(Material.FLINT_AND_STEEL).apply {
                    mineBurst.run {
                        amount = remainTimeToFire.toInt() + 1
                    }
                }
                mineBurst.duringFire() -> ItemStack(Material.ENCHANTED_BOOK).apply {
                    mineBurst.run {
                        amount = remainTimeToCool.toInt() + 1
                    }
                }
                else -> ItemStack(Material.ENCHANTED_BOOK)
            }.apply {
                setDisplayName(HookedItemMessages.MINE_BURST.asSafety(player.wrappedLocale))
                setLore(*HookedItemMessages.MINE_BURST_LORE(mineBurst)
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray()
                )
            }
        }

        override fun onItemHeld(player: Player, event: PlayerItemHeldEvent) {
            if (!LockedFunction.MINE_BURST.isUnlocked(player)) return
            val mineBurst = player.find(CatalogPlayerCache.MINE_BURST) ?: return
            mineBurst.run {
                if (canStart()) {
                    start()
                }
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
        }

    }

}