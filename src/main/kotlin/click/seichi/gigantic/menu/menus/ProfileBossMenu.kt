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
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

/**
 * @author tar0ss
 */
object ProfileBossMenu : BookMenu() {

    override val size: Int
        get() = 18

    private const val numOfBossInAPage = 9

    override val maxPage = Boss.values().size.div(numOfBossInAPage).plus(1)

    private val nextButton = NextButton(this)
    private val prevButton = PrevButton(this)

    override fun setItem(inventory: Inventory, player: Player, page: Int): Inventory {
        player.find(CatalogPlayerCache.RAID_DATA)?.let {
            val bossList = Boss.values().toList()
            val start = (page - 1) * numOfBossInAPage
            val end = page * numOfBossInAPage
            (start until end)
                    .filter { bossList.getOrNull(it) != null }
                    .map { it % 9 to bossList[it] }
                    .toMap()
                    .forEach { index, boss ->
                        inventory.setItem(index, MenuButtons.PROFILE_RAID_BOSS_INFO(boss, it.getDefeatCount(boss)).getItemStack(player))
                    }
            inventory.setItem(numOfBossInAPage + 3, prevButton.getItemStack(player))
            inventory.setItem(numOfBossInAPage + 5, nextButton.getItemStack(player))
        }
        return inventory
    }

    override fun getTitle(player: Player, page: Int): String {
        return "${MenuMessages.PROFILE_RAID_BOSS.asSafety(player.wrappedLocale)} $page/$maxPage"
    }

    override fun getButton(page: Int, slot: Int): Button? {
        return when (slot) {
            numOfBossInAPage + 3 -> prevButton
            numOfBossInAPage + 5 -> nextButton
            else -> null
        }
    }

}