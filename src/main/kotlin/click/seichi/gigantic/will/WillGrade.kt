package click.seichi.gigantic.will

/**
 * @author unicroak
 * @author tar0ss
 */
enum class WillGrade(val unlockLevel: Int) {
    BASIC(20),
    ADVANCED(70),
    ;

    override fun toString() = name.toLowerCase()

}