package click.seichi.gigantic.message.messages

import click.seichi.gigantic.cache.manipulator.manipulators.*
import click.seichi.gigantic.config.PlayerLevelConfig
import click.seichi.gigantic.message.*
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

    val MEMORY_SIDEBAR = { memory: Memory, aptitude: WillAptitude, isForced: Boolean ->
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
                                        "${willMap[will] ?: 0}個"
                            }
                    )
                }.toMap()
                , isForced
        )
    }

    val EXP_BAR_DISPLAY = { level: Level ->
        val expToLevel = PlayerLevelConfig.LEVEL_MAP[level.current] ?: 0L
        val expToNextLevel = PlayerLevelConfig.LEVEL_MAP[level.current + 1]
                ?: PlayerLevelConfig.LEVEL_MAP[PlayerLevelConfig.MAX]!!
        LevelMessage(level.current, (level.exp - expToLevel).div((expToNextLevel - expToLevel).toFloat().coerceAtLeast(Float.MIN_VALUE)))
    }

    val HEALTH_DISPLAY = { health: Health ->
        val interval = health.max.div(20.0)
        val healthAmount = health.current.div(interval)
        HealthMessage(healthAmount)
    }

    val FIRST_JOIN = ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
            Locale.JAPANESE to "${ChatColor.AQUA}${ChatColor.BOLD}" +
                    "ブロックを壊そう!!\n"
    ))

    val OBTAIN_WILL_APTITUDE = { will: Will ->
        ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
                Locale.JAPANESE.let { it to "${ChatColor.AQUA}${ChatColor.BOLD}新しく${will.localizedName.asSafety(it)}の遺志と交感できるようになった" }
        ))
    }

    val LEVEL_UP_LEVEL = { level: Int ->
        ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
                Locale.JAPANESE to "${ChatColor.AQUA}" +
                        "レベルアップ!! ( ${level - 1} → $level )"
        ))
    }

    val LEVEL_UP_MANA = { prevMax: Long, nextMax: Long ->
        ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
                Locale.JAPANESE to "${ChatColor.AQUA}" +
                        "マナの最大値が上がった ( $prevMax → $nextMax )"
        ))
    }

    val LEVEL_UP_HEALTH = { prevMax: Long, nextMax: Long ->
        ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
                Locale.JAPANESE to "${ChatColor.AQUA}" +
                        "体力の最大値が上がった ( $prevMax → $nextMax )"
        ))
    }

    val DEATH_PENALTY = { penaltyMineBlock: Long ->
        ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
                Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                        "経験値を$penaltyMineBlock 失った..."
        ))
    }

}