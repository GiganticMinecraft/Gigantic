package click.seichi.gigantic.extension

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.animation.animations.PlayerAnimations
import click.seichi.gigantic.battle.BattleManager
import click.seichi.gigantic.breaker.Cutter
import click.seichi.gigantic.config.Config
import click.seichi.gigantic.sound.sounds.PlayerSounds
import org.bukkit.GameMode
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

val Block.isAir
    get() = Gigantic.AIRS.contains(type)


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
                target.y > 255 -> {
                    return
                }
                Gigantic.BROKEN_BLOCK_SET.contains(target) -> {
                    target.update()
                }
                target.isCrust -> {
                    target.fall()
                    target.update()
                }
                target.isLog -> {
                    Cutter().breakRelationalBlock(target, false)
                    return
                }
                else -> {
                    target.update()
                }
            }

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
            .forEach { it.changeBedrock() }
}

fun Block.changeBedrock() {
    if (Gigantic.BROKEN_BLOCK_SET.contains(this)) return
    if (type != Material.BEDROCK) return
    if (y == 0) return
    type = Material.STONE
}


private fun Block.condenseRelativeLiquid() {
    faceSet.map { getRelative(it) }
            .forEach {
                it.condenseLiquid()
            }
}

fun Block.condenseLiquid() {
    if (Gigantic.BROKEN_BLOCK_SET.contains(this)) return
    if (!isLiquid && !isWaterPlant) return
    when {
        type == Material.WATER || isWaterPlant -> {
            PlayerSounds.ON_CONDENSE_WATER.play(centralLocation)
            PlayerAnimations.ON_CONDENSE_WATER.start(centralLocation)
            type = Material.PACKED_ICE
        }
        type == Material.LAVA -> {
            PlayerSounds.ON_CONDENSE_LAVA.play(centralLocation)
            PlayerAnimations.ON_CONDENSE_LAVA.start(centralLocation)
            type = Material.MAGMA_BLOCK
        }
    }
}

val Block.isSpawnArea: Boolean
    get() = chunk.isSpawnArea

fun Block.findBattle() = BattleManager.findBattle(chunk)

val Block.surfaceBlock: Block
    get() {
        var block = world.getBlockAt(x, 255, z)
        while (!block.isSurface && block.y > 0) {
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

// そのブロックとplayerまでの距離を取得
private fun Block.xzDistance(player: Player): Double {
    val central = centralLocation
    return Math.sqrt(
            Math.pow(central.x - player.location.x, 2.0) +
                    Math.pow(central.z - player.location.z, 2.0)
    )
}

fun Block.firstOrNullOfNearPlayer(player: Player) = world.players
        .filterNotNull()
        .filter { it.isValid }
        .filter { it.gameMode == GameMode.SURVIVAL }
        .filter { !it.isFlying && it.uniqueId != player.uniqueId }
        // フォローされていれば除外
        .filter { !it.isFollow(player.uniqueId) }
        .firstOrNull { xzDistance(it) < Config.PROTECT_RADIUS }

fun Block.calcGravity() = ((1 + Config.MAX_BREAKABLE_GRAVITY)..(255 - y))
        .map { getRelative(BlockFace.UP, it) }
        .filter { it.isCrust }
        .size
