package click.seichi.gigantic.extension

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.animation.animations.PlayerAnimations
import click.seichi.gigantic.battle.BattleManager
import click.seichi.gigantic.breaker.Cutter
import click.seichi.gigantic.sound.sounds.PlayerSounds
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

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

val Block.isWaterPlant
    get() = Gigantic.WATER_PLANTS.contains(type)

val Block.isSurface
    get() = if (Gigantic.AIRS.contains(type)) false
    else (1..3).firstOrNull {
        val block = getRelative(0, it, 0)
        !Gigantic.AIRS.contains(block.type) && !block.isPassable
    }?.let { false } ?: true


val Block.centralLocation: Location
    get() = location.clone().add(0.5, 0.5, 0.5)


fun Block.update() {
    changeRelativeBedrock()
    condenseRelativeLiquid()
    fallUpperCrustBlock()
}

private fun Block.fallUpperCrustBlock() {
    object : BukkitRunnable() {
        override fun run() {
            val target = getRelative(BlockFace.UP) ?: return
            when {
                Gigantic.BROKEN_BLOCK_SET.contains(target) -> {
                }
                target.isCrust -> {
                    target.fall()
                }
                target.isLog -> {
                    Cutter().breakRelationalBlock(target, false)
                    return
                }
            }
            target.update()
        }
    }.runTaskLater(Gigantic.PLUGIN, 2L)
}


private fun Block.fall() {
    location.world.spawnFallingBlock(
            location.central.subtract(0.0, 0.5, 0.0),
            blockData
    )
    type = Material.AIR
}

private val faceSet = setOf(
        BlockFace.NORTH,
        BlockFace.EAST,
        BlockFace.SOUTH,
        BlockFace.WEST,
        BlockFace.UP,
        BlockFace.DOWN
)

private fun Block.changeRelativeBedrock() {
    faceSet.map { getRelative(it) }
            .filter { !Gigantic.BROKEN_BLOCK_SET.contains(it) }
            .filter { it.type == Material.BEDROCK && it.y != 0 }
            .forEach { it.type = Material.STONE }
}


private fun Block.condenseRelativeLiquid() {
    faceSet.map { getRelative(it) }
            .filter { !Gigantic.BROKEN_BLOCK_SET.contains(it) }
            .filter { it.isLiquid || it.isWaterPlant }
            .forEach {
                when {
                    it.type == Material.WATER || it.isWaterPlant -> {
                        PlayerSounds.ON_CONDENSE_WATER.play(it.centralLocation)
                        PlayerAnimations.ON_CONDENSE_WATER.start(it.centralLocation)
                        it.type = Material.PACKED_ICE
                    }
                    it.type == Material.LAVA -> {
                        PlayerSounds.ON_CONDENSE_LAVA.play(it.centralLocation)
                        PlayerAnimations.ON_CONDENSE_LAVA.start(it.centralLocation)
                        it.type = Material.MAGMA_BLOCK
                    }
                    else -> {
                    }
                }
            }
}

val Block.isSpawnArea: Boolean
    get() = chunk.isSpawnArea

fun Block.findBattle() = BattleManager.findBattle(chunk)

val Block.surfaceBlock: Block
    get() {
        var block = world.getBlockAt(x, 255, z)
        while (!block.isSurface && block.y > 1) {
            block = block.getRelative(BlockFace.DOWN)
        }
        return block
    }

fun Block.isUnder(player: Player) = player.location.blockY > y

val BlockFace.leftFace: BlockFace
    get() = when (this) {
        BlockFace.NORTH -> BlockFace.WEST
        BlockFace.EAST -> BlockFace.NORTH
        BlockFace.SOUTH -> BlockFace.EAST
        BlockFace.WEST -> BlockFace.SOUTH
        else -> BlockFace.NORTH
    }

val BlockFace.rightFace: BlockFace
    get() = leftFace.oppositeFace
