package click.seichi.gigantic.will

import click.seichi.gigantic.extension.isOcean
import click.seichi.gigantic.extension.isRiver
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.messages.WillMessages
import org.bukkit.Color
import org.bukkit.block.Block
import org.bukkit.entity.Player

/**
 * @author unicroak
 * @author tar0ss
 */
enum class Will(
        val id: Int,
        val color: Color,
        val grade: WillGrade,
        val localizedName: LocalizedText
) {

    AQUA(1, Color.fromRGB(0, 0, 128), WillGrade.BASIC, WillMessages.AQUA) {
        // 海，川等のバイオームであること
        override fun canSpawn(player: Player, block: Block): Boolean {
            val biome = block.biome ?: return false
            return biome.isOcean || biome.isRiver
        }
    },
    IGNIS(2, Color.fromRGB(255, 69, 0), WillGrade.BASIC, WillMessages.IGNIS) {
        // 高度が40以下であり，かつ海，川等のバイオームではないこと
        override fun canSpawn(player: Player, block: Block): Boolean {
            val biome = block.biome ?: return false
            if (biome.isOcean || biome.isRiver) return false
            return block.y <= 20
        }
    },
    AER(3, Color.fromRGB(240, 248, 255), WillGrade.BASIC, WillMessages.AER) {
        // 高度が65以上であること，かつ海，川等のバイオームではないこと
        override fun canSpawn(player: Player, block: Block): Boolean {
            val biome = block.biome ?: return false
            if (biome.isOcean || biome.isRiver) return false
            return block.y >= 65
        }
    },
    TERRA(4, Color.fromRGB(124, 83, 53), WillGrade.BASIC, WillMessages.TERRA) {
        // 土系のブロックであること
        override fun canSpawn(player: Player, block: Block): Boolean {
            return false
        }
    },
    NATURA(5, Color.fromRGB(0, 255, 0), WillGrade.BASIC, WillMessages.NATURA) {
        override fun canSpawn(player: Player, block: Block): Boolean {
            return false
        }
    },
    GLACIES(6, Color.fromRGB(127, 255, 255), WillGrade.ADVANCED, WillMessages.GLACIES) {
        override fun canSpawn(player: Player, block: Block): Boolean {
            return false
        }
    },
    LUX(7, Color.fromRGB(255, 255, 77), WillGrade.ADVANCED, WillMessages.LUX) {
        override fun canSpawn(player: Player, block: Block): Boolean {
            return false
        }
    },
    SOLUM(8, Color.fromRGB(105, 105, 105), WillGrade.ADVANCED, WillMessages.SOLUM) {
        override fun canSpawn(player: Player, block: Block): Boolean {
            return false
        }
    },
    UMBRA(9, Color.fromRGB(148, 0, 211), WillGrade.ADVANCED, WillMessages.UMBRA) {
        override fun canSpawn(player: Player, block: Block): Boolean {
            return false
        }
    },
    VENTUS(10, Color.fromRGB(123, 104, 238), WillGrade.ADVANCED, WillMessages.VENTUS) {
        override fun canSpawn(player: Player, block: Block): Boolean {
            return false
        }
    }
    ;

    abstract fun canSpawn(player: Player, block: Block): Boolean

    override fun toString(): String = name.toLowerCase()

    companion object {
        private val idMap = values().map { it.id to it }.toMap()

        fun findById(id: Int) = idMap[id]
    }
}