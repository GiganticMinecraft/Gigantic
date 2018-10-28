package click.seichi.gigantic.player.breaker.spells

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.animation.SpellAnimations
import click.seichi.gigantic.extension.centralLocation
import click.seichi.gigantic.extension.isGrass
import click.seichi.gigantic.player.breaker.Miner
import click.seichi.gigantic.player.breaker.RelationalBreaker
import click.seichi.gigantic.player.spell.SpellParameters
import click.seichi.gigantic.sound.sounds.SpellSounds
import org.bukkit.Bukkit
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player

/**
 *
 * @author tar0ss
 */
class IgnisVolcano : Miner(), RelationalBreaker {

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

    private fun canBreakRelations(block: Block) = relationalMaterials.contains(block.type)

    override fun breakRelations(player: Player, block: Block) {
        SpellAnimations.IGNIS_VOLCANO_ON_FIRE.start(block.centralLocation)
        SpellSounds.IGNIS_VOLCANO_ON_FIRE.play(player.location)
        breakRelationalBlock(player, block, block, true)
    }

    private fun breakRelationalBlock(player: Player, base: Block, target: Block, isBaseBlock: Boolean) {
//        player.server.consoleSender.sendMessage("${target.type} ${target.x} ${target.y} ${target.z} ")
        if (!target.isGrass) return
        if (Math.abs(target.location.x - base.location.x) >= maxRadius
                || Math.abs(target.location.z - base.location.z) >= maxRadius) return

        // 芝生でなければ処理しない
        if (canBreakRelations(target)) {
            relationalFaceSet.map {
                Bukkit.getScheduler().runTaskLater(
                        Gigantic.PLUGIN,
                        {
                            if (!player.isValid) return@runTaskLater
                            breakRelationalBlock(player, base, target.getRelative(it), false)
                        },
                        when (it) {
                            BlockFace.NORTH -> 1L
                            BlockFace.NORTH_EAST -> 3L
                            BlockFace.EAST -> 5L
                            BlockFace.SOUTH_EAST -> 7L
                            BlockFace.SOUTH -> 2L
                            BlockFace.SOUTH_WEST -> 4L
                            BlockFace.WEST -> 6L
                            BlockFace.NORTH_WEST -> 8L
                            else -> throw error("terra drain error code:001")
                        }
                )
            }
            val upperBlock = target.getRelative(BlockFace.UP)
            relationalFaceSet.map {
                Bukkit.getScheduler().runTaskLater(
                        Gigantic.PLUGIN,
                        {
                            if (!player.isValid) return@runTaskLater
                            breakRelationalBlock(player, base, upperBlock.getRelative(it), false)
                        },
                        when (it) {
                            BlockFace.NORTH -> 1L + 9L
                            BlockFace.NORTH_EAST -> 3L + 9L
                            BlockFace.EAST -> 5L + 9L
                            BlockFace.SOUTH_EAST -> 7L + 9L
                            BlockFace.SOUTH -> 2L + 9L
                            BlockFace.SOUTH_WEST -> 4L + 9L
                            BlockFace.WEST -> 6L + 9L
                            BlockFace.NORTH_WEST -> 8L + 9L
                            else -> throw error("terra drain error code:002")
                        }
                )
            }
            Bukkit.getScheduler().runTaskLater(
                    Gigantic.PLUGIN,
                    {
                        if (!player.isValid) return@runTaskLater
                        breakRelationalBlock(player, base, upperBlock, false)
                    },
                    9L
            )
            val underBlock = target.getRelative(BlockFace.DOWN)
            relationalFaceSet.map {
                Bukkit.getScheduler().runTaskLater(
                        Gigantic.PLUGIN,
                        {
                            if (!player.isValid) return@runTaskLater
                            breakRelationalBlock(player, base, underBlock.getRelative(it), false)
                        },
                        when (it) {
                            BlockFace.NORTH -> 1L + 9L
                            BlockFace.NORTH_EAST -> 3L + 9L
                            BlockFace.EAST -> 5L + 9L
                            BlockFace.SOUTH_EAST -> 7L + 9L
                            BlockFace.SOUTH -> 2L + 9L
                            BlockFace.SOUTH_WEST -> 4L + 9L
                            BlockFace.WEST -> 6L + 9L
                            BlockFace.NORTH_WEST -> 8L + 9L
                            else -> throw error("terra drain error code:003")
                        }
                )
            }
            Bukkit.getScheduler().runTaskLater(
                    Gigantic.PLUGIN,
                    {
                        if (!player.isValid) return@runTaskLater
                        breakRelationalBlock(player, base, underBlock, false)
                    },
                    9L
            )
        }
        onSpellBreak(target)
        if (!isBaseBlock)
            breakBlock(player, target, false, false)
    }

    private fun onSpellBreak(block: Block) {
        SpellAnimations.IGNIS_VOLCANO_ON_BREAK.start(block.centralLocation)
        SpellSounds.IGNIS_VOLCANO_ON_BREAK.play(block.centralLocation)
    }

    companion object {

        val relationalMaterials = SpellParameters.IGNIS_VOLCANO_RELATIONAL_BLOCKS

        const val maxRadius = SpellParameters.IGNIS_VOLCANO_MAX_RADIUS
    }


}