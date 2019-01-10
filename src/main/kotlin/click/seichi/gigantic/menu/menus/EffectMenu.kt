package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.Currency
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.effect.GiganticEffect
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.MenuMessages
import click.seichi.gigantic.message.messages.menu.EffectMenuMessages
import click.seichi.gigantic.sound.sounds.MenuSounds
import click.seichi.gigantic.sound.sounds.PlayerSounds
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object EffectMenu : Menu() {

    override val size: Int
        get() = 54

    override fun getTitle(player: Player): String {
        return EffectMenuMessages.TITLE.asSafety(player.wrappedLocale)
    }

    init {

        // プレイヤー情報
        registerButton(9, object : Button {

            override fun findItemStack(player: Player): ItemStack? {
                return player.getHead().apply {
                    setDisplayName(EffectMenuMessages.PLAYER.asSafety(player.wrappedLocale))
                    clearLore()
                    // TODO implements
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

            override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
                return false
            }

        })

        // 現在使用しているエフェクト
        registerButton(10, object : Button {
            override fun findItemStack(player: Player): ItemStack? {
                val effect = player.getOrPut(Keys.EFFECT)
                return effect.getIcon().apply {
                    setDisplayName(EffectMenuMessages.CURRENT_EFFECT.asSafety(player.wrappedLocale))
                    addLore(effect.getName(player.wrappedLocale))
                    addLore(*effect.getLore(player.wrappedLocale).toTypedArray())
                    setEnchanted(true)
                }
            }

            override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
                return false
            }
        })

        // デフォルトエフェクト
        registerButton(12, object : Button {

            val effect = GiganticEffect.DEFAULT

            override fun findItemStack(player: Player): ItemStack? {
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

            override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
                val current = player.getOrPut(Keys.EFFECT)
                if (current == effect) return false
                player.offer(Keys.EFFECT, effect)
                reopen(player)
                return true
            }

        })

        // DEFAULT を抜いたエフェクト全て
        GiganticEffect.values().filter { it.id != 0 }.forEach { effect ->
            registerButton(effect.slot + 27, object : click.seichi.gigantic.item.Button {
                override fun findItemStack(player: Player): ItemStack? {
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
                        addLore(EffectMenuMessages.APOSTOL.asSafety(player.wrappedLocale) +
                                "${ChatColor.RESET}${ChatColor.WHITE} " +
                                if (effect.hasApostolEffect) "あり" else "なし")

                        addLore(MenuMessages.LINE)

                        if (!effect.isBought(player)) {
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
                        }

                        when {
                            effect.isSelected(player) -> // 選択中の場合
                                addLore(EffectMenuMessages.SELECTED.asSafety(player.wrappedLocale))
                            effect.isBought(player) -> // 購入済みだが選択されていない場合
                                addLore(EffectMenuMessages.CLICK_TO_SELECT.asSafety(player.wrappedLocale))
                            effect.canBuy(player) -> // 購入されていないかつ購入できる場合
                                addLore(EffectMenuMessages.CAN_BUY.asSafety(player.wrappedLocale))
                            else -> // 購入されていないかつ購入できない場合
                                addLore(EffectMenuMessages.CANT_BUY.asSafety(player.wrappedLocale))
                        }

                        if (effect.isSelected(player)) {
                            setEnchanted(true)
                        }


                    }
                }

                override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
                    if (effect.isBought(player)) {
                        // 購入されている場合
                        if (effect.isSelected(player)) return false
                        // 選択処理
                        effect.select(player)
                        PlayerSounds.TOGGLE.playOnly(player)
                        reopen(player)
                    } else {
                        // 購入されていない場合

                        // 購入可否判定
                        if (!effect.canBuy(player)) return false
                        // 購入処理
                        effect.buy(player)
                        MenuSounds.EFFECT_BUY.playOnly(player)
                        reopen(player)
                    }
                    return true
                }

            }
            )
        }
    }

}