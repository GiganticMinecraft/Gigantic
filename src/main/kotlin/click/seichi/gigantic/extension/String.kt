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