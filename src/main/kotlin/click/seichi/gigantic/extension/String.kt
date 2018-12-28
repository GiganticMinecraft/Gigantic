package click.seichi.gigantic.extension

import click.seichi.gigantic.util.Random
import org.bukkit.ChatColor

/**
 * @author unicroak
 * @author tar0ss
 */

internal val String.Companion.NOT_AVAILABLE
    get() = "N/A"

fun String.beginWithUpperCase(): String {
    return when (this.length) {
        0 -> ""
        1 -> this.toUpperCase()
        else -> this[0].toUpperCase() + this.substring(1)
    }
}

fun String.toRainbow(isBold: Boolean = false) = this.toCharArray().map {
    "${ChatColor.RESET}${Random.nextChatColor()}${if (isBold) "${ChatColor.BOLD}" else ""}$it"
}.joinToString(separator = "")


fun String.Companion.enchantLevel(level: Int) =
        when (level) {
            1 -> "Ⅰ"
            2 -> "Ⅱ"
            3 -> "Ⅲ"
            4 -> "Ⅳ"
            5 -> "Ⅴ"
            6 -> "Ⅵ"
            7 -> "Ⅶ"
            8 -> "Ⅷ"
            9 -> "Ⅸ"
            10 -> "Ⅹ"
            else -> "???"
        }
