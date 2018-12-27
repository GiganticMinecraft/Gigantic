package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.MenuMessages
import click.seichi.gigantic.message.messages.menu.EffectMenuMessages
import click.seichi.gigantic.player.GiganticEffect
import click.seichi.gigantic.sound.DetailedSound
import click.seichi.gigantic.sound.sounds.MenuSounds
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

    override val openSound: DetailedSound
        get() = MenuSounds.EFFECT_MENU

    override val closeSound: DetailedSound
        get() = MenuSounds.EFFECT_MENU

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
                    addLore(EffectMenuMessages.VOTE_POINT.asSafety(player.wrappedLocale) +
                            ": " + "${ChatColor.RESET}" + "${ChatColor.WHITE}" +
                            "114514")
                    addLore(EffectMenuMessages.POMME.asSafety(player.wrappedLocale) +
                            ": " + "${ChatColor.RESET}" + "${ChatColor.WHITE}" +
                            "1919")
                    addLore(EffectMenuMessages.DONATE_POINT.asSafety(player.wrappedLocale) +
                            ": " + "${ChatColor.RESET}" + "${ChatColor.WHITE}" +
                            "810")
                    addLore(MenuMessages.LINE)
                    addLore(EffectMenuMessages.VOTE_POINT_DESCRIPTION.asSafety(player.wrappedLocale))
                    addLore(EffectMenuMessages.POMME_DESCRIPTION.asSafety(player.wrappedLocale))
                    addLore(EffectMenuMessages.DONATE_POINT_DESCRIPTION.asSafety(player.wrappedLocale))
                }
            }

            override fun onClick(player: Player, event: InventoryClickEvent) {

            }

        })

        // 現在使用しているエフェクト
        registerButton(10, object : Button {
            override fun findItemStack(player: Player): ItemStack? {
                val effect = player.getOrPut(Keys.EFFECT)
                return effect.getIcon().apply {
                    setDisplayName(effect.getName(player.wrappedLocale))
                    setLore(*effect.getLore(player.wrappedLocale).toTypedArray())
                    setEnchanted(true)
                }
            }

            override fun onClick(player: Player, event: InventoryClickEvent) {
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

            override fun onClick(player: Player, event: InventoryClickEvent) {
                val current = player.getOrPut(Keys.EFFECT)
                if (current == effect) return
                player.offer(Keys.EFFECT, effect)
                reopen(player)
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

                        addLore(MenuMessages.LINE)

                        if (!effect.isBought(player)) {
                            //購入方法と購入に必要なポイントを提示
                            addLore("${effect.amount} " +
                                    when (effect.buyType) {
                                        GiganticEffect.BuyType.VOTE_POINT -> EffectMenuMessages.VOTE_POINT
                                        GiganticEffect.BuyType.POMME -> EffectMenuMessages.POMME
                                        GiganticEffect.BuyType.DONATE_POINT -> EffectMenuMessages.DONATE_POINT
                                        else -> throw Error("$effect is illegal buy type : ${effect.buyType}")
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

                override fun onClick(player: Player, event: InventoryClickEvent) {
                    if (effect.isBought(player)) {
                        // 購入されている場合
                        if (effect.isSelected(player)) return
                        // 選択処理
                        effect.select(player)
                        MenuSounds.EFFECT_SELECT.playOnly(player)
                        reopen(player)
                    } else {
                        // 購入されていない場合
                        if (!effect.canBuy(player)) return
                        // 購入処理
                        effect.buy(player)
                        MenuSounds.EFFECT_BUY.playOnly(player)
                        reopen(player)
                    }
                }

            }
            )
        }
    }

}