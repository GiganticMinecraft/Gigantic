package click.seichi.gigantic.item.items.menu

import click.seichi.gigantic.Currency
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.effect.GiganticEffect
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.menu.menus.EffectMenu
import click.seichi.gigantic.message.messages.MenuMessages
import click.seichi.gigantic.message.messages.menu.EffectMenuMessages
import click.seichi.gigantic.sound.sounds.PlayerSounds
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object EffectButtons {

    val PLAYER = object : Button {

        override fun toShownItemStack(player: Player): ItemStack? {
            return player.getHead().apply {
                setDisplayName(EffectMenuMessages.PLAYER.asSafety(player.wrappedLocale))
                clearLore()
                addLore(EffectMenuMessages.REMAIN.asSafety(player.wrappedLocale) +
                        EffectMenuMessages.VOTE_POINT.asSafety(player.wrappedLocale) +
                        ": " + "${ChatColor.RESET}" + "${ChatColor.WHITE}" +
                        "${Currency.VOTE_POINT.calcRemainAmount(player)}")
                // TODO Pomme実装後に実装
//                    addLore(EffectMenuMessages.REMAIN.asSafety(player.wrappedLocale) +
//                            EffectMenuMessages.POMME.asSafety(player.wrappedLocale) +
//                            ": " + "${ChatColor.RESET}" + "${ChatColor.WHITE}" +
//                            "${Currency.POMME.calcRemainAmount(player)}")
                addLore(EffectMenuMessages.REMAIN.asSafety(player.wrappedLocale) +
                        EffectMenuMessages.DONATION.asSafety(player.wrappedLocale) +
                        ": " + "${ChatColor.RESET}" + "${ChatColor.WHITE}" +
                        "${Currency.DONATE_POINT.calcRemainAmount(player)}")
                addLore(MenuMessages.LINE)
                addLore(EffectMenuMessages.VOTE_POINT_DESCRIPTION.asSafety(player.wrappedLocale))
                // TODO Pomme実装後に実装
//                    addLore(EffectMenuMessages.POMME_DESCRIPTION.asSafety(player.wrappedLocale))
                addLore(EffectMenuMessages.DONATION_DESCRIPTION.asSafety(player.wrappedLocale))
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            return false
        }

    }

    val CURRENT_EFFECT = object : Button {
        override fun toShownItemStack(player: Player): ItemStack? {
            val effect = player.getOrPut(Keys.EFFECT)
            return effect.getIcon().apply {
                setDisplayName(EffectMenuMessages.CURRENT_EFFECT.asSafety(player.wrappedLocale))
                addLore(effect.getName(player.wrappedLocale))
                addLore(*effect.getLore(player.wrappedLocale).toTypedArray())
                setEnchanted(true)
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            return false
        }
    }

    val DEFAULT_EFFECT = object : Button {

        val effect = GiganticEffect.DEFAULT

        override fun toShownItemStack(player: Player): ItemStack? {
            return effect.getIcon().apply {
                setDisplayName(effect.getName(player.wrappedLocale))
                setLore(*effect.getLore(player.wrappedLocale).toTypedArray())
                val current = player.getOrPut(Keys.EFFECT)
                if (current == effect) {
                    setEnchanted(true)
                    return@apply
                }
                addLore(EffectMenuMessages.CLICK_TO_SELECT.asSafety(player.wrappedLocale))
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            val current = player.getOrPut(Keys.EFFECT)
            if (current == effect) return false
            player.offer(Keys.EFFECT, effect)
            PlayerSounds.TOGGLE.playOnly(player)
            EffectMenu.reopen(player)
            return true
        }

    }

    val EFFECT: (GiganticEffect) -> Button = { effect: GiganticEffect ->
        object : Button {
            override fun toShownItemStack(player: Player): ItemStack? {
                if (!effect.isBought(player)) return null

                val itemStack = when {
                    effect.isBought(player) -> effect.getIcon()
                    else -> ItemStack(Material.BEDROCK)
                }

                return itemStack.apply {
                    setDisplayName(effect.getName(player.wrappedLocale))
                    setLore(*effect.getLore(player.wrappedLocale).toTypedArray())

                    addLore("")

                    addLore(EffectMenuMessages.GENERAL_BREAK.asSafety(player.wrappedLocale) +
                            "${ChatColor.RESET}${ChatColor.WHITE} " +
                            if (effect.hasGeneralBreakEffect) "あり" else "なし")
                    addLore(EffectMenuMessages.MULTI_BREAK.asSafety(player.wrappedLocale) +
                            "${ChatColor.RESET}${ChatColor.WHITE} " +
                            if (effect.hasMultiBreakEffect) "あり" else "なし")

                    addLore(MenuMessages.LINE)

                    when {
                        effect.isSelected(player) -> // 選択中の場合
                            addLore(EffectMenuMessages.SELECTED.asSafety(player.wrappedLocale))
                        else -> // 購入済みだが選択されていない場合
                            addLore(EffectMenuMessages.CLICK_TO_SELECT.asSafety(player.wrappedLocale))
                    }

                    if (effect.isSelected(player)) {
                        setEnchanted(true)
                    }

                }
            }

            override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
                if (!effect.isBought(player)) return false
                // 選択されている場合
                if (effect.isSelected(player)) return false
                // 選択処理
                effect.select(player)
                PlayerSounds.TOGGLE.playOnly(player)
                EffectMenu.reopen(player)
                return true
            }

        }
    }

}