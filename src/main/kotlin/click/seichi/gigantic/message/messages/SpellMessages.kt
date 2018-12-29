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
                                    "回復量: 最大マナの${Config.SPELL_STELLA_CLAIR_RATIO.toBigDecimal().setScale(1, RoundingMode.HALF_UP)} %"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                                    "全ての通常破壊で発動"
                    )
            )

    val APOSTOL = LocalizedText(
            Locale.JAPANESE to "アポストル"
    )

    val APOSTOL_LORE =
            listOf(
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.GRAY}" +
                                    "アクティブ効果: ブロック破壊時,周囲も破壊"
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

}