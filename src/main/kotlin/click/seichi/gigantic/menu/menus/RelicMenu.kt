package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.item.items.menu.NextButton
import click.seichi.gigantic.item.items.menu.PrevButton
import click.seichi.gigantic.item.items.menu.RelicButtons
import click.seichi.gigantic.menu.BookMenu
import click.seichi.gigantic.menu.RelicCategory
import click.seichi.gigantic.relic.Relic
import click.seichi.gigantic.will.Will
import click.seichi.gigantic.will.WillGrade
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

/**
 * @author tar0ss
 */
object RelicMenu : BookMenu() {

    override val size: Int
        get() = 6 * 9

    private const val numOfContentsPerPage = 2 * 9

    private const val offset = 3 * 9

    init {
        // 通常意志
        Will.values()
                .filter { it.grade == WillGrade.BASIC }
                .sortedBy { it.displayPriority }
                .forEachIndexed { index, will ->
                    registerButton(index, RelicButtons.WILL(will))
                }
        // 高度意志
        Will.values()
                .filter { it.grade == WillGrade.ADVANCED }
                .sortedBy { it.displayPriority }
                .forEachIndexed { index, will ->
                    registerButton(index + 9, RelicButtons.WILL(will))
                }

        // さくら
        registerButton(6, RelicButtons.WILL(Will.SAKURA))

        // スペシャル
        registerButton(7, RelicButtons.SPECIAL)

        // 絞り込みをリセットするボタン
        registerButton(8, RelicButtons.ALL_RELIC)

        // active relics
        registerButton(15, RelicButtons.ACTIVE)

        registerButton(size - 6, PrevButton(this))
        registerButton(size - 4, NextButton(this))
    }

    override fun getMaxPage(player: Player): Int {
        return player.getOrPut(Keys.MENU_RELIC_LIST).size.minus(1).div(numOfContentsPerPage).plus(1).coerceAtLeast(1)
    }

    override fun onOpen(player: Player, page: Int, isFirst: Boolean) {
        if (isFirst) {
            player.offer(Keys.MENU_RELIC_CATEGORY, RelicCategory.ALL)
        }

        val category = player.getOrPut(Keys.MENU_RELIC_CATEGORY)
        player.offer(Keys.MENU_RELIC_LIST,
                Relic.values()
                        .filter { player.hasRelic(it) }
                        .filter {
                            category.isContain(player, it)
                        }
                        .toList()
        )
    }

    override fun setItem(inventory: Inventory, player: Player, page: Int): Inventory {
        val contentList = player.getOrPut(Keys.MENU_RELIC_LIST)
        val start = (page - 1) * numOfContentsPerPage
        val end = page * numOfContentsPerPage
        (start until end)
                .filter { contentList.getOrNull(it) != null }
                .map { it % numOfContentsPerPage to contentList[it] }
                .toMap()
                .forEach { index, relic ->
                    inventory.setItem(player, index + offset, RelicButtons.RELIC(relic))
                }
        getButtonMap().forEach { slot, button ->
            inventory.setItem(player, slot, button)
        }
        return inventory
    }

    override fun getTitle(player: Player, page: Int): String {
        val category = player.getOrPut(Keys.MENU_RELIC_CATEGORY)
        return "${category.menuTitle.asSafety(player.wrappedLocale)} $page/${getMaxPage(player)}"
    }

    override fun getButton(player: Player, page: Int, slot: Int): Button? {
        val index = (page - 1) * numOfContentsPerPage + slot - offset
        return getButtonMap()[slot] ?: RelicButtons.RELIC(player.getOrPut(Keys.MENU_RELIC_LIST)[index])
    }


}