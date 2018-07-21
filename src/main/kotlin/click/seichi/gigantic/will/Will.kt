package click.seichi.gigantic.will

import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.messages.WillMessages
import org.bukkit.Color

/**
 * @author unicroak
 * @author tar0ss
 */
enum class Will(val id: Int, val color: Color, val grade: WillGrade, val localizedName: LocalizedText) {

    AER(1, Color.fromRGB(240, 248, 255), WillGrade.BASIC, WillMessages.AER),
    AQUA(2, Color.fromRGB(0, 0, 128), WillGrade.BASIC, WillMessages.AQUA),
    IGNIS(3, Color.fromRGB(255, 69, 0), WillGrade.BASIC, WillMessages.IGNIS),
    NATURA(4, Color.fromRGB(0, 255, 0), WillGrade.BASIC, WillMessages.NATURA),
    TERRA(5, Color.fromRGB(124, 83, 53), WillGrade.BASIC, WillMessages.TERRA),
    GLACIES(6, Color.fromRGB(127, 255, 255), WillGrade.ADVANCED, WillMessages.GLACIES),
    LUX(7, Color.fromRGB(255, 255, 77), WillGrade.ADVANCED, WillMessages.LUX),
    SOLUM(8, Color.fromRGB(105, 105, 105), WillGrade.ADVANCED, WillMessages.SOLUM),
    UMBRA(9, Color.fromRGB(148, 0, 211), WillGrade.ADVANCED, WillMessages.UMBRA),
    VENTUS(10, Color.fromRGB(123, 104, 238), WillGrade.ADVANCED, WillMessages.VENTUS)
    ;

    override fun toString(): String = name.toLowerCase()

    companion object {

        private val typeMap = values().map { it.id to it }.toMap()

        fun findWillById(id: Int) = typeMap[id]
    }
}