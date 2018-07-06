package click.seichi.gigantic.skill.breakskill

import click.seichi.gigantic.extension.getRelative
import click.seichi.gigantic.util.Box
import click.seichi.gigantic.util.CardinalDirection
import org.bukkit.block.Block
import org.bukkit.util.Vector

/**
 * @author tar0ss
 */
class BreakBox(
        box: Box,
        style: BreakStyle,
        private val baseBlock: Block,
        private val cardinalDirection: CardinalDirection,
        private val maxUpperNum: Int = 5
) : Box(box) {

    private val mWidth = (width - 1) / 2
    private val mHeight = style.marginHeight(this)
    private val mDepth = (depth - 1) / 2

    // 破壊範囲にあるすべてのブロック
    val blockSet by lazy {
        getBlockSet(
                (-mWidth until width - mWidth),
                (-mHeight until height - mHeight),
                (-mDepth until depth - mDepth)
        )
    }
    // 破壊範囲の上方ブロック（上限[maxUpperNum])
    val upperBlockSet by lazy {
        getBlockSet(
                (-mWidth until width - mWidth),
                (height - mHeight + 1..height - mHeight + maxUpperNum),
                (-mDepth until depth - mDepth)
        )
    }

    private fun getBlockSet(xRange: IntRange, yRange: IntRange, zRange: IntRange) =
            yRange.reversed().map { y ->
                xRange.map { x ->
                    zRange.map { z ->
                        baseBlock.getRelative(cardinalDirection, Vector(x, y, z))
                    }
                }.flatMap { it }
            }.flatMap { it }.toSet()


}