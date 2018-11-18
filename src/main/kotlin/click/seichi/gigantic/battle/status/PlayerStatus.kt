package click.seichi.gigantic.battle.status

import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
enum class PlayerStatus {

    ;

    open val priority: EffectPriority = EffectPriority.NORMAL

    open fun modifyPower(source: Long): Long {
        return source
    }

    open fun effectCondition(player: Player) = false

}