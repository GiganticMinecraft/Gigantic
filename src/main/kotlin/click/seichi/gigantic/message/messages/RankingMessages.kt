package click.seichi.gigantic.message.messages

import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.ranking.Score
import org.bukkit.ChatColor
import org.joda.time.DateTime
import java.util.*

/**
 * @author tar0ss
 */
object RankingMessages {

    val TITLE = { dateTime: DateTime ->
        LocalizedText(
                Locale.JAPANESE to "ランキング ${dateTime.toString("yyyy/MM/dd kk:mm:ss")}"
        )
    }

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

    val RANKED_PLAYER = { rank: Int, playerName: String, level: Int ->
        val rankColor = when (rank) {
            1 -> ChatColor.AQUA
            2 -> ChatColor.GOLD
            3 -> ChatColor.GRAY
            else -> ChatColor.WHITE
        }
        val levelString = if (level == 0) "" else "Lv$level "
        LocalizedText(
                Locale.JAPANESE.let {
                    it to "$rankColor${ChatColor.BOLD}${rank}位 $levelString$playerName"
                }
        )
    }

    val RANKED_PLAYER_LORE = { value: Long, unit: String ->

        LocalizedText(
                Locale.JAPANESE.let {
                    it to "${ChatColor.WHITE}${ChatColor.BOLD}" +
                            "$value $unit"
                }
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

    val NO_DATA = listOf(
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.GREEN}あなたの順位: " +
                            "${ChatColor.RED}" +
                            "不明"
            )
    )

    val CLICK_TO_RANKING = LocalizedText(
            Locale.JAPANESE to "${ChatColor.GREEN}${ChatColor.UNDERLINE}クリックしてランキングを見る"
    )

    val CLICK_TO_LOAD_HEAD = LocalizedText(
            Locale.JAPANESE to "${ChatColor.GREEN}${ChatColor.UNDERLINE}クリックしてスキンをロード"
    )

    val CLICK_TO_LOAD_HEAD_LORE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.RED}※数秒間ゲームが一時停止します"
    )

    val CLICK_TO_MY_RANKING_PAGE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.GREEN}${ChatColor.UNDERLINE}クリックして自分がいるページへ"
    )

}