package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.cache.RankingPlayerCacheMemory
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.offer
import click.seichi.gigantic.extension.setItemAsync
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.item.items.menu.BackButton
import click.seichi.gigantic.item.items.menu.NextButton
import click.seichi.gigantic.item.items.menu.PrevButton
import click.seichi.gigantic.item.items.menu.RankingButtons
import click.seichi.gigantic.menu.BookMenu
import click.seichi.gigantic.ranking.RankingPlayer
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

/**
 * @author tar0ss
 */
object RankingMenu : BookMenu() {

    override val size: Int
        get() = 6 * 9

    private const val numOfContentsPerPage = 5 * 9

    private const val offset = 0

    init {
        registerButton(size - 9, BackButton(this, RankingSelectMenu))
        registerButton(size - 6, PrevButton(this))
        registerButton(size - 4, NextButton(this))
        registerButton(size - 2, RankingButtons.PLAYER_PAGE(numOfContentsPerPage))
    }

    override fun getMaxPage(player: Player): Int {
        val score = player.getOrPut(Keys.MENU_RANKING_SCORE)
        val ranking = Gigantic.RANKING_MAP.getValue(score)
        return ranking.size.minus(1).div(numOfContentsPerPage).plus(1).coerceAtLeast(1)
    }

    override fun onOpen(player: Player, page: Int, isFirst: Boolean) {
        val score = player.getOrPut(Keys.MENU_RANKING_SCORE)
        val ranking = Gigantic.RANKING_MAP.getValue(score)
        val start = (page - 1) * numOfContentsPerPage
        val end = page * numOfContentsPerPage
        player.offer(Keys.MENU_RANKING_PLAYER_LIST,
                ranking.sliceByRank(start + 1..end)
                        .mapNotNull { uniqueId ->
                            RankingPlayer(
                                    uniqueId,
                                    ranking.findRank(uniqueId)!!,
                                    RankingPlayerCacheMemory.find(uniqueId) ?: return@mapNotNull null
                            )
                        }
        )
    }

    override fun setItem(inventory: Inventory, player: Player, page: Int): Inventory {
        val score = player.getOrPut(Keys.MENU_RANKING_SCORE)
        player.getOrPut(Keys.MENU_RANKING_PLAYER_LIST)
                .forEachIndexed { index, rankingPlayer ->
                    inventory.setItemAsync(player, index + offset, RankingButtons.RANKING_PLAYER(rankingPlayer, score))
                }
        getButtonMap().forEach { slot, button ->
            inventory.setItemAsync(player, slot, button)
        }
        return inventory
    }

    override fun getTitle(player: Player, page: Int): String {
        val score = player.getOrPut(Keys.MENU_RANKING_SCORE)
        return "${score.getName(player.wrappedLocale)} $page/${getMaxPage(player)}"
    }

    override fun getButton(player: Player, page: Int, slot: Int): Button? {
        val index = slot - offset
        val score = player.getOrPut(Keys.MENU_RANKING_SCORE)
        return getButtonMap()[slot]
                ?: RankingButtons.RANKING_PLAYER(player.getOrPut(Keys.MENU_RANKING_PLAYER_LIST)[index], score)
    }

}