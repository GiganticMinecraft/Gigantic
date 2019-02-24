package click.seichi.gigantic.item.items.menu

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.menu.RelicCategory
import click.seichi.gigantic.menu.menus.RelicMenu
import click.seichi.gigantic.message.messages.MenuMessages
import click.seichi.gigantic.message.messages.menu.RelicMenuMessages
import click.seichi.gigantic.relic.Relic
import click.seichi.gigantic.sound.sounds.MenuSounds
import click.seichi.gigantic.will.Will
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object RelicButtons {

    val WILL: (Will) -> Button = { will: Will ->
        object : Button {

            val category = RelicCategory.getByWill(will)

            override fun toShownItemStack(player: Player): ItemStack? {
                val hasNoRelic = will.relicSet
                        // 一つもレリックを持っていなければTRUE
                        .none { player.hasRelic(it) }

                if (hasNoRelic) return null
                return ItemStack(will.material).apply {
                    setDisplayName(RelicMenuMessages.WILL_RELIC_MENU_TITLE(will).asSafety(player.wrappedLocale))
                    // 合計個数
                    var relicNum = 0L
                    will.relicSet.forEach { relicNum += it.getDroppedNum(player) }
                    // 種類
                    val type = will.relicSet.count { player.hasRelic(it) }
                    val allType = will.relicSet.size
                    setLore(
                            *RelicMenuMessages.RELIC_MENU_LORE(relicNum, type, allType)
                                    .map {
                                        it.asSafety(player.wrappedLocale)
                                    }.toTypedArray()
                    )
                }
            }

            override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
                val hasNoRelic = will.relicSet
                        // 一つもレリックを持っていなければTRUE
                        .none { player.hasRelic(it) }
                if (hasNoRelic) return false
                val currentCategory = player.getOrPut(Keys.MENU_RELIC_CATEGORY)
                if (currentCategory == category) return false
                player.offer(Keys.MENU_RELIC_CATEGORY, category)
                RelicMenu.open(player, isFirst = false, playSound = false)
                MenuSounds.CATEGORY_CHANGE.playOnly(player)
                return true
            }
        }
    }

    val RELIC: (Relic) -> Button = { relic: Relic ->
        val will = Will.findByRelic(relic)
        object : Button {
            override fun toShownItemStack(player: Player): ItemStack? {
                val amount = relic.getDroppedNum(player)
                if (amount == 0L) return null
                return relic.getIcon().apply {
                    setDisplayName(player, RelicMenuMessages.RELIC_TITLE(will?.chatColor
                            ?: ChatColor.DARK_AQUA, relic, relic.getDroppedNum(player)))
                    setLore(*relic.getLore(player.wrappedLocale).map { "${ChatColor.GRAY}" + it }.toTypedArray())
                    addLore("${ChatColor.WHITE}" + MenuMessages.LINE)
                    val multiplier = relic.calcMultiplier(player)
                    addLore(RelicMenuMessages.BONUS_EXP(multiplier.toBigDecimal()).asSafety(player.wrappedLocale))
                    val bonusLore = relic.getBonusLore(player.wrappedLocale)
                    bonusLore.forEachIndexed { index, s ->
                        if (index == 0) {
                            addLore(RelicMenuMessages.CONDITIONS_FIRST_LINE(s).asSafety(player.wrappedLocale))
                        } else {
                            addLore(RelicMenuMessages.CONDITIONS(s).asSafety(player.wrappedLocale))
                        }
                    }
                }
            }

            override fun tryClick(player: Player, event: InventoryClickEvent) = false
        }

    }

    val SPECIAL = object : Button {

        val category = RelicCategory.SPECIAL

        override fun toShownItemStack(player: Player): ItemStack? {
            val hasNoRelic = Relic.SPECIAL_RELICS
                    // 一つもレリックを持っていなければTRUE
                    .none { player.hasRelic(it) }

            if (hasNoRelic) return null
            return itemStackOf(Material.EMERALD_BLOCK) {
                setDisplayName(player, RelicMenuMessages.SPECIAL_RELIC_MENU_TITLE)
                // 合計個数
                var relicNum = 0L
                Relic.SPECIAL_RELICS.forEach { relicNum += it.getDroppedNum(player) }
                // 種類
                val type = Relic.SPECIAL_RELICS.count { player.hasRelic(it) }
                val allType = Relic.SPECIAL_RELICS.size
                setLore(
                        *RelicMenuMessages.RELIC_MENU_LORE(relicNum, type, allType)
                                .map {
                                    it.asSafety(player.wrappedLocale)
                                }.toTypedArray()
                )
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            val hasNoRelic = Relic.SPECIAL_RELICS
                    // 一つもレリックを持っていなければTRUE
                    .none { player.hasRelic(it) }
            if (hasNoRelic) return false
            val currentCategory = player.getOrPut(Keys.MENU_RELIC_CATEGORY)
            if (currentCategory == category) return false
            player.offer(Keys.MENU_RELIC_CATEGORY, category)
            RelicMenu.open(player, isFirst = false, playSound = false)
            MenuSounds.CATEGORY_CHANGE.playOnly(player)
            return true
        }
    }

    val ALL_RELIC = object : Button {

        val category = RelicCategory.ALL

        override fun toShownItemStack(player: Player): ItemStack? {
            return itemStackOf(Material.GRASS_BLOCK) {
                setDisplayName(player, RelicMenuMessages.ALL_RELIC_MENU_TITLE)
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            val currentCategory = player.getOrPut(Keys.MENU_RELIC_CATEGORY)
            if (currentCategory == category) return false
            player.offer(Keys.MENU_RELIC_CATEGORY, category)
            RelicMenu.open(player, isFirst = false, playSound = false)
            MenuSounds.CATEGORY_CHANGE.playOnly(player)
            return true
        }
    }

    val ACTIVE = object : Button {

        val category = RelicCategory.ACTIVE

        override fun toShownItemStack(player: Player): ItemStack? {
            val activeRelics = player.getOrPut(Keys.ACTIVE_RELICS)
            if (activeRelics.isEmpty()) return null
            return itemStackOf(Material.REDSTONE_BLOCK) {
                setDisplayName(player, RelicMenuMessages.ACTIVE_RELIC_MENU_TITLE)
                // 合計個数
                var relicNum = 0L
                activeRelics.forEach { relicNum += it.getDroppedNum(player) }
                // 種類
                val type = activeRelics.size
                // 合計ボーナス値
                val bonus = activeRelics.sumByDouble { it.calcMultiplier(player) }
                setLore(
                        *RelicMenuMessages.ACTIVE_RELIC_MENU_LORE(relicNum, type, bonus)
                                .map {
                                    it.asSafety(player.wrappedLocale)
                                }.toTypedArray()
                )
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            val activeRelics = player.getOrPut(Keys.ACTIVE_RELICS)
            if (activeRelics.isEmpty()) return false
            val currentCategory = player.getOrPut(Keys.MENU_RELIC_CATEGORY)
            if (currentCategory == category) return false
            player.offer(Keys.MENU_RELIC_CATEGORY, category)
            RelicMenu.open(player, isFirst = false, playSound = false)
            MenuSounds.CATEGORY_CHANGE.playOnly(player)
            return true
        }
    }
}