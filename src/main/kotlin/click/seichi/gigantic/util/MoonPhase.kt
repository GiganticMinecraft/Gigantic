package click.seichi.gigantic.util

import click.seichi.gigantic.message.LocalizedText
import org.bukkit.World
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

    companion object {

        // 月齢が1週する間隔
        private const val INTERVAL = 24000L.times(8L)

        // 月が見える時間
        private const val MOON_RISE_TIME = 12567L

        // 月が沈む
        private const val MOON_SET_TIME = 22917L

        fun now(world: World): MoonPhase {
            return when ((world.fullTime % INTERVAL).div(24000).toInt()) {
                0 -> MANGETSU
                1 -> IMACHIZUKI
                2 -> KAGEN
                3 -> NIZYUROKUYA
                4 -> SHINGETSU
                5 -> MIKAZUKI
                6 -> ZYOGEN
                7 -> ZYUUSANYA
                else -> MANGETSU
            }
        }

        // 月が昇っているか
        fun isRisingMoon(world: World): Boolean {
            return world.time in MOON_RISE_TIME..MOON_SET_TIME
        }
    }
}