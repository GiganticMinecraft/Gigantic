package click.seichi.gigantic.skill.timer

/**
 * @author tar0ss
 */
interface SkillTimer {

    fun start()

    fun canStart(): Boolean

    fun duringCoolTime(): Boolean

}