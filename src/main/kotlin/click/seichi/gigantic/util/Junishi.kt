package click.seichi.gigantic.util

import click.seichi.gigantic.message.LocalizedText
import java.util.*

/**
 * @author tar0ss
 */
enum class Junishi(
        val id: Int,
        private val localizedName: LocalizedText
) {
    NE(1, LocalizedText(
            Locale.JAPANESE to "子"
    )),
    USHI(2, LocalizedText(
            Locale.JAPANESE to "丑"
    )),
    TORA(3, LocalizedText(
            Locale.JAPANESE to "寅"
    )),
    U(4, LocalizedText(
            Locale.JAPANESE to "卯"
    )),
    TATSU(5, LocalizedText(
            Locale.JAPANESE to "辰"
    )),
    MI(6, LocalizedText(
            Locale.JAPANESE to "巳"
    )),
    UMA(7, LocalizedText(
            Locale.JAPANESE to "午"
    )),
    HITSUJI(8, LocalizedText(
            Locale.JAPANESE to "未"
    )),
    SARU(9, LocalizedText(
            Locale.JAPANESE to "申"
    )),
    TORI(10, LocalizedText(
            Locale.JAPANESE to "酉"
    )),
    INU(11, LocalizedText(
            Locale.JAPANESE to "戌"
    )),
    I(12, LocalizedText(
            Locale.JAPANESE to "亥"
    )),
    ;

    fun getName(locale: Locale) = localizedName.asSafety(locale)

}