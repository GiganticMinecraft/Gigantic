package click.seichi.gigantic.message.messages

import click.seichi.gigantic.message.LocalizedText
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object ArmorMessages {

    private val PREFIX = "${ChatColor.AQUA}${ChatColor.ITALIC}"

    val HELMET = LocalizedText(
            Locale.JAPANESE to "不思議なヘルメット"
    ).withPrefix(PREFIX)

    val HELMET_LORE = listOf(
            LocalizedText(
                    Locale.JAPANESE to "どんなに使用しても壊れない"
            ).withPrefix(PREFIX)
    )

    val ELYTRA = LocalizedText(
            Locale.JAPANESE to "不思議なエリトラ"
    ).withPrefix(PREFIX)

    val ELYTRA_LORE = listOf(
            LocalizedText(
                    Locale.JAPANESE to "どんなに使用しても壊れない"
            ).withPrefix(PREFIX)
    )

    val LEGGINGS = LocalizedText(
            Locale.JAPANESE to "不思議なレギンス"
    ).withPrefix(PREFIX)

    val LEGGINGS_LORE = listOf(
            LocalizedText(
                    Locale.JAPANESE to "どんなに使用しても壊れない"
            ).withPrefix(PREFIX)
    )

    val BOOTS = LocalizedText(
            Locale.JAPANESE to "不思議なブーツ"
    ).withPrefix(PREFIX)

    val BOOTS_LORE = listOf(
            LocalizedText(
                    Locale.JAPANESE to "どんなに使用しても壊れない"
            ).withPrefix(PREFIX)
    )

}