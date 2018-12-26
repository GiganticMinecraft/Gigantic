package click.seichi.gigantic.battle.passive

import click.seichi.gigantic.battle.BattlePlayer
import click.seichi.gigantic.extension.combo
import click.seichi.gigantic.message.messages.EffectMessages
import click.seichi.gigantic.relic.Relic
import org.bukkit.block.Block

/**
 * @author tar0ss
 */
enum class PowerEffect {
    CHICKEN_KING_CROWN() {
        override fun modifyPower(source: Long, battler: BattlePlayer, block: Block): Long {
            val requireCombo = 10L

            val combo = battler.player.combo
            if (combo == requireCombo) {
                EffectMessages.COMBO.sendTo(battler.player)
            }
            return if (combo >= requireCombo)
                source + 1
            else
                source
        }

        override fun effectCondition(battler: BattlePlayer, block: Block): Boolean {
            return Relic.CHICKEN_KING_CROWN.has(battler.player)
        }
    },
    TURTLE_KING_CROWN() {
        override fun modifyPower(source: Long, battler: BattlePlayer, block: Block): Long {
            val requireCombo = 30L

            val combo = battler.player.combo
            if (combo == requireCombo) {
                EffectMessages.COMBO.sendTo(battler.player)
            }
            return if (combo >= requireCombo)
                source + 1
            else
                source
        }

        override fun effectCondition(battler: BattlePlayer, block: Block): Boolean {
            return Relic.TURTLE_KING_CROWN.has(battler.player)
        }
    },
    SPIDER_KING_CROWN() {
        override fun modifyPower(source: Long, battler: BattlePlayer, block: Block): Long {
            val requireCombo = 70L

            val combo = battler.player.combo
            if (combo == requireCombo) {
                EffectMessages.COMBO.sendTo(battler.player)
            }
            return if (combo >= requireCombo)
                source + 1
            else
                source
        }

        override fun effectCondition(battler: BattlePlayer, block: Block): Boolean {
            return Relic.SPIDER_KING_CROWN.has(battler.player)
        }
    },
    ZOMBIE_KING_CROWN() {
        override fun modifyPower(source: Long, battler: BattlePlayer, block: Block): Long {
            val requireCombo = 150L

            val combo = battler.player.combo
            if (combo == requireCombo) {
                EffectMessages.COMBO.sendTo(battler.player)
            }
            return if (combo >= requireCombo)
                source + 1
            else
                source
        }

        override fun effectCondition(battler: BattlePlayer, block: Block): Boolean {
            return Relic.ZOMBIE_KING_CROWN.has(battler.player)
        }
    },
    SKELETON_KING_CROWN() {
        override fun modifyPower(source: Long, battler: BattlePlayer, block: Block): Long {
            val requireCombo = 350L

            val combo = battler.player.combo
            if (combo == requireCombo) {
                EffectMessages.COMBO.sendTo(battler.player)
            }
            return if (combo >= requireCombo)
                source + 1
            else
                source
        }

        override fun effectCondition(battler: BattlePlayer, block: Block): Boolean {
            return Relic.SKELETON_KING_CROWN.has(battler.player)
        }
    },
    ORC_KING_CROWN() {
        override fun modifyPower(source: Long, battler: BattlePlayer, block: Block): Long {
            val requireCombo = 800L

            val combo = battler.player.combo
            if (combo == requireCombo) {
                EffectMessages.COMBO.sendTo(battler.player)
            }
            return if (combo >= requireCombo)
                source + 1
            else
                source
        }

        override fun effectCondition(battler: BattlePlayer, block: Block): Boolean {
            return Relic.ORC_KING_CROWN.has(battler.player)
        }
    },
    GHOST_KING_CROWN() {
        override fun modifyPower(source: Long, battler: BattlePlayer, block: Block): Long {
            val requireCombo = 1200L

            val combo = battler.player.combo
            if (combo == requireCombo) {
                EffectMessages.COMBO.sendTo(battler.player)
            }
            return if (combo >= requireCombo)
                source + 1
            else
                source
        }

        override fun effectCondition(battler: BattlePlayer, block: Block): Boolean {
            return Relic.GHOST_KING_CROWN.has(battler.player)
        }
    },
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