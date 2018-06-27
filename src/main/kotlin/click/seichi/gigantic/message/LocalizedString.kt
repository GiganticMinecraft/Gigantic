package click.seichi.gigantic.message

import click.seichi.gigantic.extension.NOT_AVAILABLE
import java.util.*

/**
 * @author unicroak
 */
class LocalizedString(vararg pairs: Pair<Locale, String>) {

    private val stringMap: Map<Locale, String> = mapOf(*pairs)

    fun `as`(locale: Locale): String? = stringMap[locale]

    fun asSafety(locale: Locale): String = `as`(locale) ?: `as`(Locale.ENGLISH) ?: String.NOT_AVAILABLE

}