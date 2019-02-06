package click.seichi.gigantic.message.messages

import click.seichi.gigantic.config.Config
import click.seichi.gigantic.message.LocalizedText
import org.bukkit.ChatColor
import java.math.RoundingMode
import java.util.*

/**
 * @author tar0ss
 */
object SpellMessages {

    val STELLA_CLAIR = LocalizedText(
            Locale.JAPANESE to "ステラ・クレア"
    )

    val STELLA_CLAIR_LORE =
            listOf(
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.GRAY}" +
                                    "パッシブ効果: ブロックを破壊してマナを回復"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.AQUA}" +
                                    "発動確率: ${Config.SPELL_STELLA_CLAIR_PROBABILITY.toBigDecimal().setScale(1, RoundingMode.HALF_UP)} %"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.AQUA}" +
                                    "回復: 最大マナの${Config.SPELL_STELLA_CLAIR_RATIO.toBigDecimal().setScale(1, RoundingMode.HALF_UP)} %"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                                    "全ての通常破壊で発動"
                    )
            )

    val MULTI_BREAK = LocalizedText(
            Locale.JAPANESE to "マルチ・ブレイク"
    )

    val MULTI_BREAK_LORE =
            listOf(
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.GRAY}" +
                                    "アクティブ効果: ブロック破壊時,周囲も破壊"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.AQUA}" +
                                    "消費: ${Config.SPELL_MULTI_BREAK_MANA_PER_BLOCK}マナ/ブロック"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                                    "全ての通常破壊で発動"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                                    ""
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.YELLOW}${ChatColor.BOLD}${ChatColor.UNDERLINE}" +
                                    "クリックで破壊範囲を設定"
                    )
            )

    val SKY_WALK = LocalizedText(
            Locale.JAPANESE to "スカイ・ウォーク"
    )

    val SKY_WALK_LORE =
            listOf(
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.GRAY}" +
                                    "パッシブ効果: 補助足場生成"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.GRAY}" +
                                    "スニーク:下降 ジャンプ:上昇"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.AQUA}" +
                                    "消費: ${Config.SPELL_SKY_WALK_MANA_PER_BLOCK.toBigDecimal().setScale(1, RoundingMode.HALF_UP)} マナ/ブロック"
                    )
            )


}