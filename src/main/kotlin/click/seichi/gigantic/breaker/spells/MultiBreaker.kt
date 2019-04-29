package click.seichi.gigantic.breaker.spells

import click.seichi.gigantic.breaker.SpellCaster
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.config.Config
import click.seichi.gigantic.config.DebugConfig
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.player.Defaults
import click.seichi.gigantic.player.ExpReason
import click.seichi.gigantic.relic.Relic
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * @author tar0ss
 */
class MultiBreaker : SpellCaster {

    companion object {
        // 最大同時破壊数 = maxMana * (5/100) / 2
        fun calcLimitOfBreakNumOfMultiBreak(maxMana: BigDecimal): Int {
            return maxMana
                    .divide(100.toBigDecimal(), RoundingMode.HALF_UP)
                    .times(5.toBigDecimal())
                    .divide(Config.SPELL_MULTI_BREAK_MANA_PER_BLOCK.toBigDecimal(), 10, RoundingMode.HALF_UP)
                    .coerceAtMost(Math.pow(Config.SPELL_MULTI_BREAK_LIMIT_SIZE.toDouble(), 3.0).toBigDecimal())
                    .toInt()
        }

        fun calcBreakBlockSet(player: Player, base: Block): Set<Block> {
            // プレイヤーの向いている方向を取得
            val breakFace = player.calcFace()
            // プレイヤーが選択している破壊範囲を取得
            val breakArea = player.getOrPut(Keys.SPELL_MULTI_BREAK_AREA)

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
                            // スニークしていないかつbaseの高さがプレイヤーより1ブロック低い時
                            if (!player.isSneaking && player.location.blockY == base.y) {
                                columnBlockSet.add(base.getRelative(BlockFace.UP, breakArea.height - 1))
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

                    val rotFace = player.calcFace(true)

                    // breakFaceの上下左右方向にブロックを取得，その後breakFace方向に高さだけブロックを取得
                    // 上下ブロック
                    val columnBlockSet = mutableSetOf<Block>()

                    columnBlockSet.add(base)

                    (1..breakArea.width.minus(1).div(2)).forEach {
                        columnBlockSet.add(base.getRelative(rotFace.leftFace, it))
                        columnBlockSet.add(base.getRelative(rotFace.rightFace, it))
                    }

                    // プレイヤーの正面に当たるブロックセット
                    val facingBlockSet = mutableSetOf<Block>()

                    facingBlockSet.addAll(columnBlockSet)

                    // 左右に振る
                    columnBlockSet.forEach { columnBase ->
                        (1..breakArea.depth.minus(1).div(2)).forEach {
                            facingBlockSet.add(columnBase.getRelative(rotFace, it))
                            facingBlockSet.add(columnBase.getRelative(rotFace.oppositeFace, it))
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

            val minY = allBlockSet.minBy { it.y }?.y ?: 0
            val maxY = allBlockSet.maxBy { it.y }?.y ?: 0
            val height = maxY - minY

            return allBlockSet.filter {
                // 因果の制約
                it != base
            }.filter {
                // 場所の制約
                !it.isSpawnArea
            }.filter {
                // 高さの制約
                it.y > 0
            }.filter {
                // 重力値の制約
                it.calcCrustGravity(height - (it.y - minY)) <= Config.MAX_BREAKABLE_GRAVITY
            }.toMutableSet().apply {
                // 場所の制約
                if (!player.isSneaking) {
                    removeIf { it.isUnder(player) }
                }

                // プレイヤーと近い場合は除外
                removeIf { it.firstOrNullOfNearPlayer(player) != null }

                // 場所の制約
                val battle = player.findBattle()
                removeIf { it.findBattle() != battle }

                // 先にブロックを変換
                forEach {
                    it.changeBedrock()
                    it.condenseLiquid(false)
                    it.condenseSkyWalkBlock(false)
                }
            }.filter {
                // 種類の制約
                it.isCrust
            }.toSet()
        }
    }

    override fun cast(player: Player, base: Block) {
        // 破壊可能ブロック取得
        val breakBlockSet = player.remove(Keys.SPELL_MULTI_BREAK_BLOCKS)

        val breakArea = player.getOrPut(Keys.SPELL_MULTI_BREAK_AREA)

        if (breakBlockSet == null || breakBlockSet.isEmpty()) return

        // onBreak処理（先にやっておくことで，途中でサーバーが終了したときに対応）
        // すべてのエフェクトの実行速度に影響を与えないようにする．

        if (!Config.DEBUG_MODE || !DebugConfig.SPELL_INFINITY) {
            player.manipulate(CatalogPlayerCache.MANA) {
                it.decrease(calcConsumeMana(player, breakBlockSet))
            }
        }

        val stripCount = breakBlockSet
                .count { block -> block.y == 1 && block.calcGravity(breakArea.height - 1) == 0 }

        val relicBonus = breakBlockSet.fold(0.0) { source, b ->
            source.plus(Relic.calcMultiplier(player, b))
        }

        player.manipulate(CatalogPlayerCache.EXP) {
            it.add(breakBlockSet.size.toBigDecimal(), ExpReason.SPELL_MULTI_BREAK)
            it.add(relicBonus.toBigDecimal(), reason = ExpReason.RELIC_BONUS)
            it.add(stripCount.times(Defaults.STRIP_BONUS).toBigDecimal(), reason = ExpReason.STRIP_MINE_BONUS)
        }

        player.transform(Keys.BREAK_COUNT) { it + breakBlockSet.size }
        player.transform(Keys.RELIC_BONUS) { it + relicBonus }
        player.transform(Keys.STRIP_COUNT) { it + stripCount }

        PlayerMessages.MANA_DISPLAY(player.mana, player.maxMana).sendTo(player)

        // 実際の破壊処理（エフェクト部分）
        player.getOrPut(Keys.EFFECT).multiBreak(player, base, breakBlockSet)
    }


    override fun calcConsumeMana(player: Player, breakBlockSet: Set<Block>): BigDecimal {
        return Config.SPELL_MULTI_BREAK_MANA_PER_BLOCK.toBigDecimal() * breakBlockSet.size.toBigDecimal()
    }

}