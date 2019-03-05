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

    val RANKED_PLAYER = { rank: Int, playerName: String ->
        val rankColor = when (rank) {
            1 -> ChatColor.AQUA
            2 -> ChatColor.GOLD
            3 -> ChatColor.GRAY
            else -> ChatColor.WHITE
        }
        LocalizedText(
                Locale.JAPANESE.let {
                    it to "$rankColor${ChatColor.BOLD}${rank}位" +
                            " $playerName"
                }
        )
    }

    val RANKED_PLAYER_LORE = { value: Long ->
        listOf(
                LocalizedText(
                        Locale.JAPANESE.let {
                            it to "${ChatColor.WHITE}${ChatColor.BOLD}" +
                                    "$value"
                        }
                )
        )
    }

    val SCORE = { score: Score ->

        LocalizedText(
                Locale.JAPANESE.let {
                    it to "${ChatColor.YELLOW}${ChatColor.BOLD}" +
                            score.getName(it)
                }
        )
    }

    val SCORE_LORE = { rank: Int, value: Long ->
        listOf(
                LocalizedText(
                        Locale.JAPANESE.let {
                            it to "${ChatColor.GREEN}" +
                                    "あなたの順位: " +
                                    "${ChatColor.WHITE}${ChatColor.BOLD}" +
                                    "$rank 位"
                        }
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