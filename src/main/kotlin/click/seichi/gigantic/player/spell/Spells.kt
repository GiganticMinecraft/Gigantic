package click.seichi.gigantic.player.spell

import click.seichi.gigantic.animation.animations.SpellAnimations
import click.seichi.gigantic.breaker.spells.Apostol
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.config.Config
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.message.messages.PopUpMessages
import click.seichi.gigantic.player.Invokable
import click.seichi.gigantic.popup.LongAnimation
import click.seichi.gigantic.popup.PopUp
import click.seichi.gigantic.sound.sounds.SpellSounds
import click.seichi.gigantic.util.NoiseData
import click.seichi.gigantic.util.Random
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
                PopUp(LongAnimation, block.centralLocation.noised(NoiseData(sizeY = 0.2)), PopUpMessages.STELLA_CLAIR(wrappedAmount))
                        .pop()
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

}