package click.seichi.gigantic.timer

/**
 * @author tar0ss
 */
interface Timer {

    fun start()

    fun canStart(): Boolean

    fun duringCoolTime(): Boolean

}