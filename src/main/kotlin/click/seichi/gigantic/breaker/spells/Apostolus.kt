package click.seichi.gigantic.breaker.spells

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.breaker.Miner
import click.seichi.gigantic.breaker.SpellCaster
import click.seichi.gigantic.extension.isCrust
import click.seichi.gigantic.extension.update
import click.seichi.gigantic.player.spell.SpellParameters
import org.bukkit.Bukkit
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import java.math.BigDecimal

/**
 * @author tar0ss
 */
class Apostolus : Miner(), SpellCaster {

    private val relationalFaceSet = setOf(
            BlockFace.NORTH,
            BlockFace.NORTH_EAST,
            BlockFace.EAST,
            BlockFace.SOUTH_EAST,
            BlockFace.SOUTH,
            BlockFace.SOUTH_WEST,
            BlockFace.WEST,
            BlockFace.NORTH_WEST
    )

    val maxDepth = 1

    override fun cast(player: Player, base: Block) {
        // TODO implements
//        SpellAnimations.GRAND_NATURA_ON_FIRE.start(base.centralLocation)
//        SpellSounds.GRAND_NATURA_ON_FIRE.play(player.location)
        breakRelationalBlock(player, base, base, true, 0)
    }

    override fun calcConsumeMana(player: Player, block: Block): BigDecimal {
        return SpellParameters.APOSTOLUS_MANA.toBigDecimal()
    }

    override fun onCastToBlock(player: Player, block: Block) {
        // TODO implements
//        SpellAnimations.GRAND_NATURA_ON_BREAK.start(block.centralLocation)
//        SpellSounds.GRAND_NATURA_ON_BREAK.play(block.centralLocation)
    }

    private fun breakRelationalBlock(player: Player, base: Block, target: Block, isBaseBlock: Boolean, depth: Int) {
        if (!target.isCrust) return
        if (depth > maxDepth) return

        relationalFaceSet.map {
            Bukkit.getScheduler().scheduleSyncDelayedTask(
                    Gigantic.PLUGIN,
                    {
                        if (!player.isValid) return@scheduleSyncDelayedTask
                        breakRelationalBlock(player, base, target.getRelative(it), false, depth + 1)
                    },
                    when (it) {
                        BlockFace.NORTH -> 1L
                        BlockFace.NORTH_EAST -> 1L
                        BlockFace.EAST -> 1L
                        BlockFace.SOUTH_EAST -> 1L
                        BlockFace.SOUTH -> 1L
                        BlockFace.SOUTH_WEST -> 1L
                        BlockFace.WEST -> 1L
                        BlockFace.NORTH_WEST -> 1L
                        else -> throw error("explosion error code:001")
                    }
            )
        }
        val upperBlock = target.getRelative(BlockFace.UP)
        relationalFaceSet.map {
            Bukkit.getScheduler().scheduleSyncDelayedTask(
                    Gigantic.PLUGIN,
                    {
                        if (!player.isValid) return@scheduleSyncDelayedTask
                        breakRelationalBlock(player, base, upperBlock.getRelative(it), false, depth + 1)
                    },
                    when (it) {
                        BlockFace.NORTH -> 1L
                        BlockFace.NORTH_EAST -> 1L
                        BlockFace.EAST -> 1L
                        BlockFace.SOUTH_EAST -> 1L
                        BlockFace.SOUTH -> 1L
                        BlockFace.SOUTH_WEST -> 1L
                        BlockFace.WEST -> 1L
                        BlockFace.NORTH_WEST -> 1L
                        else -> throw error("explosion error code:002")
                    }
            )
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(
                Gigantic.PLUGIN,
                {
                    if (!player.isValid) return@scheduleSyncDelayedTask
                    breakRelationalBlock(player, base, upperBlock, false, depth + 1)
                },
                1L
        )
        val underBlock = target.getRelative(BlockFace.DOWN)
        relationalFaceSet.map {
            Bukkit.getScheduler().scheduleSyncDelayedTask(
                    Gigantic.PLUGIN,
                    {
                        if (!player.isValid) return@scheduleSyncDelayedTask
                        breakRelationalBlock(player, base, underBlock.getRelative(it), false, depth + 1)
                    },
                    when (it) {
                        BlockFace.NORTH -> 1L
                        BlockFace.NORTH_EAST -> 1L
                        BlockFace.EAST -> 1L
                        BlockFace.SOUTH_EAST -> 1L
                        BlockFace.SOUTH -> 1L
                        BlockFace.SOUTH_WEST -> 1L
                        BlockFace.WEST -> 1L
                        BlockFace.NORTH_WEST -> 1L
                        else -> throw error("explosion error code:003")
                    }
            )
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(
                Gigantic.PLUGIN,
                {
                    if (!player.isValid) return@scheduleSyncDelayedTask
                    breakRelationalBlock(player, base, underBlock, false, depth + 1)
                },
                1L
        )
        if (!isBaseBlock) {
            castToBlock(player, target)
            breakBlock(player, target, false, false)
        }
    }

    override fun onBreakBlock(player: Player, block: Block) {
        block.update()
    }

}