package click.seichi.gigantic.message.messages.menu

import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.player.skill.SkillParameters
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object SkillMenuMessages {

    val TITLE = LocalizedText(
            Locale.JAPANESE to "スキル"
    )

    val MINE_BURST =
            listOf(
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.GRAY}" +
                                    "アクティブ効果: 少しの間だけ掘る速度が上昇"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.AQUA}" +
                                    "持続時間: ${SkillParameters.MINE_BURST_DURATION}秒"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.AQUA}" +
                                    "クールタイム: ${SkillParameters.MINE_BURST_COOLTIME}秒"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                                    "\"${SkillParameters.MINE_BURST_KEY}\" キー を押して発動"
                    )
            )

    val MINE_BURST_TITLE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.YELLOW}" +
                    "バフスキル: マインバースト"
    )

    val FLASH =
            listOf(
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.GRAY}" +
                                    "アクティブ効果: 視点方向にワープ"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.AQUA}" +
                                    "クールタイム: ${SkillParameters.FLASH_COOLTIME}秒"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                                    "\"${SkillParameters.FLASH_KEY}\" キー を押して発動"
                    )
            )

    val FLASH_TITLE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.YELLOW}" +
                    "移動スキル: フラッシュ"
    )

    val HEAL =
            listOf(
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.GRAY}" +
                                    "パッシブ効果: ブロックを破壊して体力を回復"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.AQUA}" +
                                    "発動確率: ${SkillParameters.HEAL_PROBABILITY_PERCENT} %"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.AQUA}" +
                                    "回復量: 最大体力の${SkillParameters.HEAL_AMOUNT_PERCENT} %"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                                    "全ての通常破壊で発動"
                    )
            )

    val HEAL_TITLE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.YELLOW}" +
                    "回復スキル: ヒール"
    )
}