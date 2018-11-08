package click.seichi.gigantic.message.messages.menu

import click.seichi.gigantic.cache.manipulator.manipulators.*
import click.seichi.gigantic.config.PlayerLevelConfig
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.will.Will
import click.seichi.gigantic.will.WillGrade
import org.bukkit.ChatColor
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

    val NEED_UPDATE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.YELLOW}クリックして更新"
    )

    val PROFILE_LEVEL = { level: Level ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.GREEN}整地レベル: ${ChatColor.WHITE}${level.current}"
        )
    }

    val PROFILE_EXP = { level: Level ->
        val isMax = level.current == PlayerLevelConfig.MAX
        if (isMax) {
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.GREEN}経験値: ${ChatColor.WHITE}${level.exp} / ${level.exp}"
            )
        } else {
            val expToNextLevel = PlayerLevelConfig.LEVEL_MAP[level.current + 1]
                    ?: PlayerLevelConfig.LEVEL_MAP[PlayerLevelConfig.MAX]!!
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.GREEN}経験値: ${ChatColor.WHITE}${level.exp} / $expToNextLevel"
            )
        }
    }

    val PROFILE_HEALTH = { health: Health ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.GREEN}体力: ${ChatColor.WHITE}${health.current} / ${health.max}"
        )
    }
    val PROFILE_MANA = { mana: Mana ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.GREEN}マナ: ${ChatColor.WHITE}${mana.current} / ${mana.max}"
        )
    }

    val PROFILE_MAX_COMBO = { mineCombo: MineCombo ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.GREEN}最大コンボ数: ${ChatColor.WHITE}${mineCombo.maxCombo} combo"
        )
    }

    val PROFILE_WILL_APTITUDE = { aptitude: WillAptitude ->
        arrayOf(
                LocalizedText(
                        Locale.JAPANESE to "${ChatColor.GREEN}適正遺志"
                ),
                LocalizedText(
                        Locale.JAPANESE.let { locale ->
                            locale to Will.values()
                                    .filter { it.grade == WillGrade.BASIC }
                                    .joinToString(" ") {
                                        if (aptitude.has(it))
                                            "${ChatColor.WHITE}${it.localizedName.asSafety(locale)}"
                                        else
                                            "${ChatColor.DARK_GRAY}${it.localizedName.asSafety(locale)}"
                                    }
                        }
                ),
                LocalizedText(
                        Locale.JAPANESE.let { locale ->
                            locale to Will.values()
                                    .filter { it.grade == WillGrade.ADVANCED }
                                    .joinToString(" ") {
                                        if (aptitude.has(it))
                                            "${ChatColor.WHITE}${it.localizedName.asSafety(locale)}"
                                        else
                                            "${ChatColor.DARK_GRAY}${it.localizedName.asSafety(locale)}"
                                    }
                        }
                )

        )
    }


}