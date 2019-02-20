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

    fun breakRelationalBlock(target: Block, isBaseBlock: Boolean) {
        if (!target.isTree) return

        // 原木でなければ処理しない
        if (target.isLog) {
            // 破壊ブロック段を処理
            relationalFaceSet.map {
                Bukkit.getScheduler().scheduleSyncDelayedTask(
                        Gigantic.PLUGIN,
                        {
                            breakRelationalBlock(target.getRelative(it), false)
                        },
                        when (it) {
                            BlockFace.NORTH -> 1L
                            BlockFace.NORTH_EAST -> 2L
                            BlockFace.EAST -> 1L
                            BlockFace.SOUTH_EAST -> 2L
                            BlockFace.SOUTH -> 1L
                            BlockFace.SOUTH_WEST -> 2L
                            BlockFace.WEST -> 1L
                            BlockFace.NORTH_WEST -> 2L
                            else -> throw error("cutter error code:001")
                        }
                )
            }
            // 破壊ブロック段の上段を処理
            val upperBlock = target.getRelative(BlockFace.UP)
            relationalFaceSet.map {
                Bukkit.getScheduler().scheduleSyncDelayedTask(
                        Gigantic.PLUGIN,
                        {
                            breakRelationalBlock(upperBlock.getRelative(it), false)
                        },
                        when (it) {
                            BlockFace.NORTH -> 1L + 2L
                            BlockFace.NORTH_EAST -> 2L + 2L
                            BlockFace.EAST -> 1L + 2L
                            BlockFace.SOUTH_EAST -> 2L + 2L
                            BlockFace.SOUTH -> 1L + 2L
                            BlockFace.SOUTH_WEST -> 2L + 2L
                            BlockFace.WEST -> 1L + 2L
                            BlockFace.NORTH_WEST -> 1L + 2L
                            else -> throw error("cutter error code:002")
                        }
                )
            }
            Bukkit.getScheduler().scheduleSyncDelayedTask(
                    Gigantic.PLUGIN,
                    {
                        breakRelationalBlock(upperBlock, false)
                    },
                    2L
            )

            // 破壊ブロック段の下段を処理
            val underBlock = target.getRelative(BlockFace.DOWN)
            relationalFaceSet.map {
                Bukkit.getScheduler().scheduleSyncDelayedTask(
                        Gigantic.PLUGIN,
                        {
                            breakRelationalBlock(underBlock.getRelative(it), false)
                        },
                        when (it) {
                            BlockFace.NORTH -> 1L + 2L
                            BlockFace.NORTH_EAST -> 2L + 2L
                            BlockFace.EAST -> 1L + 2L
                            BlockFace.SOUTH_EAST -> 2L + 2L
                            BlockFace.SOUTH -> 1L + 2L
                            BlockFace.SOUTH_WEST -> 2L + 2L
                            BlockFace.WEST -> 1L + 2L
                            BlockFace.NORTH_WEST -> 2L + 2L
                            else -> throw error("cutter error code:003")
                        }
                )
            }
            Bukkit.getScheduler().scheduleSyncDelayedTask(
                    Gigantic.PLUGIN,
                    {
                        breakRelationalBlock(underBlock, false)
                    },
                    2L
            )
        }

        // ベースブロックで無ければ通常破壊処理
        if (!isBaseBlock) {
            onBreakBlock(null, target)
            breakBlock(target)
        }
    }

    override fun onBreakBlock(player: Player?, block: Block) {
        PlayerAnimations.ON_CUT.start(block.centralLocation)
        PlayerSounds.ON_CUT.play(block.centralLocation)
        block.update()
        // 松明をセット
        block.setTorchIfNeeded(player)

    }

}