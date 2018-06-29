package click.seichi.gigantic.skill

import click.seichi.gigantic.extension.getRelative
import click.seichi.gigantic.util.Box
import click.seichi.gigantic.util.CardinalDirection
import org.bukkit.block.Block
import org.bukkit.util.Vector

/**
 * @author tar0ss
 */
class BreakBox(
        width: Int,
        height: Int,
        depth: Int,
        style: BreakStyle,
        private val baseBlock: Block,
        private val cardinalDirection: CardinalDirection
) : Box(width, height, depth) {

    private val mWidth = (width - 1) / 2
    private val mHeight = style.marginHeight(this)
    private val mDepth = (depth - 1) / 2

    companion object {
        private val UPPER_NUM = 10
    }

    // 破壊範囲にあるすべてのブロック
    val blockSet by lazy {
        getBlockSet(
                (-mWidth until width - mWidth),
                (-mHeight until height - mHeight),
                (-mDepth until depth - mDepth)
        )
    }
    // 破壊範囲の上方ブロック（上限[UPPER_NUM])
    val upperBlockSet by lazy {
        getBlockSet(
                (-mWidth until width - mWidth),
                (height - mHeight + 1..height - mHeight + UPPER_NUM),
                (-mDepth until depth - mDepth)
        )
    }

    private fun getBlockSet(xRange: IntRange, yRange: IntRange, zRange: IntRange) =
            yRange.map { y ->
                xRange.map { x ->
                    zRange.map { z ->
                        baseBlock.getRelative(cardinalDirection, Vector(x, y, z))
                    }
                }.flatMap { it }
            }.flatMap { it }.toSet()


}