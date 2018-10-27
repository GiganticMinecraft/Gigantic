package click.seichi.gigantic.player.breaker.skills

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.animation.SpellAnimations
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.player.breaker.Cutter
import click.seichi.gigantic.player.breaker.RelationalBreaker
import click.seichi.gigantic.player.spell.SpellParameters
import click.seichi.gigantic.popup.SpellPops
import click.seichi.gigantic.sound.sounds.SpellSounds
import org.bukkit.Bukkit
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class TerraDrain : Cutter(), RelationalBreaker {

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

    override fun breakRelations(player: Player, block: Block) {
        SpellAnimations.TERRA_DRAIN_ON_FIRE.start(block.centralLocation)
        SpellSounds.TERRA_DRAIN_ON_FIRE.play(player.location)
        breakRelationalBlock(player, block, true)
    }

    private fun breakRelationalBlock(player: Player, target: Block, isBaseBlock: Boolean) {
//        player.server.consoleSender.sendMessage("${target.type} ${target.x} ${target.y} ${target.z} ")
        if (!target.isTree) return

        // 原木でなければ処理しない
        if (target.isLog) {
            relationalFaceSet.map {
                Bukkit.getScheduler().runTaskLater(
                        Gigantic.PLUGIN,
                        {
                            if (!player.isValid) return@runTaskLater
                            breakRelationalBlock(player, target.getRelative(it), false)
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
                            breakRelationalBlock(player, upperBlock.getRelative(it), false)
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
                        breakRelationalBlock(player, upperBlock, false)
                    },
                    9L
            )
            val underBlock = target.getRelative(BlockFace.DOWN)
            relationalFaceSet.map {
                Bukkit.getScheduler().runTaskLater(
                        Gigantic.PLUGIN,
                        {
                            if (!player.isValid) return@runTaskLater
                            breakRelationalBlock(player, underBlock.getRelative(it), false)
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
                        breakRelationalBlock(player, underBlock, false)
                    },
                    9L
            )
        }
        onSpellBreak(player, target)
        if (!isBaseBlock)
            breakBlock(player, target, false, false)
    }

    private fun onSpellBreak(player: Player, block: Block) {
        SpellAnimations.TERRA_DRAIN_ON_BREAK.start(block.centralLocation)
        SpellSounds.TERRA_DRAIN_ON_BREAK.play(block.centralLocation)
        player.manipulate(CatalogPlayerCache.HEALTH) {
            if (it.isMaxHealth()) return@manipulate
            val percent = when {
                block.isLog -> SpellParameters.TERRA_DRAIN_LOG_HEAL_PERCENT
                block.isLeaves -> SpellParameters.TERRA_DRAIN_LEAVES_HEAL_PERCENT
                else -> 0.0
            }
            val wrappedAmount = it.increase(it.max.div(100.0).times(percent).toLong())
            if (wrappedAmount > 0) {
                SpellPops.HEAL(wrappedAmount).pop(block.centralLocation)
                PlayerMessages.HEALTH_DISPLAY(it).sendTo(player)
            }
        }
    }

}