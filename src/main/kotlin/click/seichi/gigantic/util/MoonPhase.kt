package click.seichi.gigantic.util

import click.seichi.gigantic.message.LocalizedText
import java.util.*

/**
 * @author tar0ss
 */
enum class MoonPhase(
        val id: Int,
        private val localizedName: LocalizedText
) {
    MANGETSU(1, LocalizedText(
            Locale.JAPANESE to "満月"
    )),
    IMACHIZUKI(2, LocalizedText(
            Locale.JAPANESE to "居待月"
    )),
    KAGEN(3, LocalizedText(
            Locale.JAPANESE to "下弦"
    )),
    NIZYUROKUYA(4, LocalizedText(
            Locale.JAPANESE to "二十六夜"
    )),
    SHINGETSU(5, LocalizedText(
            Locale.JAPANESE to "新月"
    )),
    MIKAZUKI(6, LocalizedText(
            Locale.JAPANESE to "三日月"
    )),
    ZYOGEN(7, LocalizedText(
            Locale.JAPANESE to "上弦"
    )),
    ZYUUSANYA(8, LocalizedText(
            Locale.JAPANESE to "十三夜"
    )),
    ;

    fun getName(locale: Locale) = localizedName.asSafety(locale)

}