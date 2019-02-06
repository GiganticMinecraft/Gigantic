package click.seichi.gigantic.message.messages

import click.seichi.gigantic.config.Config
import click.seichi.gigantic.message.LocalizedText
import org.bukkit.ChatColor
import java.math.RoundingMode
import java.util.*

/**
 * @author tar0ss
 */
object SkillMessages {

    val FLASH = LocalizedText(
            Locale.JAPANESE to "フラッシュ"
    )

    val HEAL = LocalizedText(
            Locale.JAPANESE to "ヒール"
    )

    val MINE_BURST = LocalizedText(
            Locale.JAPANESE to "マイン・バースト"
    )

    val MINE_BURST_LORE =
            listOf(
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.GRAY}" +
                                    "アクティブ効果: 少しの間だけ掘る速度が上昇"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.AQUA}" +
                                    "持続時間: ${Config.SKILL_MINE_BURST_DURATION}秒"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.AQUA}" +
                                    "クールタイム: ${Config.SKILL_MINE_BURST_COOLTIME}秒"
                    )
            )

    val FLASH_LORE =
            listOf(
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.GRAY}" +
                                    "アクティブ効果: 視点方向にワープ"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.AQUA}" +
                                    "クールタイム: ${Config.SKILL_FLASH_COOLTIME}秒"
                    )
            )

    val HEAL_LORE =
            listOf(
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.GRAY}" +
                                    "パッシブ効果: ブロックを破壊して体力を回復"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.AQUA}" +
                                    "発動確率: ${Config.SKILL_HEAL_PROBABILITY.toBigDecimal().setScale(1, RoundingMode.HALF_UP)} %"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.AQUA}" +
                                    "回復量: 最大体力の${Config.SKILL_HEAL_RATIO.toBigDecimal().setScale(1, RoundingMode.HALF_UP)} %"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                                    "全ての通常破壊で発動"
                    )
            )

    val MINE_COMBO = LocalizedText(
            Locale.JAPANESE to "マイン・コンボ"
    )

    val MINE_COMBO_LORE =
            listOf(
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.GRAY}" +
                                    "パッシブ効果: 掘り続けると採掘速度が徐々に上昇"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.AQUA}" +
                                    "コンボ継続時間: ${Config.SKILL_MINE_COMBO_CONTINUATION_SECONDS.toBigDecimal().setScale(1, RoundingMode.HALF_UP)}秒"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.AQUA}" +
                                    "コンボが途切れると${Config.SKILL_MINE_COMBO_DECREASE_INTERVAL.toBigDecimal().setScale(1, RoundingMode.HALF_UP)}秒おきに" +
                                    "最大で100Combo減少する"
                    )
            )

    val JUMP = LocalizedText(
            Locale.JAPANESE to "ジャンプ"
    )

    val JUMP_LORE =
            listOf(
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.GRAY}" +
                                    "アクティブ効果: スニークしながら" +
                                    "${ChatColor.LIGHT_PURPLE}" +
                                    "${Config.ELYTRA_CHARGE_UP_TIME.div(20)}秒" +
                                    "${ChatColor.GRAY}" +
                                    "チャージ後"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.GRAY}" +
                                    "スニークを離すと飛び立てる!!"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.RED}" +
                                    ""
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.GRAY}" +
                                    "落下中にスペースを押すと滑空できます"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.RED}" +
                                    "落下死" +
                                    "${ChatColor.GRAY}" +
                                    "に注意してください．"
                    )
            )
}