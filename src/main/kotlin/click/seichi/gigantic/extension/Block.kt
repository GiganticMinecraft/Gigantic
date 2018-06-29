package click.seichi.gigantic.extension

import click.seichi.gigantic.util.CardinalDirection
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.util.Vector

/**
 * @author tar0ss
 */
// 根を張るブロックかどうか（つまり、下のブロックが消えた時に状態が変化するかどうか）
val Block.isRoot: Boolean
    get() = when (type) {
        Material.AIR -> false
        else -> true
    }


val Block.centralLocation: Location
    get() = location.clone().add(0.5, 0.5, 0.5)

fun Block.getRelative(direction: CardinalDirection, vector: Vector): Block {
    return vector.rotateXZ(direction.deg).let { this.getRelative(it.blockX, it.blockY, it.blockZ) }
}


