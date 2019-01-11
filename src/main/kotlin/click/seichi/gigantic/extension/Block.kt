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


// 落下判定に使う。自然生成されるブロックのみ
val CRUSTS = setOf(
        Material.STONE,
        Material.GRANITE,
        Material.MOSSY_COBBLESTONE,
        Material.MOSSY_COBBLESTONE_WALL,
        Material.MOSSY_STONE_BRICKS,
        Material.RED_SAND,
        Material.SANDSTONE,
        Material.RED_SANDSTONE,
        Material.CHISELED_RED_SANDSTONE,
        Material.CHISELED_SANDSTONE,
        Material.CUT_RED_SANDSTONE,
        Material.CUT_SANDSTONE,
        Material.SMOOTH_RED_SANDSTONE,
        Material.SMOOTH_SANDSTONE,
        Material.OBSIDIAN,
        Material.DIORITE,
        Material.ANDESITE,
        Material.STONE_BRICKS,
        Material.CHISELED_STONE_BRICKS,
        Material.CRACKED_STONE_BRICKS,
        Material.DIRT,
        Material.GRASS_BLOCK,
        Material.GRASS_PATH,
        Material.EMERALD_ORE,
        Material.LAPIS_ORE,
        Material.GOLD_ORE,
        Material.IRON_ORE,
        Material.DIAMOND_ORE,
        Material.COAL_ORE,
        Material.REDSTONE_ORE,
        Material.NETHER_QUARTZ_ORE,
        Material.NETHERRACK,
        Material.SAND,
        Material.GRAVEL,
        Material.END_STONE,
        Material.TERRACOTTA,
        Material.ICE,
        Material.FROSTED_ICE,
        Material.PURPUR_BLOCK,
        Material.BONE_BLOCK,
        Material.CLAY,
        Material.COARSE_DIRT,
        Material.PODZOL,
        Material.OAK_PLANKS,
        Material.SPRUCE_PLANKS,
        Material.BIRCH_PLANKS,
        Material.JUNGLE_PLANKS,
        Material.ACACIA_PLANKS,
        Material.DARK_OAK_PLANKS,
        Material.SPONGE,
        Material.WET_SPONGE,
        Material.WHITE_TERRACOTTA,
        Material.ORANGE_TERRACOTTA,
        Material.MAGENTA_TERRACOTTA,
        Material.LIGHT_BLUE_TERRACOTTA,
        Material.YELLOW_TERRACOTTA,
        Material.LIME_TERRACOTTA,
        Material.PINK_TERRACOTTA,
        Material.GRAY_TERRACOTTA,
        Material.LIGHT_GRAY_TERRACOTTA,
        Material.CYAN_TERRACOTTA,
        Material.PURPLE_TERRACOTTA,
        Material.BLUE_TERRACOTTA,
        Material.BROWN_TERRACOTTA,
        Material.GREEN_TERRACOTTA,
        Material.RED_TERRACOTTA,
        Material.BLACK_TERRACOTTA,
        Material.PRISMARINE,
        Material.PRISMARINE_BRICKS,
        Material.DARK_PRISMARINE,
        Material.PACKED_ICE,
        Material.BLUE_ICE,
        Material.MAGMA_BLOCK,
        Material.INFESTED_CHISELED_STONE_BRICKS,
        Material.INFESTED_COBBLESTONE,
        Material.INFESTED_CRACKED_STONE_BRICKS,
        Material.INFESTED_MOSSY_STONE_BRICKS,
        Material.INFESTED_STONE,
        Material.INFESTED_STONE_BRICKS,
        Material.COBBLESTONE,
        Material.PUMPKIN,
        Material.MELON,
        Material.MYCELIUM
)

val LOGS = setOf(
        Material.BIRCH_LOG,
        Material.ACACIA_LOG,
        Material.DARK_OAK_LOG,
        Material.JUNGLE_LOG,
        Material.OAK_LOG,
        Material.SPRUCE_LOG,
        Material.STRIPPED_BIRCH_LOG,
        Material.STRIPPED_ACACIA_LOG,
        Material.STRIPPED_DARK_OAK_LOG,
        Material.STRIPPED_JUNGLE_LOG,
        Material.STRIPPED_OAK_LOG,
        Material.STRIPPED_SPRUCE_LOG,
        Material.MUSHROOM_STEM,
        Material.RED_MUSHROOM_BLOCK,
        Material.BROWN_MUSHROOM_BLOCK
)

val LEAVES = setOf(
        Material.ACACIA_LEAVES,
        Material.BIRCH_LEAVES,
        Material.DARK_OAK_LEAVES,
        Material.JUNGLE_LEAVES,
        Material.OAK_LEAVES,
        Material.SPRUCE_LEAVES
)

val AIRS = setOf(
        Material.AIR,
        Material.CAVE_AIR,
        Material.VOID_AIR
)

val GRASSES = setOf(
        Material.GRASS_BLOCK,
        Material.GRASS_PATH,
        Material.GRASS,
        Material.TALL_GRASS,
        Material.TALL_SEAGRASS,
        Material.FERN,
        Material.SEA_PICKLE,
        Material.DANDELION,
        Material.POPPY,
        Material.BLUE_ORCHID,
        Material.ALLIUM,
        Material.AZURE_BLUET,
        Material.RED_TULIP,
        Material.ORANGE_TULIP,
        Material.WHITE_TULIP,
        Material.PINK_TULIP,
        Material.OXEYE_DAISY,
        Material.BROWN_MUSHROOM_BLOCK,
        Material.RED_MUSHROOM_BLOCK,
        Material.BROWN_MUSHROOM,
        Material.RED_MUSHROOM,
        Material.MUSHROOM_STEM,
        Material.CACTUS,
        Material.SUNFLOWER,
        Material.LILAC,
        Material.ROSE_BUSH,
        Material.PEONY,
        Material.LARGE_FERN
)

val WATER_PLANTS = setOf(
        Material.SEAGRASS,
        Material.SEA_PICKLE,
        Material.TALL_SEAGRASS,
        Material.KELP_PLANT,
        Material.KELP,
        Material.BRAIN_CORAL,
        Material.BUBBLE_CORAL,
        Material.FIRE_CORAL,
        Material.HORN_CORAL,
        Material.TUBE_CORAL,
        Material.BRAIN_CORAL_FAN,
        Material.BUBBLE_CORAL_FAN,
        Material.FIRE_CORAL_FAN,
        Material.HORN_CORAL_FAN,
        Material.TUBE_CORAL_FAN
)

val WATERS = setOf(
        Material.WATER,
        Material.BUBBLE_COLUMN,
        *WATER_PLANTS.toTypedArray()
)

val TREES = setOf(*LOGS.toTypedArray(), *LEAVES.toTypedArray())

val DIRTS = setOf(
        Material.DIRT,
        Material.GRASS_BLOCK,
        Material.GRAVEL
)

val INFESTEDS = setOf(
        Material.INFESTED_STONE,
        Material.INFESTED_STONE_BRICKS,
        Material.INFESTED_MOSSY_STONE_BRICKS,
        Material.INFESTED_CRACKED_STONE_BRICKS,
        Material.INFESTED_COBBLESTONE,
        Material.INFESTED_CHISELED_STONE_BRICKS
)

val MOSSIES = setOf(
        Material.MOSSY_COBBLESTONE,
        Material.MOSSY_COBBLESTONE_WALL,
        Material.MOSSY_STONE_BRICKS,
        Material.INFESTED_MOSSY_STONE_BRICKS
)

// Not contain log blocks
val Block.isCrust
    get() = CRUSTS.contains(type)

val Block.isLog
    get() = LOGS.contains(type)

val Block.isLeaves
    get() = LEAVES.contains(type)

val Block.isTree
    get() = TREES.contains(type)

val Block.isGrass
    get() = GRASSES.contains(type)

val Block.isWaterPlant
    get() = WATER_PLANTS.contains(type)

val Block.isAir
    get() = AIRS.contains(type)

val Block.isWater
    get() = WATERS.contains(type)

val Block.isLava
    get() = type == Material.LAVA

val Block.isInfested
    get() = INFESTEDS.contains(type)

val Block.isMossy
    get() = MOSSIES.contains(type)

val Block.isSurface
    get() = if (isAir) false
    else (1..3).firstOrNull {
        val block = getRelative(0, it, 0)
        !block.isAir && !block.isPassable
    }?.let { false } ?: true


val Block.centralLocation: Location
    get() = location.clone().add(0.5, 0.5, 0.5)


fun Block.update() {
    changeRelativeBedrock()
    condenseRelativeLiquid()
    // 最悪空中に残ってしまった時のために，ここで保険
    clearRelativeFloatingBlock()

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
        BlockFace.DOWN
        // どっちにしろ一番上まで探索するので，UPは除外
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

fun Block.condenseLiquid(playSound: Boolean = true, playAnimation: Boolean = true) {
    if (Gigantic.BROKEN_BLOCK_SET.contains(this)) return
    if (!isWater && !isLava) return
    when {
        isWater -> {
            if (playSound)
                PlayerSounds.ON_CONDENSE_WATER.play(centralLocation)
            if (playAnimation)
                PlayerAnimations.ON_CONDENSE_WATER.start(centralLocation)
            type = Material.PACKED_ICE
        }
        isLava -> {
            if (playSound)
                PlayerSounds.ON_CONDENSE_LAVA.play(centralLocation)
            if (playAnimation)
                PlayerAnimations.ON_CONDENSE_LAVA.start(centralLocation)
            type = Material.MAGMA_BLOCK
        }
    }
}

private fun Block.clearRelativeFloatingBlock() {
    faceSet.map { getRelative(it) }
            .forEach { it.clearFloatingBlock() }
}

private fun Block.clearFloatingBlock() {
    if (isCrust) return
    if (isLog) return
    if (y == 0) return
    type = Material.AIR
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

fun Block.firstOrNullOfNearPlayer(player: Player) = if (Gigantic.IS_LIVE) null
else world.players
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
