package click.seichi.gigantic.player.spell

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
                // 周囲3ブロックの落とし穴
                val placeBlockSet = calcPlaceBlockSet(player)
                // 以前の不要なブロックを削除
                prevSet.filterNot {
                    placeBlockSet.contains(it)
                }.forEach { block ->
                    block.type = Material.AIR
                }
                // 足場設置
                placeBlockSet.filterNot {
                    prevSet.contains(it)
                }.forEach { block ->
                    block.type = Material.GLASS
                    p.playSound(block.centralLocation, Sound.BLOCK_GLASS_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F)
                }
                // ガラスブロックを保存
                p.offer(Keys.SPELL_SKY_WALK_PLACE_BLOCKS, placeBlockSet)
            }
        }

        fun calcPlaceBlockSet(player: Player): Set<Block> {
            val base = player.location.block.getRelative(BlockFace.DOWN)
            val columnSet = mutableSetOf<Block>(base)
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
            return allSet.filter { it.isPassable || it.isAir }
                    .toSet()
        }
    }

}