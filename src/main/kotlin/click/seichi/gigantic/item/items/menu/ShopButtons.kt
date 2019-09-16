package click.seichi.gigantic.item.items.menu

import click.seichi.gigantic.Currency
import click.seichi.gigantic.effect.GiganticEffect
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.menu.menus.EffectMenu
import click.seichi.gigantic.menu.menus.shop.DonateEffectShopMenu
import click.seichi.gigantic.menu.menus.shop.DonateShopMenu
import click.seichi.gigantic.menu.menus.shop.VoteEffectShopMenu
import click.seichi.gigantic.menu.menus.shop.VoteShopMenu
import click.seichi.gigantic.message.messages.MenuMessages
import click.seichi.gigantic.message.messages.menu.EffectMenuMessages
import click.seichi.gigantic.message.messages.menu.ShopMessages
import click.seichi.gigantic.sound.sounds.MenuSounds
import click.seichi.gigantic.sound.sounds.PlayerSounds
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import java.util.*

/**
 * @author tar0ss
 */
object ShopButtons {

    val VOTE = object : Button {
        override fun toShownItemStack(player: Player): ItemStack? {
            return itemStackOf(Material.ENCHANTED_GOLDEN_APPLE) {
                setDisplayName(player, ShopMessages.VOTE)
                sublime()
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            if (event.inventory.holder is VoteShopMenu) return false
            if (event.inventory.holder is DonateEffectShopMenu) {
                MenuSounds.CATEGORY_CHANGE.playOnly(player)
                VoteEffectShopMenu.open(player, isFirst = false, playSound = false)
            } else {
                VoteEffectShopMenu.open(player)
            }

            return true
        }
    }

    val DONATION = object : Button {
        override fun toShownItemStack(player: Player): ItemStack? {
            return itemStackOf(Material.DIAMOND) {
                setDisplayName(player, ShopMessages.DONATION)
                sublime()
                setEnchanted(true)
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            if (event.inventory.holder is DonateShopMenu) return false
            if (event.inventory.holder is VoteEffectShopMenu) {
                MenuSounds.CATEGORY_CHANGE.playOnly(player)
                DonateEffectShopMenu.open(player, isFirst = false, playSound = false)
            } else {
                DonateEffectShopMenu.open(player)
            }
            return true
        }
    }

    val EFFECT_MENU = object : Button {
        override fun toShownItemStack(player: Player): ItemStack? {
            return itemStackOf(Material.ENCHANTING_TABLE) {
                setDisplayName(player, ShopMessages.EFFECT_MENU)
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            EffectMenu.open(player)
            return true
        }
    }


    val buyMap = mutableMapOf<UUID, GiganticEffect>()

    val EFFECT: (GiganticEffect) -> Button = { effect: GiganticEffect ->
        object : Button {
            override fun toShownItemStack(player: Player): ItemStack? {
                val itemStack = effect.getIcon()

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

                    //購入方法と購入に必要なポイントを提示
                    val product = effect.product ?: return@apply
                    addLore("${product.price} " +
                            when (product.currency) {
                                Currency.VOTE_POINT -> EffectMenuMessages.VOTE_POINT
                                Currency.POMME -> EffectMenuMessages.POMME
                                Currency.DONATE_POINT -> EffectMenuMessages.DONATION
                            }.asSafety(player.wrappedLocale) +
                            "${ChatColor.RESET}" +
                            EffectMenuMessages.BUY_TYPE.asSafety(player.wrappedLocale))

                    addLore("")

                    when {
                        !product.canBuy(player) -> // 購入できない場合
                            addLore(EffectMenuMessages.CANT_BUY.asSafety(player.wrappedLocale))
                        buyMap[player.uniqueId] == effect -> {// 購入できる場合(2クリック目)
                            addLore(EffectMenuMessages.CAUTION.asSafety(player.wrappedLocale))
                            addLore(EffectMenuMessages.CAN_BUY.asSafety(player.wrappedLocale))
                        }
                        else -> // 購入できる場合(1クリック目)
                            addLore(EffectMenuMessages.CAN_BUY_DOUBLE.asSafety(player.wrappedLocale))
                    }
                    setEnchanted(true)
                }
            }

            override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
                val product = effect.product ?: return true
                // 購入可否判定
                if (!product.canBuy(player)) return false
                val uniqueId = player.uniqueId
                val menu = event.inventory.holder as? Menu ?: return false
                if (buyMap[uniqueId] == effect) {
                    // 購入処理
                    product.buy(player)
                    MenuSounds.EFFECT_BUY.playOnly(player)
                    buyMap.remove(uniqueId)
                } else {
                    buyMap[uniqueId] = effect
                    PlayerSounds.TOGGLE.playOnly(player)
                    runTaskLater(20L * 10L) {
                        if (buyMap[uniqueId] != effect) return@runTaskLater
                        buyMap.remove(uniqueId)
                        if (!player.isValid) return@runTaskLater
                        // 現在のメニューとプレイヤーが開いているメニューを検査
                        if (menu != player.findOpenMenu()) return@runTaskLater
                        menu.reopen(player)
                    }
                }
                menu.reopen(player)
                return true
            }

        }
    }

}