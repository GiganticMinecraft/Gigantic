package click.seichi.gigantic.battle.passive

import click.seichi.gigantic.battle.BattlePlayer
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.find
import click.seichi.gigantic.message.messages.EffectMessages
import click.seichi.gigantic.relic.Relic
import org.bukkit.block.Block

/**
 * @author tar0ss
 */
enum class PowerEffect {
    CHICKEN_KING_CROWN() {
        override fun modifyPower(source: Long, battler: BattlePlayer, block: Block): Long {
            val mineCombo = battler.player.find(CatalogPlayerCache.MINE_COMBO) ?: return source
            if (mineCombo.currentCombo == 10L) {
                EffectMessages.CHICKEN_KING_CROWN.sendTo(battler.player)
            }
            return if (mineCombo.currentCombo >= 10L)
                source + 1
            else
                source
        }

        override fun effectCondition(battler: BattlePlayer, block: Block): Boolean {
            return Relic.CHICKEN_KING_CROWN.has(battler.player)
        }
    }
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