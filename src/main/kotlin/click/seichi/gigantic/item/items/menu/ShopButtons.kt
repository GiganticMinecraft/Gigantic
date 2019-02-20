package click.seichi.gigantic.item.items.menu

import click.seichi.gigantic.Currency
import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.effect.GiganticEffect
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.menu.menus.EffectMenu
import click.seichi.gigantic.menu.menus.shop.DonateGiftEffectShopMenu
import click.seichi.gigantic.menu.menus.shop.DonateGiftShopMenu
import click.seichi.gigantic.menu.menus.shop.VoteGiftEffectShopMenu
import click.seichi.gigantic.menu.menus.shop.VoteGiftShopMenu
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
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

/**
 * @author tar0ss
 */
object ShopButtons {

    val VOTE = object : Button {
        override fun toShownItemStack(player: Player): ItemStack? {
            return ItemStack(Material.ENCHANTED_GOLDEN_APPLE).apply {
                setDisplayName(player, ShopMessages.VOTE)
                sublime()
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            if (event.inventory.holder is VoteGiftShopMenu) return false
            VoteGiftEffectShopMenu.open(player)
            return true
        }
    }

    val DONATION = object : Button {
        override fun toShownItemStack(player: Player): ItemStack? {
            return ItemStack(Material.DIAMOND).apply {
                setDisplayName(player, ShopMessages.DONATION)
                sublime()
                setEnchanted(true)
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            if (event.inventory.holder is DonateGiftShopMenu) return false
            DonateGiftEffectShopMenu.open(player)
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
                    addLore("${effect.amount} " +
                            when (effect.currency) {
                                Currency.VOTE_POINT -> EffectMenuMessages.VOTE_POINT
                                Currency.POMME -> EffectMenuMessages.POMME
                                Currency.DONATE_POINT -> EffectMenuMessages.DONATION
                                else -> throw Error("$effect is illegal buy type : ${effect.currency}")
                            }.asSafety(player.wrappedLocale) +
                            "${ChatColor.RESET}" +
                            EffectMenuMessages.BUY_TYPE.asSafety(player.wrappedLocale))

                    addLore("")

                    when {
                        !effect.canBuy(player) -> // 購入できない場合
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
                // 購入可否判定
                if (!effect.canBuy(player)) return false
                val uniqueId = player.uniqueId
                val menu = event.inventory.holder as? Menu ?: return false
                if (buyMap[uniqueId] == effect) {
                    // 購入処理
                    effect.buy(player)
                    MenuSounds.EFFECT_BUY.playOnly(player)
                } else {
                    buyMap[uniqueId] = effect
                    PlayerSounds.TOGGLE.playOnly(player)
                    object : BukkitRunnable() {
                        override fun run() {
                            if (!player.isValid) return
                            if (buyMap[uniqueId] != effect) return
                            // 現在のメニューとプレイヤーが開いているメニューを検査
                            val playerHolder = player.openInventory.topInventory.holder
                            if (menu != playerHolder) return
                            buyMap.remove(uniqueId)
                            menu.reopen(player)
                        }
                    }.runTaskLater(Gigantic.PLUGIN, 200L)
                }
                menu.reopen(player)
                return true
            }

        }
    }

}