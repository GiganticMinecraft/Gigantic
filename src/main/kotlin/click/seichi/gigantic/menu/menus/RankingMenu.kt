package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.item.items.menu.RankingButtons
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.RankingMessages
import click.seichi.gigantic.ranking.Score
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
object RankingMenu : Menu() {
    override val size: Int
        get() = 9 * 3

    override fun getTitle(player: Player): String {
        return RankingMessages.TITLE.asSafety(player.wrappedLocale)
    }

    init {
        registerButton(0, RankingButtons.DIAMOND)
        registerButton(9, RankingButtons.GOLD)
        registerButton(18, RankingButtons.SILVER)
        Score.values().forEachIndexed { index, score ->
            registerButton(1 + index, RankingButtons.RANKED_PLAYER(score, 1))
            registerButton(10 + index, RankingButtons.RANKED_PLAYER(score, 2))
            registerButton(19 + index, RankingButtons.RANKED_PLAYER(score, 3))
        }

    }

}