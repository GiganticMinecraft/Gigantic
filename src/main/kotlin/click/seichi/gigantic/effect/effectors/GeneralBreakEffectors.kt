package click.seichi.gigantic.effect.effectors

import click.seichi.gigantic.animation.animations.effect.GeneralBreakAnimations
import click.seichi.gigantic.effect.EffectParameters
import click.seichi.gigantic.effect.effector.GeneralBreakEffector
import click.seichi.gigantic.extension.centralLocation
import click.seichi.gigantic.util.Random
import org.bukkit.block.Block
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
object GeneralBreakEffectors {

    // 通常はマイクラの破壊エフェクトが既に実行されているため何もしない
    val DEFAULT = object : GeneralBreakEffector {
        override fun generalBreak(player: Player, block: Block) {

        }
    }

    val EXPLOSION = object : GeneralBreakEffector {
        override fun generalBreak(player: Player, block: Block) {
            if (Random.nextDouble() > EffectParameters.EXPLOSION_PROBABILITY.div(100.0)) return
            GeneralBreakAnimations.EXPLOSION.start(block.centralLocation)
        }
    }

}