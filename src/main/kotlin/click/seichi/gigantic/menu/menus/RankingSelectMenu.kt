package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.item.items.menu.RankingButtons
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.RankingMessages
import click.seichi.gigantic.ranking.Score
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
object RankingSelectMenu : Menu() {
    override val size: Int
        get() = 9 * 4

    override fun getTitle(player: Player): String {
        return RankingMessages.TITLE(Gigantic.RANKING_UPDATE_TIME).asSafety(player.wrappedLocale)
    }

    init {
        registerButton(9, RankingButtons.DIAMOND)
        registerButton(18, RankingButtons.GOLD)
        registerButton(27, RankingButtons.SILVER)
        Score.values().forEachIndexed { index, score ->
            registerButton(1 + index, RankingButtons.SCORE(score))
            registerButton(10 + index, RankingButtons.RANKED_PLAYER(score, 1))
            registerButton(19 + index, RankingButtons.RANKED_PLAYER(score, 2))
            registerButton(28 + index, RankingButtons.RANKED_PLAYER(score, 3))
        }
    }

}