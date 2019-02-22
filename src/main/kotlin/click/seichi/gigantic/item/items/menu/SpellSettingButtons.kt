package click.seichi.gigantic.item.items.menu

import click.seichi.gigantic.breaker.spells.MultiBreaker
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.config.Config
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.menu.menus.spell.SpellSettingMenu
import click.seichi.gigantic.message.messages.MenuMessages
import click.seichi.gigantic.message.messages.menu.SpellSettingMenuMessages
import click.seichi.gigantic.player.Defaults
import click.seichi.gigantic.player.spell.Spell
import click.seichi.gigantic.sound.sounds.PlayerSounds
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object SpellSettingButtons {

    val MULTI_BREAK = object : Button {
        override fun toShownItemStack(player: Player): ItemStack? {
            return itemStackOf(Material.DIAMOND_PICKAXE) {
                setDisplayName(SpellSettingMenuMessages.CURRENT_AREA.asSafety(player.wrappedLocale))

                val breakArea = player.getOrPut(Keys.SPELL_MULTI_BREAK_AREA)
                val limitOfBreakNum = MultiBreaker.calcLimitOfBreakNumOfMultiBreak(player.maxMana)

                setLore(*SpellSettingMenuMessages.CURRENT_AREA_LORE(breakArea)
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray())

                addLore(MenuMessages.LINE)

                addLore(SpellSettingMenuMessages.LIMIT_SIZE.asSafety(player.wrappedLocale))

                addLore(*SpellSettingMenuMessages.LIMIT_OF_BREAK_NUM_LORE(limitOfBreakNum)
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray())

                itemMeta = itemMeta.apply {
                    addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                }
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            return true
        }

    }

    val BIGGER_WIDTH = object : Button {
        override fun toShownItemStack(player: Player): ItemStack? {
            return itemStackOf(Material.YELLOW_STAINED_GLASS_PANE) {
                val breakArea = player.getOrPut(Keys.SPELL_MULTI_BREAK_AREA)
                val nextBreakArea = breakArea.add(2, 0, 0)
                val limitOfBreakNum = MultiBreaker.calcLimitOfBreakNumOfMultiBreak(player.maxMana)

                if (nextBreakArea.calcBreakNum() > limitOfBreakNum) {
                    setDisplayName(SpellSettingMenuMessages.LIMIT_OF_BREAK_NUM.asSafety(player.wrappedLocale))
                } else {
                    setDisplayName(SpellSettingMenuMessages.BIGGER_WIDTH(breakArea).asSafety(player.wrappedLocale))
                }
            }
        }


        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            player.transform(Keys.SPELL_MULTI_BREAK_AREA) { breakArea ->
                val nextBreakArea = breakArea.add(2, 0, 0)
                val limitOfBreakNum = MultiBreaker.calcLimitOfBreakNumOfMultiBreak(player.maxMana)
                if (nextBreakArea.width in 1..Config.SPELL_MULTI_BREAK_LIMIT_SIZE &&
                        nextBreakArea.calcBreakNum() <= limitOfBreakNum) {
                    PlayerSounds.TOGGLE.playOnly(player)
                    nextBreakArea
                } else {
                    PlayerSounds.FAIL.playOnly(player)
                    breakArea
                }
            }
            SpellSettingMenu.reopen(player)
            return true
        }

    }

    val SMALLER_WIDTH = object : Button {
        override fun toShownItemStack(player: Player): ItemStack? {
            return itemStackOf(Material.LIGHT_BLUE_STAINED_GLASS_PANE) {
                val breakArea = player.getOrPut(Keys.SPELL_MULTI_BREAK_AREA)
                setDisplayName(SpellSettingMenuMessages.SMALLER_WIDTH(breakArea).asSafety(player.wrappedLocale))
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            player.transform(Keys.SPELL_MULTI_BREAK_AREA) { breakArea ->
                val nextBreakArea = breakArea.add(-2, 0, 0)
                if (nextBreakArea.width > 0) {
                    PlayerSounds.TOGGLE.playOnly(player)
                    nextBreakArea
                } else {
                    PlayerSounds.FAIL.playOnly(player)
                    breakArea
                }
            }
            SpellSettingMenu.reopen(player)
            return true
        }

    }

    val BIGGER_HEIGHT = object : Button {
        override fun toShownItemStack(player: Player): ItemStack? {
            return itemStackOf(Material.ORANGE_STAINED_GLASS_PANE) {
                val breakArea = player.getOrPut(Keys.SPELL_MULTI_BREAK_AREA)
                val nextBreakArea = breakArea.add(0, 1, 0)
                val limitOfBreakNum = MultiBreaker.calcLimitOfBreakNumOfMultiBreak(player.maxMana)

                if (nextBreakArea.calcBreakNum() > limitOfBreakNum) {
                    setDisplayName(SpellSettingMenuMessages.LIMIT_OF_BREAK_NUM.asSafety(player.wrappedLocale))
                } else {
                    setDisplayName(SpellSettingMenuMessages.BIGGER_HEIGHT(breakArea).asSafety(player.wrappedLocale))
                }
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            player.transform(Keys.SPELL_MULTI_BREAK_AREA) { breakArea ->
                val nextBreakArea = breakArea.add(0, 1, 0)
                val limitOfBreakNum = MultiBreaker.calcLimitOfBreakNumOfMultiBreak(player.maxMana)
                if (nextBreakArea.height in 1..Config.SPELL_MULTI_BREAK_LIMIT_SIZE && nextBreakArea.calcBreakNum() <= limitOfBreakNum) {
                    PlayerSounds.TOGGLE.playOnly(player)
                    nextBreakArea
                } else {
                    PlayerSounds.FAIL.playOnly(player)
                    breakArea
                }
            }
            SpellSettingMenu.reopen(player)
            return true
        }

    }

    val SMALLER_HEIGHT = object : Button {
        override fun toShownItemStack(player: Player): ItemStack? {
            return itemStackOf(Material.PINK_STAINED_GLASS_PANE) {
                val breakArea = player.getOrPut(Keys.SPELL_MULTI_BREAK_AREA)
                setDisplayName(SpellSettingMenuMessages.SMALLER_HEIGHT(breakArea).asSafety(player.wrappedLocale))
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            player.transform(Keys.SPELL_MULTI_BREAK_AREA) { breakArea ->
                val nextBreakArea = breakArea.add(0, -1, 0)
                if (nextBreakArea.height > 0) {
                    PlayerSounds.TOGGLE.playOnly(player)
                    nextBreakArea
                } else {
                    PlayerSounds.FAIL.playOnly(player)
                    breakArea
                }
            }
            SpellSettingMenu.reopen(player)
            return true
        }

    }

    val BIGGER_DEPTH = object : Button {
        override fun toShownItemStack(player: Player): ItemStack? {
            return itemStackOf(Material.MAGENTA_STAINED_GLASS_PANE) {
                val breakArea = player.getOrPut(Keys.SPELL_MULTI_BREAK_AREA)
                val nextBreakArea = breakArea.add(0, 0, 1)
                val limitOfBreakNum = MultiBreaker.calcLimitOfBreakNumOfMultiBreak(player.maxMana)

                if (nextBreakArea.calcBreakNum() > limitOfBreakNum) {
                    setDisplayName(SpellSettingMenuMessages.LIMIT_OF_BREAK_NUM.asSafety(player.wrappedLocale))
                } else {
                    setDisplayName(SpellSettingMenuMessages.BIGGER_DEPTH(breakArea).asSafety(player.wrappedLocale))
                }
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            player.transform(Keys.SPELL_MULTI_BREAK_AREA) { breakArea ->
                val nextBreakArea = breakArea.add(0, 0, 1)
                val limitOfBreakNum = MultiBreaker.calcLimitOfBreakNumOfMultiBreak(player.maxMana)
                if (nextBreakArea.depth in 1..Config.SPELL_MULTI_BREAK_LIMIT_SIZE && nextBreakArea.calcBreakNum() <= limitOfBreakNum) {
                    PlayerSounds.TOGGLE.playOnly(player)
                    nextBreakArea
                } else {
                    PlayerSounds.FAIL.playOnly(player)
                    breakArea
                }
            }
            SpellSettingMenu.reopen(player)
            return true
        }

    }

    val SMALLER_DEPTH = object : Button {
        override fun toShownItemStack(player: Player): ItemStack? {
            return itemStackOf(Material.LIME_STAINED_GLASS_PANE) {
                val breakArea = player.getOrPut(Keys.SPELL_MULTI_BREAK_AREA)
                setDisplayName(SpellSettingMenuMessages.SMALLER_DEPTH(breakArea).asSafety(player.wrappedLocale))
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            player.transform(Keys.SPELL_MULTI_BREAK_AREA) { breakArea ->
                val nextBreakArea = breakArea.add(0, 0, -1)
                if (nextBreakArea.depth > 0) {
                    PlayerSounds.TOGGLE.playOnly(player)
                    nextBreakArea
                } else {
                    PlayerSounds.FAIL.playOnly(player)
                    breakArea
                }
            }
            SpellSettingMenu.reopen(player)
            return true
        }

    }


    val LUNA_FLEX = object : Button {
        override fun toShownItemStack(player: Player): ItemStack? {
            if (!Spell.LUNA_FLEX.isGranted(player)) return null
            return itemStackOf(Material.SUGAR) {
                val degree = player.getOrPut(Keys.WALK_SPEED)
                        .minus(Defaults.WALK_SPEED)
                        .times(10.toBigDecimal())
                        .toInt()
                        .coerceIn(0..Defaults.LUNA_FLEX_MAX_DEGREE)
                val consumeManaPerBlock = degree.toDouble()
                        .times(Config.SPELL_LUNA_FLEX_MANA_PER_DEGREE)
                        .toBigDecimal()
                setDisplayName(SpellSettingMenuMessages.WALK_SPEED.asSafety(player.wrappedLocale))
                clearLore()
                if (degree > 0) {
                    addLore(SpellSettingMenuMessages.CURRENT_DEGREE(degree).asSafety(player.wrappedLocale))
                    addLore(SpellSettingMenuMessages.CONSUME_MANA(consumeManaPerBlock).asSafety(player.wrappedLocale))
                } else {
                    addLore(SpellSettingMenuMessages.DEFAULT_SPEED.asSafety(player.wrappedLocale))
                }
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            return true
        }

    }

    val BIGGER_DEGREE = object : Button {
        override fun toShownItemStack(player: Player): ItemStack? {
            if (!Spell.LUNA_FLEX.isGranted(player)) return null
            return itemStackOf(Material.ORANGE_STAINED_GLASS_PANE) {
                val degree = player.getOrPut(Keys.WALK_SPEED)
                        .minus(Defaults.WALK_SPEED)
                        .times(10.toBigDecimal())
                        .toInt()
                        .coerceIn(0..Defaults.LUNA_FLEX_MAX_DEGREE)
                setDisplayName(SpellSettingMenuMessages.BIGGER_DEGREE(degree).asSafety(player.wrappedLocale))
                if (degree >= Defaults.LUNA_FLEX_MAX_DEGREE) {
                    addLore(SpellSettingMenuMessages.MAX_DEGREE.asSafety(player.wrappedLocale))
                }
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            if (!Spell.LUNA_FLEX.isGranted(player)) return false
            val degree = player.getOrPut(Keys.WALK_SPEED)
                    .minus(Defaults.WALK_SPEED)
                    .times(10.toBigDecimal())
                    .toInt()
                    .coerceIn(0..Defaults.LUNA_FLEX_MAX_DEGREE)
            if (degree >= Defaults.LUNA_FLEX_MAX_DEGREE) return false
            player.transform(Keys.WALK_SPEED) {
                it.plus(0.1.toBigDecimal()).coerceAtMost(1.0.toBigDecimal()).apply {
                    player.walkSpeed = this.toFloat()
                }
            }
            PlayerSounds.TOGGLE.playOnly(player)
            SpellSettingMenu.reopen(player)
            return true
        }
    }

    val SMALLER_DEGREE = object : Button {
        override fun toShownItemStack(player: Player): ItemStack? {
            if (!Spell.LUNA_FLEX.isGranted(player)) return null
            return itemStackOf(Material.PINK_STAINED_GLASS_PANE) {
                val degree = player.getOrPut(Keys.WALK_SPEED)
                        .minus(Defaults.WALK_SPEED)
                        .times(10.toBigDecimal())
                        .toInt()
                        .coerceIn(0..Defaults.LUNA_FLEX_MAX_DEGREE)
                setDisplayName(SpellSettingMenuMessages.SMALLER_DEGREE(degree).asSafety(player.wrappedLocale))
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            if (!Spell.LUNA_FLEX.isGranted(player)) return false
            val degree = player.getOrPut(Keys.WALK_SPEED)
                    .minus(Defaults.WALK_SPEED)
                    .times(10.toBigDecimal())
                    .toInt()
                    .coerceIn(0..Defaults.LUNA_FLEX_MAX_DEGREE)
            if (degree <= 0) return false
            player.transform(Keys.WALK_SPEED) {
                it.minus(0.1.toBigDecimal()).coerceAtLeast(Defaults.WALK_SPEED).apply {
                    player.walkSpeed = this.toFloat()
                }
            }
            PlayerSounds.TOGGLE.playOnly(player)
            SpellSettingMenu.reopen(player)
            return true
        }
    }

}