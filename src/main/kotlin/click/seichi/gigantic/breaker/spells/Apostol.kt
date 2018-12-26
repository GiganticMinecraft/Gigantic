package click.seichi.gigantic.breaker.spells

import click.seichi.gigantic.breaker.Miner
import click.seichi.gigantic.breaker.SpellCaster
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.player.spell.SpellParameters
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import java.math.BigDecimal

/**
 * @author tar0ss
 */
class Apostol : Miner(), SpellCaster {

    override fun cast(player: Player, base: Block) {
        // この時点で既に破壊ブロックが存在しない可能性あり
        // 破壊可能ブロック取得
        val breakBlockSet = calcBreakBlockSet(player, base)

        // onBreak処理（先にやっておくことで，途中でサーバーが終了したときに対応）
        // すべてのエフェクトの実行速度に影響を与えないようにする．

        player.manipulate(CatalogPlayerCache.MANA) {
            it.decrease(calcConsumeMana(player, breakBlockSet))
        }

        PlayerMessages.MANA_DISPLAY(player.mana, player.maxMana).sendTo(player)

        // 実際の破壊処理（エフェクト部分）
        breakBlockSet.run {
            forEach { target ->
                breakBlock(player, target, false, false)
            }
            // 凍結，火成等の処理を最後にまとめる
            forEach { target ->
                onBreakBlock(player, target)
            }
        }
    }

    private fun calcBreakBlockSet(player: Player, base: Block): Set<Block> {
        // プレイヤーの向いている方向を取得
        val breakFace = player.calcBreakFace()
        // プレイヤーが選択している破壊範囲を取得
        val breakArea = player.getOrPut(Keys.APOSTOLUS_BREAK_AREA)

        val allBlockSet = mutableSetOf<Block>()

        // 注意：破壊する数によって破壊範囲が変動する
        // 破壊する方向，破壊範囲から破壊可能ブロックセットを計算
        when (breakFace) {
            BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST, BlockFace.EAST -> {
                // breakFaceの上下左右方向にブロックを取得，その後breakFace方向に奥行だけブロックを取得
                // 上下ブロック
                val columnBlockSet = mutableSetOf<Block>()

                columnBlockSet.add(base)

                // 高さ２の場合は上側のみ
                if (breakArea.height == 2) {
                    // 上のみ
                    columnBlockSet.add(base.getRelative(BlockFace.UP))

                } else {
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
            BlockFace.UP, BlockFace.DOWN -> {

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
            else -> {
            }
        }

        return allBlockSet.filter {
            // 種類の制約
            it.isCrust || it.isLiquid
        }.filter {
            // 因果の制約
            it != base
        }.filter {
            // 場所の制約
            !it.isSpawnArea
        }.toMutableSet().apply {
            // 場所の制約
            if (!player.isSneaking) {
                removeIf { it.isUnder(player) }
            }
            // 場所の制約
            val battle = player.findBattle()
            removeIf { it.findBattle() != battle }
        }
    }

    override fun calcConsumeMana(player: Player, breakBlockSet: Set<Block>): BigDecimal {
        return SpellParameters.APOSTOL_MANA.toBigDecimal() * breakBlockSet.size.toBigDecimal()
    }

    override fun onBreakBlock(player: Player, block: Block) {
        block.update()
    }

}