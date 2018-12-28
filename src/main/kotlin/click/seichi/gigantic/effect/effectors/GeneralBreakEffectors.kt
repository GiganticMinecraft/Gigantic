package click.seichi.gigantic.effect.effectors

import click.seichi.gigantic.effect.effector.GeneralBreakEffector
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

}