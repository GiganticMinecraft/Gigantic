package click.seichi.gigantic.will

import click.seichi.gigantic.extension.*
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.messages.WillMessages
import org.bukkit.ChatColor
import org.bukkit.Color
import org.bukkit.block.Block
import org.bukkit.entity.Player
import java.util.*

/**
 * @author unicroak
 * @author tar0ss
 */
enum class Will(
        val id: Int,
        val color: Color,
        val chatColor: ChatColor,
        val grade: WillGrade,
        private val localizedName: LocalizedText
) {

    /**
     * スポーン条件は以下のいずれかによって分けること
     * * バイオーム
     * * 気温
     * * 高度
     *
     */
    AQUA(1, Color.fromRGB(0, 0, 128), ChatColor.BLUE, WillGrade.BASIC, WillMessages.AQUA) {
        // 高度が30以上62以下であり，かつ海，川等のバイオームであること
        override fun canSpawn(player: Player, block: Block): Boolean {
            val biome = block.biome ?: return false
            if (!biome.isOcean && !biome.isRiver) return false
            return block.y in 30..62
        }
    },
    IGNIS(2, Color.fromRGB(255, 69, 0), ChatColor.RED, WillGrade.BASIC, WillMessages.IGNIS) {
        // 高度が29以下であること
        override fun canSpawn(player: Player, block: Block): Boolean {
            return block.y <= 29
        }
    },
    AER(3, Color.fromRGB(240, 248, 255), ChatColor.WHITE, WillGrade.BASIC, WillMessages.AER) {
        // 高度が85以上であること
        override fun canSpawn(player: Player, block: Block): Boolean {
            return block.y >= 85
        }
    },
    TERRA(4, Color.fromRGB(124, 83, 53), ChatColor.GOLD, WillGrade.BASIC, WillMessages.TERRA) {
        // 高度が30以上62以下であり，かつ海，川等のバイオームではないこと
        override fun canSpawn(player: Player, block: Block): Boolean {
            val biome = block.biome ?: return false
            if (biome.isOcean || biome.isRiver) return false
            return block.y in 30..62
        }
    },
    NATURA(5, Color.fromRGB(0, 255, 0), ChatColor.DARK_GREEN, WillGrade.BASIC, WillMessages.NATURA) {
        // 高度が63以上84以下であること
        override fun canSpawn(player: Player, block: Block): Boolean {
            return block.y in 63..84
        }
    },
    GLACIES(6, Color.fromRGB(127, 255, 255), ChatColor.AQUA, WillGrade.ADVANCED, WillMessages.GLACIES) {
        // 温度が0以下であること
        override fun canSpawn(player: Player, block: Block): Boolean {
            return block.temperature <= 0
        }
    },
    LUX(7, Color.fromRGB(255, 255, 77), ChatColor.YELLOW, WillGrade.ADVANCED, WillMessages.LUX) {
        // 温度が1.2以上であること
        override fun canSpawn(player: Player, block: Block): Boolean {
            return block.temperature >= 1.2
        }
    },
    SOLUM(8, Color.fromRGB(105, 105, 105), ChatColor.GRAY, WillGrade.ADVANCED, WillMessages.SOLUM) {
        // 山岳バイオームであること
        override fun canSpawn(player: Player, block: Block): Boolean {
            val biome = block.biome ?: return false
            return biome.isMountain
        }
    },
    UMBRA(9, Color.fromRGB(148, 0, 211), ChatColor.DARK_PURPLE, WillGrade.ADVANCED, WillMessages.UMBRA) {
        // 森林バイオームであること
        override fun canSpawn(player: Player, block: Block): Boolean {
            val biome = block.biome ?: return false
            return biome.isForest
        }
    },
    VENTUS(10, Color.fromRGB(123, 104, 238), ChatColor.LIGHT_PURPLE, WillGrade.ADVANCED, WillMessages.VENTUS) {
        // 丘陵
        override fun canSpawn(player: Player, block: Block): Boolean {
            val biome = block.biome ?: return false
            return biome.isHill
        }
    }
    ;

    abstract fun canSpawn(player: Player, block: Block): Boolean

    override fun toString(): String = name.toLowerCase()

    fun getName(locale: Locale) = localizedName.asSafety(locale)

    companion object {
        private val idMap = values().map { it.id to it }.toMap()

        fun findById(id: Int) = idMap[id]
    }
}