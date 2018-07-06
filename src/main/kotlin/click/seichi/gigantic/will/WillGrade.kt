package click.seichi.gigantic.will

/**
 * @author unicroak
 */
enum class WillGrade(val id: Int) {

    BASIC(0),

    ADVANCED(1);

    override fun toString() = name.toLowerCase()

}