package click.seichi.gigantic.message

import click.seichi.gigantic.Gigantic
import java.util.*

/**
 * @author unicroak
 */
class LocalizedText(vararg texts: Pair<Locale, String>) {

    companion object {
        const val NOT_AVAILABLE = "The text is not available"
    }

    private val textMap = texts.toMap()

    fun `as`(locale: Locale) = textMap[locale]

    fun asSafety(locale: Locale) = textMap[locale] ?: textMap[Gigantic.DEFAULT_LOCALE] ?: NOT_AVAILABLE

}