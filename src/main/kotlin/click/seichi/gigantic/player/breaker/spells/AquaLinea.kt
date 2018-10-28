package click.seichi.gigantic.player.breaker.spells

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.animation.SpellAnimations
import click.seichi.gigantic.extension.cardinalDirection
import click.seichi.gigantic.extension.centralLocation
import click.seichi.gigantic.extension.isCrust
import click.seichi.gigantic.player.breaker.Miner
import click.seichi.gigantic.player.breaker.SpellCaster
import click.seichi.gigantic.player.spell.SpellParameters
import click.seichi.gigantic.sound.sounds.SpellSounds
import click.seichi.gigantic.util.CardinalDirection
import org.bukkit.Bukkit
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import java.math.BigDecimal

/**
 * @author tar0ss
 */
class AquaLinea : Miner(), SpellCaster {

    override fun cast(player: Player, base: Block) {
        SpellAnimations.AQUA_LINEA_ON_FIRE.start(base.centralLocation)
        SpellSounds.AQUA_LINER_ON_FIRE.play(player.location)

        val linedBlockFace = getLinedBlockFace(player)

        // baseの連続破壊
        breakRelationalBlock(player, base, true, linedBlockFace, 1)

        var delay = 0L
        // Relationalの連続破壊
        getRelationalBlocks(player, base).toSet().forEach { target ->
            delay += SpellParameters.AQUA_LINEA_LINED_BREAK_INTERVAL
            Bukkit.getScheduler().runTaskLater(Gigantic.PLUGIN, {
                breakRelationalBlock(player, target, false, linedBlockFace, 1)
            }, delay)
        }
    }

    override fun calcConsumeMana(player: Player, block: Block): BigDecimal {
        return SpellParameters.AQUA_LINEA_MANA_PER_BLOCK.toBigDecimal()
    }

    override fun onCastToBlock(player: Player, block: Block) {
        SpellAnimations.AQUA_LINEA_ON_BREAK.start(block.centralLocation)
        SpellSounds.AQUA_LINER_ON_BREAK.play(block.centralLocation)
    }

    private fun getRelationalBlocks(player: Player, block: Block): Array<Block> =
            getRelationalFaceSet(player).map { blockFace ->
                val target = block.getRelative(blockFace)
                getLineBlocks(target, blockFace, 1).toList()
            }.flatten().shuffled().toTypedArray()


    private fun getLineBlocks(block: Block, blockFace: BlockFace, depth: Int): Array<Block> {
        return if (depth > maxDepth) arrayOf()
        else arrayOf(block, *getLineBlocks(block.getRelative(blockFace), blockFace, depth + 1))
    }

    private fun getRelationalFaceSet(player: Player): Set<BlockFace> {
        val cardinalDirection = player.cardinalDirection
        return when (cardinalDirection) {
            CardinalDirection.NORTH,
            CardinalDirection.SOUTH -> setOf(BlockFace.WEST, BlockFace.EAST)
            CardinalDirection.EAST,
            CardinalDirection.WEST -> setOf(BlockFace.NORTH, BlockFace.SOUTH)
        }
    }

    private fun getLinedBlockFace(player: Player): BlockFace {
        val cardinalDirection = player.cardinalDirection
        return when (cardinalDirection) {
            CardinalDirection.NORTH -> BlockFace.NORTH
            CardinalDirection.SOUTH -> BlockFace.SOUTH
            CardinalDirection.EAST -> BlockFace.EAST
            CardinalDirection.WEST -> BlockFace.WEST
        }
    }

    private fun breakRelationalBlock(
            player: Player,
            target: Block,
            isBaseBlock: Boolean,
            linedBlockFace: BlockFace,
            count: Int
    ) {
//        player.server.consoleSender.sendMessage("${target.type} ${target.x} ${target.y} ${target.z} ")
        if (count > maxCount) return

        // restart 10 ticks later
        Bukkit.getScheduler().runTaskLater(
                Gigantic.PLUGIN, {
            if (!player.isValid) return@runTaskLater
            breakRelationalBlock(player, target, false, linedBlockFace, count + 1)
        }, SpellParameters.AQUA_LINEA_RESTART_BREAK_INTERVAL)

        breakLinedBlock(player, target, isBaseBlock, linedBlockFace, 1)
    }

    private fun breakLinedBlock(
            player: Player,
            target: Block,
            isBaseBlock: Boolean,
            linedBlockFace: BlockFace,
            distance: Int
    ) {
        if (!target.isCrust) return
        if (distance > maxDistance) return

        // break
        Bukkit.getScheduler().runTaskLater(
                Gigantic.PLUGIN, {
            if (!player.isValid) return@runTaskLater
            breakLinedBlock(player, target.getRelative(linedBlockFace), false, linedBlockFace, distance + 1)
        }, 1L)

        castToBlock(player, target)
        if (!isBaseBlock)
            breakBlock(player, target, false, false)
    }

    companion object {

        const val maxDepth = SpellParameters.AQUA_LINEA_MAX_DEPTH

        const val maxCount = SpellParameters.AQUA_LINEA_MAX_COUNT

        const val maxDistance = SpellParameters.AQUA_LINEA_MAX_DISTANCE
    }
}

