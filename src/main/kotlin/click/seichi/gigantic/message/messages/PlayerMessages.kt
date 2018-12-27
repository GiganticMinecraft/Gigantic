package click.seichi.gigantic.message.messages

import click.seichi.gigantic.config.PlayerLevelConfig
import click.seichi.gigantic.message.*
import click.seichi.gigantic.player.Defaults
import click.seichi.gigantic.will.Will
import org.bukkit.ChatColor
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

/**
 * @author tar0ss
 */
object PlayerMessages {

    val EXP_BAR_DISPLAY = { level: Int, exp: BigDecimal ->
        val expToLevel = PlayerLevelConfig.LEVEL_MAP[level] ?: BigDecimal.ZERO
        val expToNextLevel = PlayerLevelConfig.LEVEL_MAP[level + 1]
                ?: PlayerLevelConfig.LEVEL_MAP[PlayerLevelConfig.MAX]!!
        LevelMessage(level, (exp - expToLevel).setScale(2).divide((expToNextLevel - expToLevel), 10, RoundingMode.HALF_UP).toFloat().coerceAtLeast(Float.MIN_VALUE))
    }

    val HEALTH_DISPLAY = { health: Long, maxHealth: Long ->
        val interval = maxHealth.div(20.0)
        val healthAmount = health.div(interval)
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

    val DEATH_PENALTY = { penaltyMineBlock: BigDecimal ->
        ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
                Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                        "経験値を${penaltyMineBlock.setScale(1)} 失った..."
        ))
    }


    val MANA_DISPLAY = { mana: BigDecimal, maxMana: BigDecimal ->
        val interval = maxMana.divide(Defaults.MANA_BAR_NUM.toBigDecimal(), 10, RoundingMode.HALF_UP)
        val amount = mana.divide(interval, 10, RoundingMode.HALF_UP)
        ManaMessage(mana, maxMana, amount.toDouble())
    }

    val SPAWN_PROTECT = ChatMessage(ChatMessageProtocol.ACTION_BAR, LocalizedText(
            Locale.JAPANESE to "${ChatColor.RED}" +
                    "スポーン地点に近いので保護されている"
    ))

    val BREAK_NOT_TOOL = ChatMessage(ChatMessageProtocol.ACTION_BAR, LocalizedText(
            Locale.JAPANESE to "${ChatColor.RED}" +
                    "正しいツールを使って破壊しよう"
    ))

    val BREAK_UNDER_BLOCK_NOT_SNEAKING = ChatMessage(ChatMessageProtocol.ACTION_BAR, LocalizedText(
            Locale.JAPANESE to "${ChatColor.RED}" +
                    "自分より低いブロックはスニークしながら破壊しよう"
    ))

    val BATTLE_ANOTHER_PLAYER = ChatMessage(ChatMessageProtocol.ACTION_BAR, LocalizedText(
            Locale.JAPANESE to "${ChatColor.RED}" +
                    "他のプレイヤーがバトル中"
    ))

    val BREAK_OTHER_CHUNK = ChatMessage(ChatMessageProtocol.ACTION_BAR, LocalizedText(
            Locale.JAPANESE to "${ChatColor.RED}" +
                    "バトル中は敵のいるチャンクしか掘れない"
    ))

    val DECREASE_COMBO = { decreaseCombo: Long ->
        ChatMessage(ChatMessageProtocol.ACTION_BAR, LocalizedText(
                Locale.JAPANESE to "${ChatColor.RED}" +
                        "時間経過によりコンボが$decreaseCombo 減少"
        ))
    }

    val UPDATE_MAX_COMBO = { prevmaxCombo: Long, maxCombo: Long ->
        ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
                Locale.JAPANESE to "${ChatColor.YELLOW}" +
                        "最大コンボ数更新!! ($prevmaxCombo → $maxCombo)"
        ))
    }

}