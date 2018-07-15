package click.seichi.gigantic.util

/**
 * @author tar0ss
 * @author unicroak
 */
enum class SideBarRow(val toInt: Int) {
    ONE(14),
    TWO(13),
    THREE(12),
    FOUR(11),
    FIVE(10),
    SIX(9),
    SEVEN(8),
    EIGHT(7),
    NINE(6),
    TEN(5),
    ELEVEN(4),
    TWELVE(3),
    THIRTEEN(2),
    FOURTEEN(1)
    ;

    val id = 14 - toInt + 1

    companion object {
        fun getRowById(id: Int) = values().first { it.id == id }
    }
}