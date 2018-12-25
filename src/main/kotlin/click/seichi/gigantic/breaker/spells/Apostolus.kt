package click.seichi.gigantic.breaker.spells

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.breaker.Miner
import click.seichi.gigantic.breaker.SpellCaster
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.*
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
        // この時点で既に破壊ブロックが存在しない可能性あり
        // 破壊可能ブロック取得
        val breakBlockSet = calcBreakBlockSet(player, base)

        // TODO remove
        player.sendMessage("${player.calcBreakFace()}")
        breakBlockSet.forEach { it.breakNaturally(player.inventory.itemInMainHand) }
        // 必要なマナを計算

        // onBreak処理（先にやっておくことで，途中でサーバーが終了したときに対応）
        // すべてのエフェクトの実行速度に影響を与えないようにする．

        // 一旦破壊するブロック全てを保存（保存場所未定）

        // 実際の破壊処理（エフェクト部分）

        // 正常に実行された場合は保存しておいたブロックをすべて削除

        //終了

        // TODO implements
//        SpellAnimations.GRAND_NATURA_ON_FIRE.start(base.centralLocation)
//        SpellSounds.GRAND_NATURA_ON_FIRE.play(player.location)
        //breakRelationalBlock(player, base, base, true, 0)
    }

    private fun calcBreakBlockSet(player: Player, base: Block): Set<Block> {
        // プレイヤーの向いている方向を取得
        val breakFace = player.calcBreakFace()
        // プレイヤーが選択している破壊範囲を取得
        val breakArea = player.getOrPut(Keys.APOSTOLUS_BREAK_AREA)

        val allBlockSet = mutableSetOf<Block>()

        // 注意：破壊する数によって破壊範囲が変動する
        // 破壊する方向，破壊範囲から破壊可能ブロックセットを計算
        when {
            breakFace == BlockFace.NORTH ||
                    breakFace == BlockFace.SOUTH ||
                    breakFace == BlockFace.WEST ||
                    breakFace == BlockFace.EAST -> {
                // breakFaceの上下左右方向にブロックを取得，その後breakFace方向に奥行だけブロックを取得
                // 上下ブロック
                val columnBlockSet = mutableSetOf<Block>()

                columnBlockSet.add(base)

                // 上はheight - 2まで(>=3が保証されていると仮定する)
                if (breakArea.height >= 3) {
                    (1..(breakArea.height - 2)).forEach {
                        columnBlockSet.add(base.getRelative(BlockFace.UP, it))
                    }
                }

                // 下は1ブロック
                if (breakArea.height > 1) {
                    columnBlockSet.add(base.getRelative(BlockFace.DOWN))
                }

                // プレイヤーの正面に当たるブロックセット
                val facingBlockSet = mutableSetOf<Block>()

                facingBlockSet.addAll(columnBlockSet)

                // 左右に振る
                columnBlockSet.forEach { columnBase ->
                    (1..breakArea.width.minus(1).div(2)).forEach {
                        facingBlockSet.add(columnBase.getRelative(breakFace.leftFace, it))
                        facingBlockSet.add(columnBase.getRelative(breakFace.rightFace, it))
                    }
                }

                allBlockSet.addAll(facingBlockSet)

                // 奥行分を含めて全てのブロックセットを取得
                facingBlockSet.forEach { faceBase ->
                    (1..breakArea.depth.minus(1)).map {
                        allBlockSet.add(faceBase.getRelative(breakFace, it))
                    }
                }
            }
            breakFace == BlockFace.UP ||
                    breakFace == BlockFace.DOWN -> {

                // breakFaceの上下左右方向にブロックを取得，その後breakFace方向に奥行だけブロックを取得
                // 上下ブロック
                val columnBlockSet = mutableSetOf<Block>()

                columnBlockSet.add(base)

                (1..breakArea.width.minus(1).div(2)).forEach {
                    columnBlockSet.add(base.getRelative(BlockFace.NORTH, it))
                    columnBlockSet.add(base.getRelative(BlockFace.SOUTH, it))
                }

                // プレイヤーの正面に当たるブロックセット
                val facingBlockSet = mutableSetOf<Block>()

                facingBlockSet.addAll(columnBlockSet)

                // 左右に振る
                columnBlockSet.forEach { columnBase ->
                    (1..breakArea.width.minus(1).div(2)).forEach {
                        facingBlockSet.add(columnBase.getRelative(BlockFace.WEST, it))
                        facingBlockSet.add(columnBase.getRelative(BlockFace.EAST, it))
                    }
                }

                allBlockSet.addAll(facingBlockSet)

                // 奥行分を含めて全てのブロックセットを取得
                facingBlockSet.forEach { faceBase ->
                    (1..breakArea.height.minus(1)).map {
                        allBlockSet.add(faceBase.getRelative(breakFace, it))
                    }
                }
            }

        }

        // TODO 破壊可能条件はこれだけじゃないはず，保護とかも考える
        return allBlockSet.filter {
            it.isCrust && it != base
        }.toSet()
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