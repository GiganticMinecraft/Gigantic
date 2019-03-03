package click.seichi.gigantic.util

import click.seichi.gigantic.message.LocalizedText
import org.bukkit.ChatColor
import org.bukkit.block.Block
import java.util.*

/**
 * @author tar0ss
 */
enum class TemperatureGrade(
        private val localizedName: LocalizedText,
        val chatColor: ChatColor
) {
    VERY_COLD(
            LocalizedText(
                    Locale.JAPANESE to "とても寒い"
            ),
            ChatColor.AQUA
    ) {
        override fun isBelongTo(temperature: Double): Boolean {
            return temperature <= -0.5
        }
    },
    COLD(
            LocalizedText(
                    Locale.JAPANESE to "寒い"
            ),
            ChatColor.DARK_AQUA
    ) {
        override fun isBelongTo(temperature: Double): Boolean {
            return temperature > -0.5 && 0.15 >= temperature
        }
    },
    NORMAL(
            LocalizedText(
                    Locale.JAPANESE to "普通"
            ),
            ChatColor.GREEN
    ) {
        override fun isBelongTo(temperature: Double): Boolean {
            return temperature > -0.15 && 1.0 >= temperature
        }
    },
    HOT(
            LocalizedText(
                    Locale.JAPANESE to "暑い"
            ),
            ChatColor.GOLD
    ) {
        override fun isBelongTo(temperature: Double): Boolean {
            return temperature > 1.0 && 1.5 >= temperature
        }
    },
    VERY_HOT(
            LocalizedText(
                    Locale.JAPANESE to "とても暑い"
            ),
            ChatColor.RED
    ) {
        override fun isBelongTo(temperature: Double): Boolean {
            return temperature > 1.5
        }
    },
    ;

    fun getName(locale: Locale) = localizedName.asSafety(locale)

    abstract fun isBelongTo(temperature: Double): Boolean

    companion object {
        fun getByBlock(block: Block): TemperatureGrade {
            return values().first { it.isBelongTo(block.temperature) }
        }
    }
}