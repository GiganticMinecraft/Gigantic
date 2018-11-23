package click.seichi.gigantic.breaker.skills

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.animation.animations.SkillAnimations
import click.seichi.gigantic.breaker.Cutter
import click.seichi.gigantic.breaker.SkillCaster
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.player.skill.SkillParameters
import click.seichi.gigantic.popup.pops.SkillPops
import click.seichi.gigantic.sound.sounds.SkillSounds
import org.bukkit.Bukkit
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class KodamaDrain : Cutter(), SkillCaster {

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

    override fun cast(player: Player, base: Block) {
        SkillAnimations.KODAMA_DRAIN_ON_FIRE.start(base.centralLocation)
        SkillSounds.KODAMA_DRAIN_ON_FIRE.play(player.location)
        breakRelationalBlock(player, base, true)
    }

    override fun onCastToBlock(player: Player, block: Block) {
        SkillAnimations.KODAMA_DRAIN_ON_BREAK.start(block.centralLocation)
        SkillSounds.KODAMA_DRAIN_ON_BREAK.play(block.centralLocation)
        player.manipulate(CatalogPlayerCache.HEALTH) {
            if (it.isMaxHealth()) return@manipulate
            val percent = when {
                block.isLog -> SkillParameters.KODAMA_DRAIN_LOG_HEAL_PERCENT
                block.isLeaves -> SkillParameters.KODAMA_DRAIN_LEAVES_HEAL_PERCENT
                else -> 0.0
            }
            val wrappedAmount = it.increase(it.max.div(100.0).times(percent).toLong())
            if (wrappedAmount > 0) {
                // pop
                SkillPops.KODAMA_DRAIN(wrappedAmount).pop(block.centralLocation)
                // display
                PlayerMessages.HEALTH_DISPLAY(it).sendTo(player)
            }
        }
    }

    private fun breakRelationalBlock(player: Player, target: Block, isBaseBlock: Boolean) {
        if (!target.isTree) return

        // 原木でなければ処理しない
        if (target.isLog) {

            // 破壊ブロック段を処理
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
            // 破壊ブロック段の上段を処理
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

            // 破壊ブロック段の下段を処理
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

        // ブロックへのキャスト処理
        castToBlock(player, target)

        // ベースブロックで無ければ通常破壊処理
        if (!isBaseBlock)
            breakBlock(player, target, false, false)
    }

    override fun onBreakBlock(player: Player, block: Block) {
        // Gravity process
        block.fallUpperCrustBlock()
        // Remove Liquid process
        block.removeUpperLiquidBlock()
        // bedrock process
        block.changeRelativeBedrock()
    }

}