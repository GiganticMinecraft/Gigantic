package click.seichi.gigantic.extension

import click.seichi.gigantic.util.CardinalDirection
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.util.Vector

/**
 * @author unicroak
 * @author tar0ss
 */
// 浮けるかどうか
val Block.canFloat: Boolean
    get() = when (type) {
        Material.AIR -> false
        else -> true
    }

private val crustMaterialSet = setOf(
        Material.STONE,
        Material.DIRT,
        Material.WATER,
        Material.LAVA,
        Material.EMERALD_ORE,
        Material.REDSTONE_ORE,
        Material.LAPIS_ORE,
        Material.GOLD_ORE,
        Material.IRON_ORE,
        Material.DIAMOND_ORE,
        Material.COAL_ORE,
        Material.GLOWING_REDSTONE_ORE,
        Material.QUARTZ_ORE,
        Material.NETHERRACK,
        Material.GRASS,
        Material.SAND,
        Material.GRAVEL,
        Material.MOSSY_COBBLESTONE,
        Material.ENDER_STONE,
        Material.HARD_CLAY,
        Material.ICE,
        Material.FROSTED_ICE,
        Material.PURPUR_BLOCK,
        Material.BONE_BLOCK,
        Material.CLAY
)

val Block.isCrust
    get() = crustMaterialSet.contains(type)

val Block.isSurface
    get() = (1..3).firstOrNull { getRelative(0, it, 0).type != Material.AIR }?.let { false } ?: true

val Block.centralLocation: Location
    get() = location.clone().add(0.5, 0.5, 0.5)

fun Block.getRelative(direction: CardinalDirection, vector: Vector): Block {
    return vector.rotateXZ(direction.deg).let { this.getRelative(it.blockX, it.blockY, it.blockZ) }
}


