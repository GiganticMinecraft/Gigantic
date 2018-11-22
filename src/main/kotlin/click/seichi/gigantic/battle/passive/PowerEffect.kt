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
                EffectMessages.COMBO.sendTo(battler.player)
            }
            return if (mineCombo.currentCombo >= 10L)
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
            val mineCombo = battler.player.find(CatalogPlayerCache.MINE_COMBO) ?: return source
            if (mineCombo.currentCombo == 30L) {
                EffectMessages.COMBO.sendTo(battler.player)
            }
            return if (mineCombo.currentCombo >= 30L)
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
            val mineCombo = battler.player.find(CatalogPlayerCache.MINE_COMBO) ?: return source
            if (mineCombo.currentCombo == 70L) {
                EffectMessages.COMBO.sendTo(battler.player)
            }
            return if (mineCombo.currentCombo >= 70L)
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
            val mineCombo = battler.player.find(CatalogPlayerCache.MINE_COMBO) ?: return source
            if (mineCombo.currentCombo == 150L) {
                EffectMessages.COMBO.sendTo(battler.player)
            }
            return if (mineCombo.currentCombo >= 150L)
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
            val mineCombo = battler.player.find(CatalogPlayerCache.MINE_COMBO) ?: return source
            if (mineCombo.currentCombo == 350L) {
                EffectMessages.COMBO.sendTo(battler.player)
            }
            return if (mineCombo.currentCombo >= 350L)
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
            val mineCombo = battler.player.find(CatalogPlayerCache.MINE_COMBO) ?: return source
            if (mineCombo.currentCombo == 800L) {
                EffectMessages.COMBO.sendTo(battler.player)
            }
            return if (mineCombo.currentCombo >= 800L)
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
            val mineCombo = battler.player.find(CatalogPlayerCache.MINE_COMBO) ?: return source
            if (mineCombo.currentCombo == 1200L) {
                EffectMessages.COMBO.sendTo(battler.player)
            }
            return if (mineCombo.currentCombo >= 1200L)
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