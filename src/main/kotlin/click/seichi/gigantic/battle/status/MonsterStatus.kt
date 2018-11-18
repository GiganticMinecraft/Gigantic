package click.seichi.gigantic.battle.status

import click.seichi.gigantic.battle.EffectPriority

/**
 * @author tar0ss
 */
enum class MonsterStatus {


    ;

    open val priority: EffectPriority = EffectPriority.NORMAL

    open fun modifyPower(source: Long): Long {
        return source
    }

}