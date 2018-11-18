package click.seichi.gigantic.battle.passive

import click.seichi.gigantic.battle.BattlePlayer
import org.bukkit.block.Block

/**
 * @author tar0ss
 */
enum class PowerEffect {

    ;

    companion object {
        fun calcDamage(battler: BattlePlayer, block: Block): Long {
            return values().fold(1L) { source: Long, effector ->
                if (effector.effectCondition(battler, block))
                    effector.modifyPower(source, battler, block)
                else source
            }
        }
    }

    abstract fun modifyPower(source: Long, battler: BattlePlayer, block: Block): Long

    abstract fun effectCondition(battler: BattlePlayer, block: Block): Boolean

}