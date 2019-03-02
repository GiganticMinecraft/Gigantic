package click.seichi.gigantic.will

/**
 * @author unicroak
 * @author tar0ss
 */
enum class WillGrade(val unlockLevel: Int, val unlockAmount: Long) {
    BASIC(20, 1000L),
    ADVANCED(70, 4000L),
    SPECIAL(20, 2000L),
    ;

    override fun toString() = name.toLowerCase()

}