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
import org.bukkit.entity.Player
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
            if (!player.hasMana()) return null
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

    val LUNA_FLEX = object : Invokable {
        override fun findInvokable(player: Player): Consumer<Player>? {
            return Consumer { p ->
                // 以前の場所を保存しておく(毎秒必ず実行する)
                val prevLoc = p.getOrPut(Keys.PREVIOUS_LOCATION)
                p.offer(Keys.PREVIOUS_LOCATION, p.location)
                if (prevLoc == null) return@Consumer

                // サバイバルでないまたはマナがない時終了
                if (p.gameMode != GameMode.SURVIVAL ||
                        !player.hasMana()) {
                    // 移動速度補正
                    if (p.walkSpeed != Defaults.WALK_SPEED.toFloat())
                        p.walkSpeed = Defaults.WALK_SPEED.toFloat()
                    return@Consumer
                }

                //移動速度補正
                if (p.walkSpeed != p.getOrPut(Keys.WALK_SPEED).toFloat())
                    p.walkSpeed = p.getOrPut(Keys.WALK_SPEED).toFloat()

                // 段階を取得
                val degree = p.getOrPut(Keys.WALK_SPEED)
                        .minus(Defaults.WALK_SPEED)
                        .times(10.toBigDecimal())
                        .toInt()
                        .coerceIn(0..Defaults.LUNA_FLEX_MAX_DEGREE)

                // 初期速度なら終了
                if (degree <= 0) return@Consumer

                // 移動距離を算出
                val distance = p.location.distance(prevLoc)

                // 移動していない時終了
                if (distance == 0.0) return@Consumer

                // マナを計算
                val consumeMana = degree.toDouble()
                        .times(Config.SPELL_LUNA_FLEX_MANA_PER_DEGREE)
                        .times(distance)
                        .toBigDecimal()

                if (!Gigantic.IS_DEBUG || !DebugConfig.SPELL_INFINITY) {
                    p.manipulate(CatalogPlayerCache.MANA) {
                        it.decrease(consumeMana)
                    }
                }
                PlayerMessages.MANA_DISPLAY(p.mana, p.maxMana).sendTo(p)
            }
        }
    }

}