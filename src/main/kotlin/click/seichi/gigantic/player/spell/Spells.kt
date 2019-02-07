package click.seichi.gigantic.player.spell

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.animation.animations.SpellAnimations
import click.seichi.gigantic.breaker.spells.MultiBreaker
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.config.Config
import click.seichi.gigantic.config.DebugConfig
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.message.messages.PopUpMessages
import click.seichi.gigantic.player.Defaults
import click.seichi.gigantic.player.Invokable
import click.seichi.gigantic.popup.PopUp
import click.seichi.gigantic.popup.SimpleAnimation
import click.seichi.gigantic.sound.sounds.SpellSounds
import click.seichi.gigantic.util.Random
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.function.Consumer

/**
 * @author tar0ss
 */
object Spells {

    // 読み:ステラ・クレア
    val STELLA_CLAIR = object : Invokable {
        override fun findInvokable(player: Player): Consumer<Player>? {
            player.getOrPut(Keys.BREAK_BLOCK) ?: return null
            if (Config.SPELL_STELLA_CLAIR_PROBABILITY < Random.nextInt(100)) return null
            if (player.mana >= player.maxMana) return null
            return Consumer { p ->
                val block = player.getOrPut(Keys.BREAK_BLOCK) ?: return@Consumer
                var wrappedAmount = 0.toBigDecimal()

                p.manipulate(CatalogPlayerCache.MANA) {
                    wrappedAmount = it.increase(it.max.divide(100.toBigDecimal(), 10, RoundingMode.HALF_UP).times(Config.SPELL_STELLA_CLAIR_RATIO.toBigDecimal()))
                }

                SpellAnimations.STELLA_CLAIR.absorb(p, block.centralLocation)
                PopUp(SimpleAnimation, block.centralLocation.add(0.0, 0.2, 0.0), PopUpMessages.STELLA_CLAIR(wrappedAmount))
                        .pop()
                SpellSounds.STELLA_CLAIR.play(block.centralLocation)

                PlayerMessages.MANA_DISPLAY(p.mana, p.maxMana).sendTo(p)

            }
        }
    }

    val MULTI_BREAK = object : Invokable {
        override fun findInvokable(player: Player): Consumer<Player>? {
            if (!player.hasMana(BigDecimal.ZERO)) return null
            if (player.getOrPut(Keys.SPELL_MULTI_BREAK_AREA).calcBreakNum() <= 1) return null
            val base = player.getOrPut(Keys.BREAK_BLOCK) ?: return null
            val breakBlockSet = MultiBreaker.calcBreakBlockSet(player, base)
            if (breakBlockSet.isEmpty()) return null
            player.offer(Keys.SPELL_MULTI_BREAK_BLOCKS, breakBlockSet)
            return Consumer { p ->
                val b = player.getOrPut(Keys.BREAK_BLOCK) ?: return@Consumer
                MultiBreaker().cast(p, b)
            }
        }
    }


    val SKY_WALK = object : Invokable {
        override fun findInvokable(player: Player): Consumer<Player>? {
            return Consumer { p ->
                // 前回設置したガラスブロック
                val prevSet = p.getOrPut(Keys.SPELL_SKY_WALK_PLACE_BLOCKS)
                // もし続行不可能なら以前のガラスブロックを削除しておく
                if (p.gameMode != GameMode.SURVIVAL ||
                        p.isSneaking ||
                        !p.getOrPut(Keys.SPELL_SKY_WALK_TOGGLE) ||
                        !player.hasMana(BigDecimal.ZERO)) {
                    Gigantic.SKILLED_BLOCK_SET.removeAll(prevSet)
                    prevSet.forEach { block ->
                        revert(block)
                    }
                    p.offer(Keys.SPELL_SKY_WALK_PLACE_BLOCKS, setOf())
                    return@Consumer
                }

                val placeBlockSet = calcPlaceBlockSet(p)
                // 以前の不要なブロックを削除
                prevSet.filterNot {
                    placeBlockSet.contains(it)
                }.apply {
                    Gigantic.SKILLED_BLOCK_SET.removeAll(this)
                }.forEach { block ->
                    revert(block)
                }

                // 足場設置
                placeBlockSet.filterNot {
                    prevSet.contains(it)
                }.apply {
                    if (size == 0) return@apply
                    val consumeMana = calcConsumeMana(size)
                    if (!Config.DEBUG_MODE || !DebugConfig.SPELL_INFINITY) {
                        p.manipulate(CatalogPlayerCache.MANA) {
                            it.decrease(consumeMana)
                        }
                    }
                    if (consumeMana > BigDecimal.ZERO) {
                        PlayerMessages.MANA_DISPLAY(p.mana, p.maxMana).sendTo(p)
                    }
                    forEach { block ->
                        replace(block)
                    }
                }
                // 設置ブロックを保存
                p.offer(Keys.SPELL_SKY_WALK_PLACE_BLOCKS, placeBlockSet)
                // globalでも保存
                Gigantic.SKILLED_BLOCK_SET.addAll(placeBlockSet)

            }
        }

        fun revert(block: Block) {
            block.type = when (block.type) {
                Defaults.SKY_WALK_WATER_MATERIAL -> Material.WATER
                Defaults.SKY_WALK_LAVA_MATERIAL -> Material.LAVA
                else -> Material.AIR
            }
            block.setTorchIfNeeded()
        }

        fun replace(block: Block) {
            block.type = when {
                block.isWater -> Defaults.SKY_WALK_WATER_MATERIAL
                block.isLava -> Defaults.SKY_WALK_LAVA_MATERIAL
                else -> Defaults.SKY_WALK_AIR_MATERIAL
            }
            block.world.playSound(block.centralLocation, Sound.BLOCK_GLASS_PLACE, SoundCategory.BLOCKS, 0.2F, 0.5F)
        }

        fun calcConsumeMana(num: Int) = num.times(Config.SPELL_SKY_WALK_MANA_PER_BLOCK)
                .toBigDecimal()

        fun calcPlaceBlockSet(player: Player): Set<Block> {
            val prevSet = player.getOrPut(Keys.SPELL_SKY_WALK_PLACE_BLOCKS)
            val pLocBlock = player.location.block
            val base = pLocBlock.getRelative(BlockFace.DOWN) ?: return setOf()

            val columnSet = mutableSetOf(base)
            (1..Config.SPELL_SKY_WALK_RADIUS).forEach { length ->
                columnSet.add(base.getRelative(BlockFace.NORTH, length))
                columnSet.add(base.getRelative(BlockFace.SOUTH, length))
            }
            val allSet = mutableSetOf(*columnSet.toTypedArray())
            (1..Config.SPELL_SKY_WALK_RADIUS).forEach { length ->
                columnSet.forEach { columnBlock ->
                    allSet.add(columnBlock.getRelative(BlockFace.EAST, length))
                    allSet.add(columnBlock.getRelative(BlockFace.WEST, length))
                }
            }
            val additiveSet = prevSet.filter { allSet.contains(it) }.toSet()
            return allSet.filter { it.isPassable || it.isAir }
                    // 水と溶岩も固めるために除外
//                    .filterNot { it.isWater || it.isLava }
                    .filterNot { it.isSpawnArea }
                    .filterNot { it.y == 0 }
                    .filterNot { Gigantic.SKILLED_BLOCK_SET.contains(it) }
                    .toMutableSet().apply {
                        addAll(additiveSet)
                    }.toSet()
        }
    }

    val LUNA_FLEX = object : Invokable {
        override fun findInvokable(player: Player): Consumer<Player>? {
            return Consumer { p ->

            }
        }
    }

}