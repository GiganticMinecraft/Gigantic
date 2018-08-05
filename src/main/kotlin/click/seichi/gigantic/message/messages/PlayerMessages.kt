package click.seichi.gigantic.message.messages

import click.seichi.gigantic.config.PlayerLevelConfig
import click.seichi.gigantic.message.*
import click.seichi.gigantic.player.components.Mana
import click.seichi.gigantic.player.components.Memory
import click.seichi.gigantic.player.components.WillAptitude
import click.seichi.gigantic.util.SideBarRow
import click.seichi.gigantic.will.Will
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object PlayerMessages {

    val MANA_BAR_TITLE = { mana: Mana ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.AQUA}${ChatColor.BOLD}マナ ${mana.current} / ${mana.max}"
        )
    }

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
                                        "${will.localizedName.asSafety(it)} : " +
                                        "${ChatColor.RESET}${ChatColor.WHITE}" +
                                        "${willMap[will]}個"
                            }
                    )
                }.toMap()
                , true
        )
    }

    val EXP_BAR_DISPLAY = { level: Int, exp: Long ->
        val expToLevel = PlayerLevelConfig.LEVEL_MAP[level] ?: 0L
        val expToNextLevel = PlayerLevelConfig.LEVEL_MAP[level + 1]
                ?: PlayerLevelConfig.LEVEL_MAP[PlayerLevelConfig.MAX]!!

        LevelMessage(level, (exp - expToLevel).div((expToNextLevel - expToLevel).toFloat().coerceAtLeast(Float.MIN_VALUE)))
    }

    val FIRST_JOIN = ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            // TODO
            Locale.JAPANESE to "${ChatColor.AQUA}${ChatColor.BOLD}" +
                    "ブロックを破壊しよう"
    ))

    val OBTAIN_WILL_APTITUDE = { will: Will ->
        ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
                Locale.JAPANESE.let { it to "${ChatColor.AQUA}${ChatColor.BOLD}新しく${will.localizedName.asSafety(it)}の遺志と交感できるようになった" }
        ))
    }

    val FIRST_OBTAIN_WILL_APTITUDE = { will: Will ->
        ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
                Locale.JAPANESE.let {
                    it to "${ChatColor.GRAY}" +
                            "ブロックを破壊すると、稀に遺志が現れるぞ!!\n" +
                            "遺志と交感することで記憶を獲得しよう\n" +
                            "${ChatColor.AQUA}${ChatColor.BOLD}" +
                            "${will.localizedName.asSafety(it)}の遺志と交感できるようになった\n" +
                            "${ChatColor.GRAY}" +
                            "遺志は${Will.values().size}種類存在する\n" +
                            "レベルを上げてたくさんの遺志と交感しよう!!\n"
                }
        ))
    }

}