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


private val logs = listOf(Material.LOG, Material.LOG_2)

private val leaves = listOf(Material.LEAVES, Material.LEAVES_2)

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