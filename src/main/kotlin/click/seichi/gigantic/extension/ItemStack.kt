package click.seichi.gigantic.extension

import org.bukkit.ChatColor.RESET
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack


/**
 * @author tar0ss
 */

fun ItemStack.setDisplayName(name: String) {
    itemMeta = itemMeta.also { meta ->
        meta.displayName = name
    }
}

fun ItemStack.setLore(vararg lore: String) {
    itemMeta = itemMeta.also { meta ->
        meta.lore = lore.map { "$RESET$it" }.toList()
    }
}

fun ItemStack.clearLore() {
    itemMeta = itemMeta.also { meta ->
        if (meta.hasLore())
            meta.lore.clear()
    }
}

fun ItemStack.addLore(vararg lore: String) {
    itemMeta = itemMeta.also { meta ->
        lore.map { "$RESET$it" }.let { newLore ->
            meta.lore = meta.lore?.apply { addAll(newLore) } ?: newLore
        }
    }
}

fun ItemStack.hideAllFlag() {
    itemMeta = itemMeta.also { meta ->
        ItemFlag.values().forEach {
            meta.addItemFlags(it)
        }
    }
}

fun ItemStack.setEnchanted(flag: Boolean) {
    itemMeta = itemMeta.also { meta ->
        when (flag) {
            true -> if (!meta.hasEnchants()) meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 1, true)
            false -> if (meta.hasEnchants()) meta.enchants.map { it.key }.forEach { meta.removeEnchant(it) }
        }
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
    }
}
