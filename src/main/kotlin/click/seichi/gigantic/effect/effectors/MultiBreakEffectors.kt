package click.seichi.gigantic.effect.effectors

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.animation.animations.effect.MultiBreakAnimations
import click.seichi.gigantic.config.Config
import click.seichi.gigantic.effect.effector.MultiBreakEffector
import click.seichi.gigantic.extension.centralLocation
import click.seichi.gigantic.extension.runTaskLater
import click.seichi.gigantic.extension.update
import click.seichi.gigantic.sound.sounds.EffectSounds
import click.seichi.gigantic.util.Random
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import kotlin.math.roundToLong

/**
 * @author tar0ss
 */
object MultiBreakEffectors {

    val DEFAULT = object : MultiBreakEffector {
        override fun multiBreak(player: Player, base: Block, breakBlockSet: Set<Block>) {
            breakBlockSet.run {
                forEach { target ->
                    target.type = Material.AIR
                }
                // 凍結，火成等の処理を最後にまとめる
                update(player, breakBlockSet)
            }
        }
    }

    val EXPLOSION = object : MultiBreakEffector {
        override fun multiBreak(player: Player, base: Block, breakBlockSet: Set<Block>) {
            breakBlockSet.run {
                forEach { target ->
                    target.type = Material.AIR
                }
                forEach { target ->
                    MultiBreakAnimations.EXPLOSION.start(target.centralLocation)
                }
                EffectSounds.EXPLOSION.play(base.centralLocation)
                // 凍結，火成等の処理を最後にまとめる
                update(player, breakBlockSet)
            }
        }
    }

    val BLIZZARD = object : MultiBreakEffector {

        override fun multiBreak(player: Player, base: Block, breakBlockSet: Set<Block>) {
            Gigantic.USE_BLOCK_SET.addAll(breakBlockSet)
            breakBlockSet.forEach { target ->
                target.type = Material.PACKED_ICE
            }
            runTaskLater(Config.SPELL_MULTI_BREAK_DELAY.times(20.0).roundToLong()) {
                Gigantic.USE_BLOCK_SET.removeAll(breakBlockSet)
                breakBlockSet.forEach { target ->
                    target.type = Material.AIR
                }
                breakBlockSet.forEach { target ->
                    MultiBreakAnimations.BLIZZARD.start(target.centralLocation)
                }
                EffectSounds.BLIZZARD.play(base.centralLocation)
                update(player, breakBlockSet)
            }
        }
    }

    val MAGIC = object : MultiBreakEffector {
        override fun multiBreak(player: Player, base: Block, breakBlockSet: Set<Block>) {
            Gigantic.USE_BLOCK_SET.addAll(breakBlockSet)
            breakBlockSet.forEach { target ->
                target.type = Random.nextWool()
            }
            runTaskLater(Config.SPELL_MULTI_BREAK_DELAY.times(20.0).roundToLong()) {
                Gigantic.USE_BLOCK_SET.removeAll(breakBlockSet)
                breakBlockSet.forEach { target ->
                    target.type = Material.AIR
                }
                breakBlockSet.forEach { target ->
                    MultiBreakAnimations.MAGIC.start(target.centralLocation)
                }
                EffectSounds.MAGIC.play(base.centralLocation)
                update(player, breakBlockSet)
            }
        }
    }

    val FLAME = object : MultiBreakEffector {
        override fun multiBreak(player: Player, base: Block, breakBlockSet: Set<Block>) {
            breakBlockSet.run {
                forEach { target ->
                    target.type = Material.AIR
                }
                forEach { target ->
                    MultiBreakAnimations.FLAME.start(target.centralLocation)
                }
                EffectSounds.FLAME.play(base.centralLocation)
                // 凍結，火成等の処理を最後にまとめる
                update(player, breakBlockSet)
            }
        }
    }

    val WITCH_SCENT = object : MultiBreakEffector {
        override fun multiBreak(player: Player, base: Block, breakBlockSet: Set<Block>) {
            breakBlockSet.run {
                forEach { target ->
                    target.type = Material.AIR
                }
                forEach { target ->
                    MultiBreakAnimations.WITCH_SCENT.start(target.centralLocation)
                }
                EffectSounds.WITCH_SCENT.play(base.centralLocation)
                // 凍結，火成等の処理を最後にまとめる
                update(player, breakBlockSet)
            }
        }
    }

    val SLIME = object : MultiBreakEffector {

        override fun multiBreak(player: Player, base: Block, breakBlockSet: Set<Block>) {
            Gigantic.USE_BLOCK_SET.addAll(breakBlockSet)
            breakBlockSet.forEach { target ->
                target.type = Material.SLIME_BLOCK
            }
            runTaskLater(Config.SPELL_MULTI_BREAK_DELAY.times(20.0).roundToLong()) {
                Gigantic.USE_BLOCK_SET.removeAll(breakBlockSet)
                breakBlockSet.forEach { target ->
                    target.type = Material.AIR
                }
                breakBlockSet.forEach { target ->
                    MultiBreakAnimations.SLIME.start(target.centralLocation)
                }
                EffectSounds.SLIME.play(base.centralLocation)
                update(player, breakBlockSet)
            }
        }
    }

    val BUBBLE = object : MultiBreakEffector {

        override fun multiBreak(player: Player, base: Block, breakBlockSet: Set<Block>) {
            Gigantic.USE_BLOCK_SET.addAll(breakBlockSet)
            Gigantic.USE_BLOCK_SET.add(base)
            breakBlockSet.forEach { target ->
                target.type = Material.WATER
            }
            base.type = Material.WATER
            runTaskLater(Config.SPELL_MULTI_BREAK_DELAY.times(20.0).roundToLong()) {
                Gigantic.USE_BLOCK_SET.removeAll(breakBlockSet)
                Gigantic.USE_BLOCK_SET.remove(base)
                breakBlockSet.forEach { target ->
                    target.type = Material.AIR
                }
                base.type = Material.AIR
                breakBlockSet.forEach { target ->
                    MultiBreakAnimations.BUBBLE.start(target.centralLocation)
                }
                EffectSounds.BUBBLE.play(base.centralLocation)
                update(player, breakBlockSet)
            }
        }
    }


    val ALCHEMIA = object : MultiBreakEffector {

        private val alchemySet = setOf(
                Material.GOLD_ORE,
                Material.DIAMOND_ORE,
                Material.LAPIS_ORE,
                Material.REDSTONE_ORE
        )

        fun randomMaterial() = alchemySet.random()

        override fun multiBreak(player: Player, base: Block, breakBlockSet: Set<Block>) {
            Gigantic.USE_BLOCK_SET.addAll(breakBlockSet)
            breakBlockSet.forEach { target ->
                target.type = randomMaterial()
            }
            // ブロックのtypeを取得するために1tick早めに処理
            runTaskLater(Config.SPELL_MULTI_BREAK_DELAY.times(20.0).roundToLong().minus(1)) {
                breakBlockSet.forEach { target ->
                    MultiBreakAnimations.ALCHEMIA.start(target.centralLocation)
                }
            }

            runTaskLater(Config.SPELL_MULTI_BREAK_DELAY.times(20.0).roundToLong()) {
                Gigantic.USE_BLOCK_SET.removeAll(breakBlockSet)
                breakBlockSet.forEach { target ->
                    target.type = Material.AIR
                }
                EffectSounds.ALCHEMIA.play(base.centralLocation)
                update(player, breakBlockSet)
            }
        }
    }

}
