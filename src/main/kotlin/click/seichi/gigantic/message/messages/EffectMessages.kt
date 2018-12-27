package click.seichi.gigantic.message.messages

import click.seichi.gigantic.message.LocalizedText
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object EffectMessages {

    val EXPLOSION = LocalizedText(
            Locale.JAPANESE to "${ChatColor.RED}" +
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
                    "通常エフェクト"
    )

    val DEFAULT_LORE = setOf(
            LocalizedText(
                    Locale.JAPANESE to "${ChatColor.WHITE}" +
                            "マイクラの通常エフェクト"
            )
    )

}