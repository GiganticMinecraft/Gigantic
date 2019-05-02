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

    companion object {
        fun getByNumber(i: Int): SideBarRow {
            return when (i) {
                1 -> ONE
                2 -> TWO
                3 -> THREE
                4 -> FOUR
                5 -> FIVE
                6 -> SIX
                7 -> SEVEN
                8 -> EIGHT
                9 -> NINE
                10 -> TEN
                11 -> ELEVEN
                12 -> TWELVE
                13 -> THIRTEEN
                14 -> FOURTEEN
                else -> error("number needs to less than 14")
            }
        }
    }
}