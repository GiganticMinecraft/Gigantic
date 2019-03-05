package click.seichi.gigantic.message.messages

import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.ranking.Score
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object RankingMessages {

    val TITLE = LocalizedText(
            Locale.JAPANESE to "ランキング"
    )

    val DIAMOND = LocalizedText(
            Locale.JAPANESE to "${ChatColor.AQUA}${ChatColor.BOLD}" +
                    "ダイヤモンド"
    )

    val GOLD = LocalizedText(
            Locale.JAPANESE to "${ChatColor.GOLD}${ChatColor.BOLD}" +
                    "ゴールド"
    )

    val SILVER = LocalizedText(
            Locale.JAPANESE to "${ChatColor.GRAY}${ChatColor.BOLD}" +
                    "シルバー"
    )

    val RANKED_PLAYER = { score: Score, rank: Int ->
        val rankColor = when (rank) {
            1 -> ChatColor.AQUA
            2 -> ChatColor.GOLD
            3 -> ChatColor.GRAY
            else -> ChatColor.WHITE
        }
        LocalizedText(
                Locale.JAPANESE.let {
                    it to "${ChatColor.YELLOW}${ChatColor.BOLD}" +
                            "${score.getName(it)} " +
                            "$rankColor${ChatColor.BOLD}${rank}位"
                }
        )
    }

    val RANKED_PLAYER_LORE = { playerName: String, value: Long ->
        listOf(
                LocalizedText(
                        Locale.JAPANESE to "${ChatColor.GREEN}${ChatColor.BOLD}$playerName"
                ),
                LocalizedText(
                        Locale.JAPANESE.let {
                            it to "${ChatColor.WHITE}${ChatColor.BOLD}" +
                                    "$value"
                        }
                )
        )
    }


}