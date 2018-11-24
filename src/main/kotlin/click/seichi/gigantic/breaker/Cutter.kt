package click.seichi.gigantic.breaker

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.animation.animations.PlayerAnimations
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.sound.sounds.PlayerSounds
import org.bukkit.Bukkit
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class Cutter : Miner() {

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

    fun breakRelationalBlock(player: Player, target: Block, isBaseBlock: Boolean) {
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
                            else -> throw error("cutter error code:001")
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
                            else -> throw error("cutter error code:002")
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
                            else -> throw error("cutter error code:003")
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

        // ベースブロックで無ければ通常破壊処理
        if (!isBaseBlock)
            breakBlock(player, target, false, false)
    }

    override fun onBreakBlock(player: Player, block: Block) {
        PlayerAnimations.ON_CUT.start(block.centralLocation)
        PlayerSounds.ON_CUT.play(block.centralLocation)
        // Gravity process
        block.fallUpperCrustBlock()
        // bedrock process
        block.changeRelativeBedrock()
        // liquid condense process
        block.condenseRelativeLiquid()
    }

}