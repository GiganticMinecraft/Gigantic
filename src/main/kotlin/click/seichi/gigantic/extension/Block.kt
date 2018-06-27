package click.seichi.gigantic.extension

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block

/**
 * @author tar0ss
 * @author unicroak
 */
private val crustMaterialSet = setOf(
        Material.STONE,
        Material.DIRT,
        Material.WATER,
        Material.LAVA,
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


