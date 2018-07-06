package click.seichi.gigantic.will

import click.seichi.gigantic.message.LocalizedString
import click.seichi.gigantic.message.lang.WillLang
import org.bukkit.Color

/**
 * @author unicroak
 * @author tar0ss
 */
enum class Will(val id: Int, val color: Color, val grade: WillGrade, val localizedName: LocalizedString) {

    AER(1, Color.fromRGB(240, 248, 255), WillGrade.BASIC, WillLang.AER),
    AQUA(2, Color.fromRGB(0, 0, 128), WillGrade.BASIC, WillLang.AQUA),
    IGNIS(3, Color.fromRGB(255, 69, 0), WillGrade.BASIC, WillLang.IGNIS),
    NATURA(4, Color.fromRGB(0, 255, 0), WillGrade.BASIC, WillLang.NATURA),
    TERRA(5, Color.fromRGB(139, 69, 19), WillGrade.BASIC, WillLang.TERRA),
    GLACIES(6, Color.fromRGB(127, 255, 255), WillGrade.ADVANCED, WillLang.GLACIES),
    LUX(7, Color.fromRGB(255, 255, 77), WillGrade.ADVANCED, WillLang.LUX),
    SOLUM(8, Color.fromRGB(105, 105, 105), WillGrade.ADVANCED, WillLang.SOLUM),
    UMBRA(9, Color.fromRGB(148, 0, 211), WillGrade.ADVANCED, WillLang.UMBRA),
    VENTUS(10, Color.fromRGB(123, 104, 238), WillGrade.ADVANCED, WillLang.VENTUS)
    ;

    override fun toString(): String = name.toLowerCase()


    companion object {
        private val idMap = values().map { it.id to it }.toMap()

        fun getWillTypeById(id: Int) = idMap[id]
    }

}