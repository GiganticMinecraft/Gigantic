package click.seichi.gigantic.enchantment

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.message.LocalizedText
import org.bukkit.ChatColor
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*

/**
 * @author tar0ss
 */
enum class ToolEnchantment(
        private val localizedText: LocalizedText
) {
    DIG_SPEED(
            LocalizedText(Locale.JAPANESE to "整地効率")
    ) {
        override fun isMatch(player: Player, itemStack: ItemStack): Boolean {
            return true
        }

        override fun calcLevel(player: Player, itemStack: ItemStack): Int {
            val mineBurst = player.getOrPut(Keys.SKILL_MINE_BURST)
            return if (mineBurst.duringFire()) {
                10
            } else {
                player.comboRank.minus(1).coerceIn(0..10)
            }
        }

        override fun add(player: Player, itemStack: ItemStack, enchantLevel: Int): Boolean {
            if (!super.add(player, itemStack, enchantLevel)) return false

            itemStack.run {
                addUnsafeEnchantment(Enchantment.DIG_SPEED, enchantLevel)
            }
            return true
        }
    },
    FROZEN_BREAK(
            LocalizedText(Locale.JAPANESE to "氷結")
    ) {
        override fun isMatch(player: Player, itemStack: ItemStack): Boolean {
            return true
        }

        override fun calcLevel(player: Player, itemStack: ItemStack): Int {
            return 1
        }
    },
    HARDEN_BREAK(
            LocalizedText(Locale.JAPANESE to "火成")
    ) {
        override fun isMatch(player: Player, itemStack: ItemStack): Boolean {
            return true
        }

        override fun calcLevel(player: Player, itemStack: ItemStack): Int {
            return 1
        }
    },
    CUTTER(
            LocalizedText(Locale.JAPANESE to "木こり")
    ) {
        override fun isMatch(player: Player, itemStack: ItemStack): Boolean {
            return true
        }

        override fun calcLevel(player: Player, itemStack: ItemStack): Int {
            return 1
        }
    },
    ;

    protected abstract fun isMatch(player: Player, itemStack: ItemStack): Boolean

    protected abstract fun calcLevel(player: Player, itemStack: ItemStack): Int

    protected open fun add(player: Player, itemStack: ItemStack, enchantLevel: Int): Boolean {
        itemStack.run {
            addLore("${ChatColor.GRAY}" +
                    localizedText.asSafety(player.wrappedLocale) +
                    " " + String.enchantLevel(enchantLevel))
        }
        return true
    }

    fun addIfMatch(player: Player, itemStack: ItemStack): Boolean {
        if (!isMatch(player, itemStack)) return false

        val level = calcLevel(player, itemStack)
        if (level == 0) return false

        return add(player, itemStack, level)
    }

    fun has(player: Player, requiredLevel: Int = 1): Boolean {
        val toolSlot = player.getOrPut(Keys.BELT).toolSlot
        val itemStack = player.inventory.getItem(toolSlot) ?: return false

        if (!isMatch(player, itemStack)) return false

        val level = calcLevel(player, itemStack)
        return level >= requiredLevel
    }

}