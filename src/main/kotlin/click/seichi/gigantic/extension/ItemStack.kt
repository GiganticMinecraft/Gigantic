package click.seichi.gigantic.extension

import click.seichi.gigantic.message.LocalizedText
import org.bukkit.ChatColor.RESET
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.PotionMeta


/**
 * @author tar0ss
 * @author unicroak
 */

fun itemStackOf(material: Material, applies: ItemStack.() -> Unit = {}): ItemStack {
    return ItemStack(material).apply(applies)
}

fun ItemStack.setDisplayName(name: String) {
    itemMeta = itemMeta.also { meta ->
        meta.displayName = "$RESET$name"
    }
}

fun ItemStack.setDisplayName(player: Player, text: LocalizedText) {
    setDisplayName(text.asSafety(player.wrappedLocale))
}

fun ItemStack.setLore(vararg lore: String) {
    itemMeta = itemMeta.also { meta ->
        meta.lore = lore.map { "$RESET$it" }.toList()
    }
}

fun ItemStack.clearLore() {
    itemMeta = itemMeta.also { meta ->
        if (meta.hasLore())
            meta.lore = listOf()
    }
}

fun ItemStack.addLore(vararg lore: String) {
    itemMeta = itemMeta.also { meta ->
        lore.map { "$RESET$it" }.let { newLore ->
            meta.lore = meta.lore?.apply { addAll(newLore) } ?: newLore
        }
    }
}

fun ItemStack.addLore(player: Player, localizedText: LocalizedText) {
    val text = localizedText.asSafety(player.wrappedLocale)
    addLore(text)
}

fun ItemStack.addLore(player: Player, localizedTextList: List<LocalizedText>) {
    val texts = localizedTextList
            .map { it.asSafety(player.wrappedLocale) }
            .toTypedArray()
    addLore(*texts)
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

fun ItemStack.sublime() {
    hideAllFlag()
    itemMeta.isUnbreakable = true
}

fun potionOf(color: Color): ItemStack {
    return itemStackOf(Material.POTION) {
        val potionMeta = itemMeta as PotionMeta
        potionMeta.color = color
        itemMeta = potionMeta
    }
}
