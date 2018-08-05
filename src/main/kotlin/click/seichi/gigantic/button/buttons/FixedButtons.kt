package click.seichi.gigantic.button.buttons

import click.seichi.gigantic.button.Button
import click.seichi.gigantic.data.keys.Keys
import click.seichi.gigantic.extension.find
import click.seichi.gigantic.extension.setDisplayName
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.message.messages.HookedItemMessages
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object FixedButtons {

    val PICKEL = object : Button {
        override fun getItemStack(player: Player): ItemStack? {
            return ItemStack(Material.DIAMOND_PICKAXE).apply {
                setDisplayName(HookedItemMessages.PICKEL.asSafety(player.wrappedLocale))
                itemMeta = itemMeta.apply {
                    isUnbreakable = true
                    addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                    addItemFlags(ItemFlag.HIDE_ENCHANTS)
                    addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
                }
                val mineBurstTimer = player.find(Keys.MINE_BURST_TIMER) ?: return@apply
                if (mineBurstTimer.duringFire()) {
                    addEnchantment(Enchantment.DIG_SPEED, 5)
                }
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
        }
    }

    val SPADE = object : Button {
        override fun getItemStack(player: Player): ItemStack? {
            return ItemStack(Material.DIAMOND_SPADE).apply {
                setDisplayName(HookedItemMessages.PICKEL.asSafety(player.wrappedLocale))
                itemMeta = itemMeta.apply {
                    isUnbreakable = true
                    addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                    addItemFlags(ItemFlag.HIDE_ENCHANTS)
                    addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
                }
                val mineBurstTimer = player.find(Keys.MINE_BURST_TIMER) ?: return@apply
                if (mineBurstTimer.duringFire()) {
                    addEnchantment(Enchantment.DIG_SPEED, 5)
                }
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
        }
    }

    val AXE = object : Button {
        override fun getItemStack(player: Player): ItemStack? {
            return ItemStack(Material.DIAMOND_AXE).apply {
                setDisplayName(HookedItemMessages.PICKEL.asSafety(player.wrappedLocale))
                itemMeta = itemMeta.apply {
                    isUnbreakable = true
                    addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                    addItemFlags(ItemFlag.HIDE_ENCHANTS)
                    addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
                }
                val mineBurstTimer = player.find(Keys.MINE_BURST_TIMER) ?: return@apply
                if (mineBurstTimer.duringFire()) {
                    addEnchantment(Enchantment.DIG_SPEED, 5)
                }
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
        }
    }


}