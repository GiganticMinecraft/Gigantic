package click.seichi.gigantic.message.messages

import click.seichi.gigantic.config.PlayerLevelConfig
import click.seichi.gigantic.message.*
import click.seichi.gigantic.player.components.Level
import click.seichi.gigantic.player.components.Mana
import click.seichi.gigantic.player.components.Memory
import click.seichi.gigantic.player.components.WillAptitude
import click.seichi.gigantic.util.DetailedSound
import click.seichi.gigantic.util.Random
import click.seichi.gigantic.util.SideBarRow
import click.seichi.gigantic.will.Will
import org.bukkit.ChatColor
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import java.util.*

/**
 * @author tar0ss
 */
object PlayerMessages {

    val MEMORY_SIDEBAR = { memory: Memory, aptitude: WillAptitude ->
        val willMap = Will.values()
                .filter { aptitude.has(it) }
                .map { it to (memory.get(it)) }.toMap()
        SideBarMessage(
                "memory",
                LocalizedText(
                        Locale.JAPANESE to "${ChatColor.DARK_GREEN}${ChatColor.BOLD}" +
                                "遺志の記憶"
                ),
                willMap.keys.map { will ->
                    SideBarRow.getRowById(will.id) to LocalizedText(
                            Locale.JAPANESE.let {
                                it to "${ChatColor.GREEN}${ChatColor.BOLD}" +
                                        "${will.LocalizedText.asSafety(it)} : " +
                                        "${ChatColor.RESET}${ChatColor.WHITE}" +
                                        "${willMap[will]}個"
                            }
                    )
                }.toMap()
                , true
        )
    }

    val LEVEL_DISPLAY = { level: Level ->
        val expToLevel = PlayerLevelConfig.LEVEL_MAP[level.current] ?: 0L
        val expToNextLevel = PlayerLevelConfig.LEVEL_MAP[level.current + 1]
                ?: PlayerLevelConfig.LEVEL_MAP[PlayerLevelConfig.MAX]!!
        LevelMessage(level.current, (level.exp - expToLevel).div((expToNextLevel - expToLevel).toFloat()))
    }

    val MANA_DISPLAY = { bar: BossBar, mana: Mana ->
        mana.run {
            val progress = current.div(max.toDouble()).let { if (it > 1.0) 1.0 else it }
            BossBarMessage(
                    bar,
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.AQUA}${ChatColor.BOLD}$current / $max"
                    ),
                    progress,
                    when (progress) {
                        1.00 -> BarColor.WHITE
                        in 0.99..1.00 -> BarColor.PURPLE
                        in 0.10..0.99 -> BarColor.BLUE
                        in 0.01..0.10 -> BarColor.PINK
                        in 0.00..0.01 -> BarColor.RED
                        else -> BarColor.YELLOW
                    },
                    BarStyle.SOLID
            )
        }
    }


    val FIRST_JOIN = ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            // TODO
            Locale.JAPANESE to "${ChatColor.AQUA}${ChatColor.BOLD}" +
                    "ブロックを破壊しましょう"
    ))

    val OBTAIN_WILL_APTITUDE = { will: Will ->
        ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
                Locale.JAPANESE.let { it to "${ChatColor.AQUA}${ChatColor.BOLD}新しく${will.LocalizedText.asSafety(it)}の遺志と交感できるようになった" }
        ))
    }

    val FIRST_OBTAIN_WILL_APTITUDE = { will: Will ->
        ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
                Locale.JAPANESE.let {
                    it to "${ChatColor.GRAY}" +
                            "ブロックを破壊すると、稀に遺志が現れます\n" +
                            "遺志と交感することで記憶を獲得できます\n" +
                            "${ChatColor.AQUA}${ChatColor.BOLD}" +
                            "${will.LocalizedText.asSafety(it)}の遺志と交感できるようになりました\n" +
                            "${ChatColor.GRAY}" +
                            "遺志は${Will.values().size}種類存在します\n" +
                            "レベルを上げてたくさんの遺志と交感しましょう\n"
                }
        ))
    }


    val OBTAIN_EXP
        get() = WorldSound(
                DetailedSound(
                        Sound.ENTITY_EXPERIENCE_ORB_PICKUP,
                        SoundCategory.PLAYERS,
                        pitch = Random.nextGaussian(1.0, 0.2).toFloat(),
                        volume = 0.2F
                )
        )

    val LEVEL_UP = WorldSound(
            DetailedSound(
                    Sound.ENTITY_PLAYER_LEVELUP,
                    SoundCategory.PLAYERS,
                    pitch = 0.8F,
                    volume = 0.5F
            )
    )

}