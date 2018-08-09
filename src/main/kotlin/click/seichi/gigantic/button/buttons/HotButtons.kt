package click.seichi.gigantic.button.buttons

import click.seichi.gigantic.button.HotButton
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.find
import click.seichi.gigantic.extension.setDisplayName
import click.seichi.gigantic.extension.setLore
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.message.messages.HookedItemMessages
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
            val mineBurstTimer = player.find(Keys.MINE_BURST_TIMER) ?: return null
            return when {
                mineBurstTimer.duringCoolTime() -> ItemStack(Material.FLINT_AND_STEEL).apply {
                    mineBurstTimer.run {
                        amount = remainTimeToFire.toInt() + 1
                    }
                }
                mineBurstTimer.duringFire() -> ItemStack(Material.ENCHANTED_BOOK).apply {
                    mineBurstTimer.run {
                        amount = remainTimeToCool.toInt() + 1
                    }
                }
                else -> ItemStack(Material.ENCHANTED_BOOK)
            }.apply {
                setDisplayName(HookedItemMessages.MINE_BURST.asSafety(player.wrappedLocale))
                setLore(*HookedItemMessages.MINE_BURST_LORE(mineBurstTimer)
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray()
                )
            }
        }

        override fun onItemHeld(player: Player, event: PlayerItemHeldEvent) {
            val mineBurstTimer = player.find(Keys.MINE_BURST_TIMER) ?: return
            mineBurstTimer.run {
                if (canStart()) {
                    start()
                }
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
        }

    }

}