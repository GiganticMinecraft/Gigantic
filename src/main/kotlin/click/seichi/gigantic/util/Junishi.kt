package click.seichi.gigantic.util

import click.seichi.gigantic.message.LocalizedText
import org.bukkit.World
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

    companion object {
        /**
         * [world]の時刻を十二支で取得します
         */
        fun now(world: World): Junishi {
            return when (world.time) {
                in 17001..19000 -> NE
                in 19001..21000 -> USHI
                in 21001..23000 -> TORA
                in 23001..24000 -> U
                in 1..1000 -> U
                in 1001..3000 -> TATSU
                in 3001..5000 -> MI
                in 5001..7000 -> UMA
                in 7001..9000 -> HITSUJI
                in 9001..11000 -> SARU
                in 11001..13000 -> TORI
                in 13001..15000 -> INU
                in 15001..17000 -> I
                else -> NE
            }
        }
    }

    fun getName(locale: Locale) = localizedName.asSafety(locale)

}