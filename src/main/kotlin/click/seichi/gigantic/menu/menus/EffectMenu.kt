package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.extension.*
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.MenuMessages
import click.seichi.gigantic.message.messages.menu.EffectMenuMessages
import click.seichi.gigantic.player.GiganticEffect
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

        // デフォルトエフェクト
        registerButton(12, object : Button {

            override fun findItemStack(player: Player): ItemStack? {
                return ItemStack(Material.GLASS).apply {
                    setDisplayName(EffectMenuMessages.DEFAULT_EFFECT.asSafety(player.wrappedLocale))
                }
            }

            override fun onClick(player: Player, event: InventoryClickEvent) {
                // TODO implements
            }

        })

        GiganticEffect.values().forEach { effect ->
            registerButton(effect.slot + 27, object : click.seichi.gigantic.item.Button {
                override fun findItemStack(player: Player): ItemStack? {
                    val itemStack = if (effect.isBought(player)) effect.getIcon()
                    else ItemStack(Material.BEDROCK)

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
                                    }.asSafety(player.wrappedLocale) +
                                    "${ChatColor.RESET}" +
                                    EffectMenuMessages.BUY_TYPE.asSafety(player.wrappedLocale))

                            addLore("")
                        }

                        when {
                            effect.isBought(player) -> // 購入済みの場合
                                addLore(EffectMenuMessages.HAS_BOUGHT.asSafety(player.wrappedLocale))
                            effect.canBuy(player) -> // 購入されていないかつ購入できる場合
                                addLore(EffectMenuMessages.CAN_BUY.asSafety(player.wrappedLocale))
                            else -> // 購入されていないかつ購入できない場合
                                addLore(EffectMenuMessages.CANT_BUY.asSafety(player.wrappedLocale))
                        }


                    }
                }

                override fun onClick(player: Player, event: InventoryClickEvent) {
                    if (effect.isBought(player)) return
                    if (!effect.canBuy(player)) return
                    // 購入処理
                    effect.buy(player)
                    reopen(player)
                }

            }
            )
        }
    }

}