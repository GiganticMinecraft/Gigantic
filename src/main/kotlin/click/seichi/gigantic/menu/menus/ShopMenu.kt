package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.extension.addLore
import click.seichi.gigantic.extension.setDisplayName
import click.seichi.gigantic.extension.setLore
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.MenuMessages
import click.seichi.gigantic.message.messages.menu.ShopMenuMessages
import click.seichi.gigantic.player.GiganticEffect
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object ShopMenu : Menu() {

    override val size: Int
        get() = 54

    override fun getTitle(player: Player): String {
        return ShopMenuMessages.TITLE.asSafety(player.wrappedLocale)
    }

    init {
        GiganticEffect.values().forEach { effect ->
            registerButton(effect.slot, object : click.seichi.gigantic.item.Button {
                override fun findItemStack(player: Player): ItemStack? {
                    val itemStack = if (effect.isBought(player)) effect.getIcon()
                    else ItemStack(Material.BEDROCK)

                    return itemStack.apply {
                        setDisplayName(effect.getName(player.wrappedLocale))
                        setLore(*effect.getLore(player.wrappedLocale).toTypedArray())

                        addLore(MenuMessages.LINE)
                        //購入方法と購入に必要なポイントを提示
                        addLore("${effect.amount} " +
                                when (effect.buyType) {
                                    GiganticEffect.BuyType.VOTE_POINT -> ShopMenuMessages.VOTE_POINT
                                    GiganticEffect.BuyType.POMME -> ShopMenuMessages.POMME
                                    GiganticEffect.BuyType.DONATE_POINT -> ShopMenuMessages.DONATE_POINT
                                }.asSafety(player.wrappedLocale) +
                                "${ChatColor.RESET}" +
                                ShopMenuMessages.BUY_TYPE.asSafety(player.wrappedLocale))

                        addLore("")

                        when {
                            effect.isBought(player) -> // 購入済みの場合
                                addLore(ShopMenuMessages.HAS_BOUGHT.asSafety(player.wrappedLocale))
                            effect.canBuy(player) -> // 購入されていないかつ購入できる場合
                                addLore(ShopMenuMessages.CAN_BUY.asSafety(player.wrappedLocale))
                            else -> // 購入されていないかつ購入できない場合
                                addLore(ShopMenuMessages.CANT_BUY.asSafety(player.wrappedLocale))
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