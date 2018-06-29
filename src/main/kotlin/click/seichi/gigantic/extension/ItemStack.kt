package click.seichi.gigantic.extension

import org.bukkit.ChatColor.*
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

fun ItemStack.setTitle(name: String, prefix: String = "$AQUA$BOLD$UNDERLINE") {
    itemMeta = itemMeta.also { meta ->
        meta.displayName = prefix + name
    }
}

fun ItemStack.setLore(vararg lore: String, prefix: String = "$GRAY") {
    itemMeta = itemMeta.also { meta ->
        meta.lore = lore.map { "$RESET$prefix$it" }.toList()
    }
}

fun ItemStack.addLore(vararg lore: String, prefix: String = "$GRAY") {
    itemMeta = itemMeta.also { meta ->
        lore.map { "$RESET$prefix$it" }.let { newLore ->
            meta.lore = meta.lore?.apply { addAll(newLore) } ?: newLore
        }
    }
}

fun ItemStack.setEnchanted(flag: Boolean) {
    itemMeta = itemMeta.also { meta ->
        when (flag) {
            true -> if (!meta.hasEnchants()) meta.addEnchant(Enchantment.DIG_SPEED, 1, true)
            false -> if (meta.hasEnchants()) meta.enchants.map { it.key }.forEach { meta.removeEnchant(it) }
        }
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
    }
}

fun ItemStack.addLine(bit: Int = 23, prefix: String = "$GRAY") {
    addLore(prefix + (1..bit).joinToString("") { "-" })
}

fun ItemStack.addClickToOpenLore() {
    addLore("$GREEN$BOLD$UNDERLINE" + "クリックで開く")
}

// TODO implements
val ItemStack.isSeichiTool: Boolean
    get() = true

fun ItemStack.canConsumeDurability(consumeDurability: Long) = consumeDurability < 0 ||
        !(durability > type.maxDurability || type.maxDurability < durability + consumeDurability)