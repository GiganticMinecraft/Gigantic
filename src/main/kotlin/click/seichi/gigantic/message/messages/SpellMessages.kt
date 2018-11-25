package click.seichi.gigantic.message.messages

import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.player.spell.SpellParameters
import org.bukkit.ChatColor
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
                                    "発動確率: ${SpellParameters.STELLA_CLAIR_PROBABILITY_PERCENT} %"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.AQUA}" +
                                    "回復量: 最大マナの${SpellParameters.STELLA_CLAIR_AMOUNT_PERCENT} %"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                                    "全ての通常破壊で発動"
                    )
            )

    val EXPLOSION = LocalizedText(
            Locale.JAPANESE to "エクスプロージョン"
    )

    val AQUA_LINEA = LocalizedText(
            Locale.JAPANESE to "アクア・リネア"
    )

    val IGNIS_VOLCANO = LocalizedText(
            Locale.JAPANESE to "イグニス・ヴォルケーノ"
    )

    val AER_SLASH = LocalizedText(
            Locale.JAPANESE to "エアル・スラッシュ"
    )

    val TERRA_DRAIN = LocalizedText(
            Locale.JAPANESE to "テラ・ドレイン"
    )

    val GRAND_NATURA = LocalizedText(
            Locale.JAPANESE to "グランド・ナトラ"
    )

}