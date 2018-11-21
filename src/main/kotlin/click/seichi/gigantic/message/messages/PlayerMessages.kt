package click.seichi.gigantic.message.messages

import click.seichi.gigantic.cache.manipulator.manipulators.Health
import click.seichi.gigantic.cache.manipulator.manipulators.Level
import click.seichi.gigantic.cache.manipulator.manipulators.Mana
import click.seichi.gigantic.config.PlayerLevelConfig
import click.seichi.gigantic.message.*
import click.seichi.gigantic.player.Defaults
import click.seichi.gigantic.will.Will
import org.bukkit.ChatColor
import java.math.BigDecimal
import java.util.*

/**
 * @author tar0ss
 */
object PlayerMessages {


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


    val OBTAIN_WILL_APTITUDE = { will: Will ->
        ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
                Locale.JAPANESE.let { it to "${ChatColor.AQUA}新しく${will.localizedName.asSafety(it)}の遺志と交感できるようになった" }
        ))
    }

    val LEVEL_UP_LEVEL = { level: Int ->
        ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
                Locale.JAPANESE to "${ChatColor.AQUA}" +
                        "おめでとう!!レベルが上がった!! ( ${level - 1} → $level )"
        ))
    }

    val LEVEL_UP_TITLE = { level: Int ->
        TitleMessage(
                title = LocalizedText(
                        Locale.JAPANESE to "${ChatColor.AQUA}レベルアップ!!"
                ),
                subTitle = LocalizedText(
                        Locale.JAPANESE to "${ChatColor.WHITE}${level - 1} → $level"
                ),
                fadeIn = 10,
                stay = 120,
                fadeOut = 10
        )
    }

    val LEVEL_UP_MANA = { prevMax: BigDecimal, nextMax: BigDecimal ->
        ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
                Locale.JAPANESE to "${ChatColor.AQUA}" +
                        "マナの最大値が上がった ( ${prevMax.toLong()} → ${nextMax.toLong()} )"
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


    val MANA_DISPLAY = { mana: Mana ->
        val interval = mana.max.div(Defaults.MANA_BAR_NUM.toBigDecimal())
        val amount = mana.current.div(interval)
        ManaMessage(mana, amount.toDouble())
    }

    val SPAWN_PROTECT = ChatMessage(ChatMessageProtocol.ACTION_BAR, LocalizedText(
            Locale.JAPANESE to "${ChatColor.RED}" +
                    "スポーン地点に近いため、保護されています"
    ))

    val BATTLE_ANOTHER_PLAYER = ChatMessage(ChatMessageProtocol.ACTION_BAR, LocalizedText(
            Locale.JAPANESE to "${ChatColor.RED}" +
                    "他のプレイヤーがバトル中です"
    ))

    val BREAK_OTHER_CHUNK = ChatMessage(ChatMessageProtocol.ACTION_BAR, LocalizedText(
            Locale.JAPANESE to "${ChatColor.RED}" +
                    "バトル中は敵のいるチャンクしか掘れません"
    ))


}