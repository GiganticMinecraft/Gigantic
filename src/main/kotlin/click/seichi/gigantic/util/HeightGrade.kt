package click.seichi.gigantic.util

import click.seichi.gigantic.message.LocalizedText
import org.bukkit.ChatColor
import org.bukkit.block.Block
import java.util.*

/**
 * @author tar0ss
 */
enum class HeightGrade(
        private val localizedName: LocalizedText,
        val chatColor: ChatColor
) {
    VELY_LOW(
            LocalizedText(
                    Locale.JAPANESE to "とても低い"
            ),
            ChatColor.DARK_GRAY
    ) {
        override fun isBelongTo(height: Int): Boolean {
            return height <= 29
        }
    },
    LOW(
            LocalizedText(
                    Locale.JAPANESE to "低い"
            ),
            ChatColor.GRAY
    ) {
        override fun isBelongTo(height: Int): Boolean {
            return height in 30..62
        }
    },
    NORMAL(
            LocalizedText(
                    Locale.JAPANESE to "普通"
            ),
            ChatColor.GOLD
    ) {
        override fun isBelongTo(height: Int): Boolean {
            return height in 63..84
        }
    },
    HIGH(
            LocalizedText(
                    Locale.JAPANESE to "高い"
            ),
            ChatColor.RED
    ) {
        override fun isBelongTo(height: Int): Boolean {
            return height >= 85
        }
    },

    ;

    fun getName(locale: Locale) = localizedName.asSafety(locale)

    abstract fun isBelongTo(height: Int): Boolean

    companion object {
        fun getByBlock(block: Block): HeightGrade {
            return values().first { it.isBelongTo(block.y) }
        }
    }

}