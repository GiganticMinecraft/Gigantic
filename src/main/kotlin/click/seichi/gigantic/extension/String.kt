package click.seichi.gigantic.extension

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
