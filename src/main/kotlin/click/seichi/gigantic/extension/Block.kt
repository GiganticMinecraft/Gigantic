package click.seichi.gigantic.extension

import click.seichi.gigantic.Gigantic
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block

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
//        Material.GRANITE,
//        Material.MOSSY_COBBLESTONE,
//        Material.MOSSY_COBBLESTONE_WALL,
//        Material.MOSSY_STONE_BRICKS,
//        Material.RED_SAND,
//        Material.SANDSTONE,
//        Material.CHISELED_RED_SANDSTONE,
//        Material.CHISELED_SANDSTONE,
//        Material.CUT_RED_SANDSTONE,
//        Material.CUT_SANDSTONE,
//        Material.SMOOTH_RED_SANDSTONE,
//        Material.SMOOTH_SANDSTONE,
//        Material.SANDSTONE,
//        Material.OBSIDIAN,
//        Material.DIORITE,
//        Material.ANDESITE,
//        Material.STONE_BRICKS,
//        Material.CHISELED_STONE_BRICKS,
//        Material.CRACKED_STONE_BRICKS,
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
        Material.REDSTONE_ORE,
        Material.NETHER_QUARTZ_ORE,
        Material.NETHERRACK,
        Material.GRASS,
        Material.SAND,
        Material.GRAVEL,
        Material.MOSSY_COBBLESTONE,
        Material.END_STONE,
        Material.TERRACOTTA,
        Material.ICE,
        Material.FROSTED_ICE,
        Material.PURPUR_BLOCK,
        Material.BONE_BLOCK,
        Material.CLAY
)

val Block.isCrust
    get() = crustMaterialSet.contains(type)


private val logs = listOf(
        Material.BIRCH_LOG,
        Material.ACACIA_LOG,
        Material.DARK_OAK_LOG,
        Material.JUNGLE_LOG,
        Material.OAK_LOG,
        Material.SPRUCE_LOG
)

private val leaves = listOf(
        Material.ACACIA_LEAVES,
        Material.BIRCH_LEAVES,
        Material.DARK_OAK_LEAVES,
        Material.JUNGLE_LEAVES,
        Material.OAK_LEAVES,
        Material.SPRUCE_LEAVES
)

private val trees = listOf(*logs.toTypedArray(), *leaves.toTypedArray())

val Block.isLog
    get() = logs.contains(type)

val Block.isLeaves
    get() = leaves.contains(type)

val Block.isTree
    get() = trees.contains(type)

val Block.isSurface
    get() = (1..3).firstOrNull { getRelative(0, it, 0).type != Material.AIR }?.let { false } ?: true

val Block.centralLocation: Location
    get() = location.clone().add(0.5, 0.5, 0.5)

@Suppress("DEPRECATION")
fun Block.fallUpper() {
    var count = 0
    val fallTask = object : Runnable {
        override fun run() {
            val target = getRelative(0, count + 1, 0) ?: return
            if (target.isCrust) {
                target.location.world.spawnFallingBlock(
                        target.location.central.subtract(0.0, 0.5, 0.0),
                        target.type,
                        target.data
                )
                target.type = Material.AIR
                count++
                Bukkit.getScheduler().runTaskLater(Gigantic.PLUGIN, this, 2L)
            }
        }
    }
    Bukkit.getScheduler().runTask(Gigantic.PLUGIN, fallTask)
}