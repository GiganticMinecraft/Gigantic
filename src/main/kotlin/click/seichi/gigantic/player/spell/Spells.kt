package click.seichi.gigantic.player.spell

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.animation.animations.SpellAnimations
import click.seichi.gigantic.breaker.spells.Apostol
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.config.Config
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.player.Invokable
import click.seichi.gigantic.popup.pops.PopUpParameters
import click.seichi.gigantic.popup.pops.SpellPops
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
                SpellPops.STELLA_CLAIR(wrappedAmount).pop(block.centralLocation.add(0.0, PopUpParameters.STELLA_CLAIR_SKILL_DIFF, 0.0))
                SpellSounds.STELLA_CLAIR.play(block.centralLocation)

                PlayerMessages.MANA_DISPLAY(p.mana, p.maxMana).sendTo(p)

            }
        }
    }

    val APOSTOL = object : Invokable {
        override fun findInvokable(player: Player): Consumer<Player>? {
            if (!player.hasMana(BigDecimal.ZERO)) return null
            if (player.getOrPut(Keys.SPELL_APOSTOL_BREAK_AREA).calcBreakNum() <= 1) return null
            val base = player.getOrPut(Keys.BREAK_BLOCK) ?: return null
            val breakBlockSet = Apostol.calcBreakBlockSet(player, base)
            if (breakBlockSet.isEmpty()) return null
            player.offer(Keys.SPELL_APOSTOL_BREAK_BLOCKS, breakBlockSet)
            return Consumer { p ->
                val b = player.getOrPut(Keys.BREAK_BLOCK) ?: return@Consumer
                Apostol().cast(p, b)
            }
        }
    }


    val SKY_WALK = object : Invokable {
        override fun findInvokable(player: Player): Consumer<Player>? {
            if (!player.hasMana(BigDecimal.ZERO)) return null
            return Consumer { p ->
                // 前回設置したガラスブロック
                val prevSet = p.getOrPut(Keys.SPELL_SKY_WALK_PLACE_BLOCKS)

                // もし続行不可能なら以前のガラスブロックを削除しておく
                if (p.gameMode != GameMode.SURVIVAL ||
                        p.isSneaking ||
                        !p.getOrPut(Keys.SPELL_SKY_WALK_TOGGLE)) {
                    Gigantic.SKILLED_BLOCK_SET.removeAll(prevSet)
                    prevSet.forEach { block ->
                        block.type = Material.AIR
                        block.setTorchIfNeeded()
                    }
                    p.offer(Keys.SPELL_SKY_WALK_PLACE_BLOCKS, setOf())
                    return@Consumer
                }

                // 周囲3ブロックの落とし穴
                val placeBlockSet = calcPlaceBlockSet(p)
                // 以前の不要なブロックを削除
                prevSet.filterNot {
                    placeBlockSet.contains(it)
                }.apply {
                    Gigantic.SKILLED_BLOCK_SET.removeAll(this)
                }.forEach { block ->
                    block.type = Material.AIR
                    block.setTorchIfNeeded()
                }

                // 足場設置
                placeBlockSet.filterNot {
                    prevSet.contains(it)
                }.forEach { block ->
                    block.type = Material.GLASS
                    p.playSound(block.centralLocation, Sound.BLOCK_GLASS_PLACE, SoundCategory.BLOCKS, 0.2F, 0.5F)
                }
                // ガラスブロックを保存
                p.offer(Keys.SPELL_SKY_WALK_PLACE_BLOCKS, placeBlockSet)
                // globalでも保存
                Gigantic.SKILLED_BLOCK_SET.addAll(placeBlockSet)
            }
        }

        fun calcPlaceBlockSet(player: Player): Set<Block> {
            val prevSet = player.getOrPut(Keys.SPELL_SKY_WALK_PLACE_BLOCKS)
            val pLocBlock = player.location.block

            val base = when {
                !player.isOnGround && prevSet.isNotEmpty() -> player.world.getBlockAt(pLocBlock.x, prevSet.first().y, pLocBlock.z)
                else -> pLocBlock.getRelative(BlockFace.DOWN)
            } ?: return setOf()

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
                    .filterNot { it.isWater || it.isLava }
                    .filterNot { Gigantic.SKILLED_BLOCK_SET.contains(it) }
                    .toMutableSet().apply {
                        addAll(additiveSet)
                    }.toSet()
        }
    }

}