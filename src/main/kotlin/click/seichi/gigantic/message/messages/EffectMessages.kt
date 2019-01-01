package click.seichi.gigantic.message.messages

import click.seichi.gigantic.extension.toRainbow
import click.seichi.gigantic.message.LocalizedText
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object EffectMessages {

    val SLIME = LocalizedText(
            Locale.JAPANESE to "${ChatColor.GREEN}" +
                    "${ChatColor.BOLD}" +
                    "スライム"
    )

    val SLIME_LORE = setOf(
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.WHITE}" +
                            "転生したらスライムだった件"
            )
    )

    val WITCH_SCENT = LocalizedText(
            Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                    "${ChatColor.BOLD}" +
                    "魔女の残り香"
    )

    val WITCH_SCENT_LORE = setOf(
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.WHITE}" +
                            "心安らぐ香り"
            )
    )

    val FLAME = LocalizedText(
            Locale.JAPANESE to "${ChatColor.RED}" +
                    "${ChatColor.BOLD}" +
                    "フレイム"
    )

    val FLAME_LORE = setOf(
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.WHITE}" +
                            "ぐつぐつと燃える"
            )
    )


    val MAGIC = LocalizedText(
            Locale.JAPANESE to "マジック".toRainbow(true)
    )

    val MAGIC_LORE = setOf(
            LocalizedText(
                    Locale.JAPANESE to "カラフルな羊毛に変身する".toRainbow()
            )
    )

    val BLIZZARD = LocalizedText(
            Locale.JAPANESE to "${ChatColor.AQUA}" +
                    "${ChatColor.BOLD}" +
                    "ブリザード"
    )

    val BLIZZARD_LORE = setOf(
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.WHITE}" +
                            "凍結する"
            )
    )

    val EXPLOSION = LocalizedText(
            Locale.JAPANESE to "${ChatColor.RED}" +
                    "${ChatColor.BOLD}" +
                    "エクスプロージョン"
    )

    val EXPLOSION_LORE = setOf(
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.WHITE}" +
                            "爆発する"
            )
    )

    val DEFAULT = LocalizedText(
            Locale.JAPANESE to "${ChatColor.WHITE}" +
                    "${ChatColor.BOLD}" +
                    "通常エフェクト"
    )

    val DEFAULT_LORE = setOf(
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.WHITE}" +
                            "マイクラの通常エフェクト"
            )
    )

}