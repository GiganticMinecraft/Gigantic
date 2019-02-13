package click.seichi.gigantic.message

import click.seichi.gigantic.Gigantic
import java.util.*

/**
 * @author unicroak
 */
class LocalizedText(vararg texts: Pair<Locale, String>) {

    private var prefix = ""
    private var suffix = ""

    companion object {
        const val NOT_AVAILABLE = "The text is not available"
    }

    private val textMap = texts.toMap().toMutableMap()

    fun asSafety(locale: Locale): String {
        return decorated(locale) ?: decorated(Gigantic.DEFAULT_LOCALE) ?: NOT_AVAILABLE
    }

    private fun decorated(locale: Locale): String? = textMap[locale]?.let { "$prefix$it$suffix" }

    // syntactic sugar
    fun withPrefix(prefix: String) = also { it.prefix = prefix }

    fun withSuffix(suffix: String) = also { it.suffix = suffix }

}