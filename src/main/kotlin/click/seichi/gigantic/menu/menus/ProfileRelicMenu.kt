package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.boss.Boss
import click.seichi.gigantic.button.Button
import click.seichi.gigantic.button.buttons.MenuButtons
import click.seichi.gigantic.button.buttons.NextButton
import click.seichi.gigantic.button.buttons.PrevButton
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.find
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.menu.BookMenu
import click.seichi.gigantic.message.messages.MenuMessages
import click.seichi.gigantic.relic.Relic
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

/**
 * @author tar0ss
 */
object ProfileRelicMenu : BookMenu() {

    override val size: Int
        get() = 18

    private const val numOfRelicInAPage = 9

    override val maxPage = Boss.values().size.div(numOfRelicInAPage).plus(1)

    private val nextButton = NextButton(this)
    private val prevButton = PrevButton(this)

    override fun setItem(inventory: Inventory, player: Player, page: Int): Inventory {
        player.find(CatalogPlayerCache.RAID_DATA)?.let {
            val relicList = Relic.values().toList()
            val start = (page - 1) * numOfRelicInAPage
            val end = page * numOfRelicInAPage
            (start until end)
                    .filter { relicList.getOrNull(it) != null }
                    .map { it % 9 to relicList[it] }
                    .toMap()
                    .forEach { index, relic ->
                        inventory.setItem(index, MenuButtons.PROFILE_RAID_RELIC_INFO(relic, it.getRelicAmount(relic)).getItemStack(player))
                    }
            inventory.setItem(numOfRelicInAPage + 3, prevButton.getItemStack(player))
            inventory.setItem(numOfRelicInAPage + 5, nextButton.getItemStack(player))
        }
        return inventory
    }

    override fun getTitle(player: Player, page: Int): String {
        return "${ChatColor.BLACK}" +
                "${MenuMessages.PROFILE_RAID_RELIC.asSafety(player.wrappedLocale)} $page/$maxPage"
    }

    override fun getButton(page: Int, slot: Int): Button? {
        return when (slot) {
            numOfRelicInAPage + 3 -> prevButton
            numOfRelicInAPage + 5 -> nextButton
            else -> null
        }
    }

}