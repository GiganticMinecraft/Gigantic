package click.seichi.gigantic.effect.effectors

import click.seichi.gigantic.animation.animations.effect.GeneralBreakAnimations
import click.seichi.gigantic.effect.effector.GeneralBreakEffector
import click.seichi.gigantic.extension.centralLocation
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
            GeneralBreakAnimations.EXPLOSION.start(block.centralLocation)
        }
    }

    val BLIZZARD = object : GeneralBreakEffector {
        override fun generalBreak(player: Player, block: Block) {
            GeneralBreakAnimations.BLIZZARD.start(block.centralLocation)
        }
    }

    val MAGIC = object : GeneralBreakEffector {
        override fun generalBreak(player: Player, block: Block) {
            GeneralBreakAnimations.MAGIC.start(block.centralLocation)
        }
    }

    val FLAME = object : GeneralBreakEffector {
        override fun generalBreak(player: Player, block: Block) {
            GeneralBreakAnimations.FLAME.start(block.centralLocation)
        }
    }

    val WITCH_SCENT = object : GeneralBreakEffector {
        override fun generalBreak(player: Player, block: Block) {
            GeneralBreakAnimations.WITCH_SCENT.start(block.centralLocation)
        }
    }

}