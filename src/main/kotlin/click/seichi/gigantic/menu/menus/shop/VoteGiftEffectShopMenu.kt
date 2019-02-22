package click.seichi.gigantic.menu.menus.shop

import click.seichi.gigantic.Currency
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.effect.GiganticEffect
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.offer
import click.seichi.gigantic.extension.setItem
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.item.items.menu.EffectButtons
import click.seichi.gigantic.item.items.menu.NextButton
import click.seichi.gigantic.item.items.menu.PrevButton
import click.seichi.gigantic.item.items.menu.ShopButtons
import click.seichi.gigantic.message.messages.menu.ShopMessages
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

/**
 * @author tar0ss
 */
object VoteGiftEffectShopMenu : VoteGiftShopMenu() {

    override val size: Int
        get() = 5 * 9

    private const val numOfContentsPerPage = 3 * 9

    private const val offset = 2 * 9

    init {
        registerButton(0, EffectButtons.PLAYER)
        registerButton(size - 6, PrevButton(this))
        registerButton(size - 4, NextButton(this))
    }

    override fun getMaxPage(player: Player): Int {
        return player.getOrPut(Keys.MENU_EFFECT_LIST).size.minus(1).div(numOfContentsPerPage).plus(1).coerceAtLeast(1)
    }

    override fun onOpen(player: Player, isFirst: Boolean) {
        player.offer(Keys.MENU_EFFECT_LIST,
                GiganticEffect.values()
                        .filter { it.currency == Currency.VOTE_POINT }
                        .filter { !it.isBought(player) }
                        .sortedBy { it.amount }
                        .toList()
        )
    }

    override fun setItem(inventory: Inventory, player: Player, page: Int): Inventory {
        val contentList = player.getOrPut(Keys.MENU_EFFECT_LIST)
        val start = (page - 1) * numOfContentsPerPage
        val end = page * numOfContentsPerPage
        (start until end)
                .filter { contentList.getOrNull(it) != null }
                .map { it % numOfContentsPerPage to contentList[it] }
                .toMap()
                .forEach { index, to ->
                    inventory.setItem(player, index + offset, ShopButtons.EFFECT(to))
                }
        getButtonMap().forEach { slot, button ->
            inventory.setItem(player, slot, button)
        }
        return inventory
    }

    override fun getTitle(player: Player, page: Int): String {
        return "${ShopMessages.VOTE_GIFT_EFFECT_SHOP_TITLE.asSafety(player.wrappedLocale)} $page/${getMaxPage(player)}"
    }

    override fun getButton(player: Player, page: Int, slot: Int): Button? {
        val index = (page - 1) * numOfContentsPerPage + slot - offset
        return getButtonMap()[slot] ?: ShopButtons.EFFECT(player.getOrPut(Keys.MENU_EFFECT_LIST).getOrNull(index)
                ?: return null)
    }

}