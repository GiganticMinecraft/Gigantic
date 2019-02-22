package click.seichi.gigantic.menu.menus

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
import click.seichi.gigantic.menu.BookMenu
import click.seichi.gigantic.message.messages.menu.EffectMenuMessages
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

/**
 * @author tar0ss
 */
object EffectMenu : BookMenu() {

    override val size: Int
        get() = 6 * 9

    private const val numOfContentsPerPage = 3 * 9

    private const val offset = 18

    init {
        registerButton(0, EffectButtons.PLAYER)
        registerButton(1, EffectButtons.CURRENT_EFFECT)
        registerButton(3, EffectButtons.DEFAULT_EFFECT)
        registerButton(6, ShopButtons.VOTE)
        registerButton(7, ShopButtons.DONATION)
        registerButton(numOfContentsPerPage + offset + 3, PrevButton(this))
        registerButton(numOfContentsPerPage + offset + 5, NextButton(this))
    }

    override fun getMaxPage(player: Player): Int {
        return player.getOrPut(Keys.MENU_EFFECT_LIST).size.minus(1).div(numOfContentsPerPage).plus(1).coerceAtLeast(1)
    }

    override fun onOpen(player: Player, isFirst: Boolean) {
        player.offer(Keys.MENU_EFFECT_LIST,
                GiganticEffect.values()
                        .filter { it.currency != Currency.DEFAULT }
                        .filter { it.isBought(player) }
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
                    inventory.setItem(player, index + offset, EffectButtons.EFFECT(to))
                }
        getButtonMap().forEach { slot, button ->
            inventory.setItem(player, slot, button)
        }
        return inventory
    }

    override fun getTitle(player: Player, page: Int): String {
        return "${EffectMenuMessages.TITLE.asSafety(player.wrappedLocale)} $page/${getMaxPage(player)}"
    }

    override fun getButton(player: Player, page: Int, slot: Int): Button? {
        val index = (page - 1) * numOfContentsPerPage + slot - offset
        return getButtonMap()[slot] ?: EffectButtons.EFFECT(player.getOrPut(Keys.MENU_EFFECT_LIST).getOrNull(index)
                ?: return null)
    }

}