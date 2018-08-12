package click.seichi.gigantic.skill

/**
 * スキルの効果が一定時間続く
 *
 * @author tar0ss
 */
interface LingeringSkill : Skill {
    // 持続時間
    val duration: Long
}