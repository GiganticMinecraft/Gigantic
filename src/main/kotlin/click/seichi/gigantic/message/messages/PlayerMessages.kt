package click.seichi.gigantic.message.messages

import click.seichi.gigantic.config.PlayerLevelConfig
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.message.*
import click.seichi.gigantic.player.Defaults
import click.seichi.gigantic.will.Will
import org.bukkit.ChatColor
import org.bukkit.entity.Player
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

    val STRIP_EXP = { count: Long ->
        ChatMessage(ChatMessageProtocol.ACTION_BAR, LocalizedText(
                Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                        "$count ブロック分の露天掘りを達成した ( " +
                        "${ChatColor.AQUA}${ChatColor.BOLD}" +
                        "${count.times(Defaults.STRIP_BONUS)} exp" +
                        "${ChatColor.LIGHT_PURPLE}" +
                        " 獲得)"
        ))
    }

    val GET_TOTEM_PIECE = { num: Int ->
        ChatMessage(ChatMessageProtocol.ACTION_BAR, LocalizedText(
                Locale.JAPANESE to "${ChatColor.GREEN}" +
                        "トーテムのかけらを獲得($num/${Defaults.MAX_TOTEM_PIECE})"
        ))
    }

    val COMPLETE_TOTEM = ChatMessage(ChatMessageProtocol.ACTION_BAR, LocalizedText(
            Locale.JAPANESE to "${ChatColor.GREEN}" +
                    "フォーカス・トーテム 再使用可能"
    ))

    val LOCATION_INFO = { player: Player ->
        val world = player.world
        val location = player.location
        val block = location.block
        val junishi = world.junishiOfTime
        val moonPhase = world.moonPhase
        val biome = location.block.biome
        val heightGrade = block.heightGrade
        val temperatureGrade = block.temperatureGrade
        val biomeGroup = block.biomeGroup
        TabListMessage(
                LocalizedText(
                        Locale.JAPANESE.let {
                            it to "${ChatColor.WHITE}" +
                                    "======" +
                                    "${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}" +
                                    "整地鯖(春)" +
                                    "${ChatColor.WHITE}" +
                                    "======" +
                                    "\n" +
                                    "${ChatColor.WHITE}" +
                                    "時間: " +
                                    "${ChatColor.DARK_AQUA}${ChatColor.BOLD}" +
                                    "${junishi.getName(it)}の刻 " +
                                    "${ChatColor.WHITE}" +
                                    "月齢: " +
                                    "${ChatColor.YELLOW}${ChatColor.BOLD}" +
                                    moonPhase.getName(it) +
                                    "\n" +
                                    "${ChatColor.WHITE}" +
                                    "高さ: " +
                                    "${heightGrade.chatColor}${ChatColor.BOLD}" +
                                    heightGrade.getName(it) +
                                    "${ChatColor.WHITE} " +
                                    "気温: " +
                                    "${temperatureGrade.chatColor}${ChatColor.BOLD}" +
                                    temperatureGrade.getName(it) +
                                    "\n" +
                                    "${ChatColor.WHITE}" +
                                    "環境: " +
                                    "${biomeGroup?.chatColor ?: ChatColor.DARK_PURPLE}${ChatColor.BOLD}" +
                                    "${biomeGroup?.getName(it) ?: ""} " +
                                    BiomeMessages.BIOME(biome).asSafety(it)
                        }
                ),
                LocalizedText(
                        Locale.JAPANESE to "${ChatColor.WHITE}" +
                                "======" +
                                "${ChatColor.AQUA}${ChatColor.BOLD}" +
                                "seichi.click" +
                                "${ChatColor.WHITE}" +
                                "======"
                )
        )
    }

    val FRIEND_RATIO = { will: Will, ratio: Int ->
        ChatMessage(ChatMessageProtocol.ACTION_BAR, LocalizedText(
                Locale.JAPANESE.let {
                    it to "" + will.chatColor + "${ChatColor.BOLD}" +
                            will.getName(it) + "の意志" +
                            "${ChatColor.WHITE}との" +
                            "${ChatColor.GREEN}${ChatColor.BOLD}" +
                            "友好度" +
                            "${ChatColor.WHITE}が" +
                            "${ChatColor.GREEN}${ChatColor.BOLD}" +
                            "1%" +
                            "${ChatColor.WHITE}" +
                            "上昇(合計$ratio%)"

                }
        ))
    }

}