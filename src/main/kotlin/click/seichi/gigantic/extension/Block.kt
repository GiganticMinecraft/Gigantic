package click.seichi.gigantic.extension

import click.seichi.gigantic.Gigantic
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace

/**
 * @author unicroak
 * @author tar0ss
 */


// Not contain log blocks
val Block.isCrust
    get() = Gigantic.CRUSTS.contains(type)

val Block.isLog
    get() = Gigantic.LOGS.contains(type)

val Block.isLeaves
    get() = Gigantic.LEAVES.contains(type)

val Block.isTree
    get() = Gigantic.TREES.contains(type)

val Block.isGrass
    get() = Gigantic.GRASSES.contains(type)

val Block.isSurface
    get() = (1..3).firstOrNull { !Gigantic.AIRS.contains(getRelative(0, it, 0).type) }?.let { false } ?: true

val Block.centralLocation: Location
    get() = location.clone().add(0.5, 0.5, 0.5)


fun Block.fallUpperCrustBlock() {
    var count = 0
    val fallTask = object : Runnable {
        override fun run() {
            val target = getRelative(0, count + 1, 0) ?: return
            target.changeRelativeBedrock()
            if (target.isCrust) {
                target.location.world.spawnFallingBlock(
                        target.location.central.subtract(0.0, 0.5, 0.0),
                        target.blockData
                )
                target.type = Material.AIR
                count++
                Bukkit.getScheduler().runTaskLater(Gigantic.PLUGIN, this, 2L)
            }
        }
    }
    Bukkit.getScheduler().runTask(Gigantic.PLUGIN, fallTask)
}

private val bedrockFaceSet = setOf(
        BlockFace.NORTH,
        BlockFace.EAST,
        BlockFace.SOUTH,
        BlockFace.WEST,
        BlockFace.UP,
        BlockFace.DOWN
)

fun Block.changeRelativeBedrock() {
    bedrockFaceSet.map { getRelative(it) }
            .filter { it.type == Material.BEDROCK && it.y != 0 }
            .forEach { it.type = Material.STONE }
}

fun Block.removeUpperLiquidBlock() {
    var count = 0
    val fallTask = object : Runnable {
        override fun run() {
            val target = getRelative(0, count + 1, 0) ?: return
            target.changeRelativeBedrock()
            if (target.isLiquid) {
                target.type = Material.AIR
                count++
                Bukkit.getScheduler().runTaskLater(Gigantic.PLUGIN, this, 2L)
            }
        }
    }
    Bukkit.getScheduler().runTask(Gigantic.PLUGIN, fallTask)
}