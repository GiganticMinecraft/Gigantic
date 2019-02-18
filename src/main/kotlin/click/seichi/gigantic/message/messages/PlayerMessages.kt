package click.seichi.gigantic.message.messages

import click.seichi.gigantic.config.PlayerLevelConfig
import click.seichi.gigantic.message.*
import org.bukkit.ChatColor
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

/**
 * @author tar0ss
 */
object PlayerMessages {

    val PLAYER_LIST_NAME_PREFIX = { level: Int ->
        "[Lv$level]"
    }
    val DISPLAY_NAME_PREFIX = { level: Int ->
        "[Lv$level]"
    }

    val PLAYER_LIST_HEADER = LocalizedText(
            Locale.JAPANESE to "${ChatColor.WHITE}" +
                    "======" +
                    "${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}" +
                    "整地鯖(春)" +
                    "${ChatColor.WHITE}" +
                    "======"
    )

    val PLAYER_LIST_FOOTER = LocalizedText(
            Locale.JAPANESE to "${ChatColor.WHITE}" +
                    "======" +
                    "${ChatColor.AQUA}${ChatColor.BOLD}" +
                    "整地専用サーバー" +
                    "${ChatColor.WHITE}" +
                    "======"
    )

    val EXP_BAR_DISPLAY = { level: Int, exp: BigDecimal ->
        val expToLevel = PlayerLevelConfig.LEVEL_MAP[level] ?: BigDecimal.ZERO
        val expToNextLevel = PlayerLevelConfig.LEVEL_MAP[level + 1]
                ?: PlayerLevelConfig.LEVEL_MAP[PlayerLevelConfig.MAX]!!
        LevelMessage(level, (exp - expToLevel).setScale(2, RoundingMode.FLOOR).divide((expToNextLevel - expToLevel), 10, RoundingMode.HALF_UP).toFloat().coerceAtLeast(Float.MIN_VALUE))
    }

    val LEVEL_UP_LEVEL = { level: Int ->
        ChatMessage(ChatMessageProtocol.CHAT, LocalizedText(
                Locale.JAPANESE to "${ChatColor.AQUA}" +
                        "おめでとう!レベルが上がった! ( ${level - 1} → $level )"
        ))
    }

    val LEVEL_UP_TITLE = { level: Int ->
        TitleMessage(
                title = LocalizedText(
                        Locale.JAPANESE to "${ChatColor.AQUA}レベルアップ"
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
                        "経験値を${penaltyMineBlock.setScale(0, RoundingMode.HALF_UP)} 失った..."
        ))
    }

    val NO_MANA = ChatMessage(ChatMessageProtocol.ACTION_BAR, LocalizedText(
            Locale.JAPANESE to "${ChatColor.AQUA}" +
                    "マナが足りません"
    ))


    val MANA_DISPLAY = { mana: BigDecimal, maxMana: BigDecimal ->
        val interval = maxMana.divide(20.toBigDecimal(), 10, RoundingMode.HALF_UP)
        val amount = mana.divide(interval, 10, RoundingMode.HALF_UP)
        ManaMessage(amount.toDouble())
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

    val NOT_BREAK_NEAR_ANOTHER_PLAYER = ChatMessage(ChatMessageProtocol.ACTION_BAR, LocalizedText(
            Locale.JAPANESE to "${ChatColor.RED}" +
                    "他のプレイヤーと距離を取ろう"
    ))

    val NOT_BREAK_OVER_GRAVITY = ChatMessage(ChatMessageProtocol.ACTION_BAR, LocalizedText(
            Locale.JAPANESE to "${ChatColor.RED}" +
                    "上から掘ろう"
    ))

    val FLOOR_BLOCK = ChatMessage(ChatMessageProtocol.ACTION_BAR, LocalizedText(
            Locale.JAPANESE to "${ChatColor.RED}" +
                    "これ以上深く掘れない"
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
                        "最大コンボ数更新 ($prevmaxCombo → $maxCombo)"
        ))
    }

    val EXP = { count: Int ->
        ChatMessage(ChatMessageProtocol.ACTION_BAR, LocalizedText(
                Locale.JAPANESE to "${ChatColor.GRAY}${ChatColor.BOLD}" +
                        "経験値を $count 獲得"
        ))
    }

    val EXP_AND_BONUS = { count: Int, bonus: Double ->
        ChatMessage(ChatMessageProtocol.ACTION_BAR, LocalizedText(
                Locale.JAPANESE to "${ChatColor.GRAY}${ChatColor.BOLD}" +
                        "経験値を $count " +
                        "${ChatColor.YELLOW}${ChatColor.BOLD}" +
                        "+ ${bonus.toBigDecimal().setScale(2, RoundingMode.HALF_UP)}" +
                        "${ChatColor.GRAY}${ChatColor.BOLD}" +
                        " 獲得"
        ))
    }

    val LOGIN_CHAT = LinedChatMessage(ChatMessageProtocol.CHAT,
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.WHITE}" +
                            (1..53).joinToString("") { "-" } +
                            LinedChatMessage.NEW_LINE_SYMBOL +
                            "${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}" +
                            "整地鯖(春)" +
                            "${ChatColor.WHITE}" +
                            "へようこそ!" +
                            LinedChatMessage.NEW_LINE_SYMBOL +
                            "${ChatColor.AQUA}${ChatColor.BOLD}" +
                            "オープンβテスト" +
                            "${ChatColor.WHITE}" +
                            "中の為、" +
                            "${ChatColor.RED}${ChatColor.BOLD}" +
                            "レベルのリセット" +
                            "${ChatColor.WHITE}" +
                            "等が" +
                            LinedChatMessage.NEW_LINE_SYMBOL +
                            "${ChatColor.WHITE}" +
                            "起こる可能性がありますことを予めご了承ください" +
                            LinedChatMessage.NEW_LINE_SYMBOL +
                            "${ChatColor.WHITE}" +
                            "※" +
                            "${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}" +
                            "整地鯖(春)" +
                            "${ChatColor.WHITE}" +
                            "に関するお問い合わせを" +
                            LinedChatMessage.NEW_LINE_SYMBOL +
                            "${ChatColor.GREEN}${ChatColor.BOLD}" +
                            "ギガンティック整地鯖" +
                            "${ChatColor.WHITE}" +
                            "運営チームに行わないでください" +
                            LinedChatMessage.NEW_LINE_SYMBOL +
                            "${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}" +
                            "整地鯖(春)" +
                            "${ChatColor.WHITE}" +
                            "に関するご質問は" +
                            "${ChatColor.DARK_PURPLE}${ChatColor.BOLD}" +
                            "公式ディスコード" +
                            "${ChatColor.WHITE}" +
                            "で承ります" +
                            LinedChatMessage.NEW_LINE_SYMBOL +
                            "${ChatColor.WHITE}" +
                            "公式ディスコード: " +
                            "${ChatColor.YELLOW}" +
                            "https://discord.gg/nmhjtC5" +
                            LinedChatMessage.NEW_LINE_SYMBOL +
                            "${ChatColor.WHITE}" +
                            "寄付受付: " +
                            "${ChatColor.YELLOW}" +
                            "https://goo.gl/forms/8ZR3MJwtSeTDkGST2" +
                            LinedChatMessage.NEW_LINE_SYMBOL +
                            "${ChatColor.WHITE}" +
                            "リソースパック手動ダウンロードリンク↓" +
                            LinedChatMessage.NEW_LINE_SYMBOL +
                            "${ChatColor.YELLOW}" +
                            "https://red.minecraftserver.jp/attachments/download/872/Spring_Texture_ver1.1.zip" +
                            LinedChatMessage.NEW_LINE_SYMBOL +
                            "${ChatColor.WHITE}" +
                            (1..53).joinToString("") { "-" }

            ))

    val LOGIN_TITLE = TitleMessage(
            title = LocalizedText(
                    Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                            "整地鯖(春)"
            ), subTitle = null)

}