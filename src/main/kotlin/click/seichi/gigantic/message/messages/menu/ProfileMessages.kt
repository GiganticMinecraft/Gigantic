package click.seichi.gigantic.message.messages.menu

import click.seichi.gigantic.config.PlayerLevelConfig
import click.seichi.gigantic.extension.hasAptitude
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.will.Will
import click.seichi.gigantic.will.WillGrade
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

/**
 * @author tar0ss
 */
object ProfileMessages {

    val TITLE = LocalizedText(
            Locale.JAPANESE to "プロフィール"
    )

    val PROFILE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.AQUA}プロフィール"
    )

    val UPDATE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.YELLOW}${ChatColor.UNDERLINE}クリックして更新"
    )

    val UPDATING = LocalizedText(
            Locale.JAPANESE to "${ChatColor.YELLOW}更新中....."
    )

    val PROFILE_LEVEL = { level: Int ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.GREEN}整地レベル: ${ChatColor.WHITE}$level"
        )
    }

    val PROFILE_EXP = { level: Int, exp: BigDecimal ->
        val isMax = level == PlayerLevelConfig.MAX
        if (isMax) {
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.GREEN}経験値: ${ChatColor.WHITE}${exp.setScale(0, RoundingMode.FLOOR)} / ${exp.setScale(0, RoundingMode.FLOOR)}"
            )
        } else {
            val expToNextLevel = PlayerLevelConfig.LEVEL_MAP[level + 1]
                    ?: PlayerLevelConfig.LEVEL_MAP[PlayerLevelConfig.MAX]!!
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.GREEN}経験値: ${ChatColor.WHITE}${exp.setScale(0, RoundingMode.FLOOR)} / ${expToNextLevel.setScale(0, RoundingMode.FLOOR)}"
            )
        }
    }

    val PROFILE_MANA = { mana: BigDecimal, maxMana: BigDecimal ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.GREEN}マナ: ${ChatColor.WHITE}${mana.setScale(1, RoundingMode.HALF_UP)} / ${maxMana.setScale(1, RoundingMode.HALF_UP)}"
        )
    }

    val PROFILE_MAX_COMBO = { maxCombo: Long ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.GREEN}最大コンボ数: ${ChatColor.WHITE}$maxCombo combo"
        )
    }

    val PROFILE_VOTE_POINT = { votePoint: Int ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.GREEN}累計投票数: ${ChatColor.WHITE}$votePoint"
        )
    }

    val PROFILE_POMME = { pomme: Int ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.GREEN}累計ポム: ${ChatColor.WHITE}$pomme"
        )
    }

    val PROFILE_DONATION = { donation: Int ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.GREEN}累計寄付額: ${ChatColor.WHITE}$donation"
        )
    }

    val PROFILE_WILL_APTITUDE_BASIC = { player: Player ->
        mutableListOf(
                LocalizedText(
                        Locale.JAPANESE to "${ChatColor.GREEN}適正遺志"
                ),
                LocalizedText(
                        Locale.JAPANESE.let { locale ->
                            locale to Will.values()
                                    .filter { it.grade == WillGrade.BASIC }
                                    .filter { player.hasAptitude(it) }
                                    .joinToString(" ") {
                                            "${it.chatColor}${ChatColor.BOLD}" + it.getName(locale)
                                    }
                        }
                )
        )
    }

    val PROFILE_WILL_APTITUDE_ADVANCED = { player: Player ->
        mutableListOf(
                LocalizedText(
                        Locale.JAPANESE.let { locale ->
                            locale to Will.values()
                                    .filter { it.grade == WillGrade.ADVANCED }
                                    .filter { player.hasAptitude(it) }
                                    .joinToString(" ") {
                                        "${it.chatColor}${ChatColor.BOLD}" + it.getName(locale)
                                    }
                        }
                )
        )
    }

}