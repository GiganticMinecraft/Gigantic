package click.seichi.gigantic.player.spell

import click.seichi.gigantic.animation.SpellAnimations
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.player.Invokable
import click.seichi.gigantic.player.LockedFunction
import click.seichi.gigantic.player.breaker.spells.AquaLinea
import click.seichi.gigantic.player.breaker.spells.IgnisVolcano
import click.seichi.gigantic.player.breaker.spells.TerraDrain
import click.seichi.gigantic.popup.PopUpParameters
import click.seichi.gigantic.popup.SpellPops
import click.seichi.gigantic.sound.sounds.SpellSounds
import click.seichi.gigantic.util.Random
import org.bukkit.entity.Player
import java.util.function.Consumer

/**
 * @author tar0ss
 */
object Spells {

    // 読み:ステラ・クレア
    val STELLA_CLAIR = object : Invokable {
        override fun findInvokable(player: Player): Consumer<Player>? {
            if (!LockedFunction.STELLA_CLAIR.isUnlocked(player)) return null
            if (SpellParameters.STELLA_CLAIR_PROBABILITY_PERCENT < Random.nextInt(100)) return null
            val mana = player.find(CatalogPlayerCache.MANA) ?: return null
            if (mana.isMaxMana()) return null
            return Consumer { p ->
                val block = player.remove(Keys.STELLA_CLAIR_SKILL_BLOCK) ?: return@Consumer
                p.manipulate(CatalogPlayerCache.MANA) {
                    val wrappedAmount = it.increase(it.max.div(100.toBigDecimal()).times(SpellParameters.STELLA_CLAIR_AMOUNT_PERCENT.toBigDecimal()))
                    SpellAnimations.STELLA_CLAIR.absorb(p, block.centralLocation)
                    SpellPops.STELLA_CLAIR(wrappedAmount).pop(block.centralLocation.add(0.0, PopUpParameters.STELLA_CLAIR_SKILL_DIFF, 0.0))
                    PlayerMessages.MANA_DISPLAY(it).sendTo(p)
                    SpellSounds.STELLA_CLAIR.play(block.centralLocation)
                }
            }
        }

    }

    val TERRA_DRAIN = object : Invokable {

        override fun findInvokable(player: Player): Consumer<Player>? {
            if (!LockedFunction.TERRA_DRAIN.isUnlocked(player)) return null
            if (player.isSneaking) return null
            val block = player.getOrPut(Keys.TERRA_DRAIN_SKILL_BLOCK) ?: return null
            if (!block.isLog) return null
            var canSpell = true
            player.manipulate(CatalogPlayerCache.MANA) {
                if (it.current <= 0.toBigDecimal()) {
                    canSpell = false
                    PlayerMessages.MANA_DISPLAY(it).sendTo(player)
                }
            }
            if (!canSpell) return null
            return Consumer { p ->
                val b = player.remove(Keys.TERRA_DRAIN_SKILL_BLOCK) ?: return@Consumer
                TerraDrain().cast(p, b)
            }
        }

    }

    val IGNIS_VOLCANO = object : Invokable {

        override fun findInvokable(player: Player): Consumer<Player>? {
            if (!LockedFunction.IGNIS_VOLCANO.isUnlocked(player)) return null
            if (player.isSneaking) return null
            val block = player.getOrPut(Keys.IGNIS_VOLCANO_SKILL_BLOCK) ?: return null
            if (!SpellParameters.IGNIS_VOLCANO_RELATIONAL_BLOCKS.contains(block.type)) return null
            var canSpell = true
            player.manipulate(CatalogPlayerCache.MANA) {
                if (it.current <= 0.toBigDecimal()) {
                    canSpell = false
                    PlayerMessages.MANA_DISPLAY(it).sendTo(player)
                }
            }
            if (!canSpell) return null
            return Consumer { p ->
                val b = player.remove(Keys.IGNIS_VOLCANO_SKILL_BLOCK) ?: return@Consumer
                IgnisVolcano().cast(p, b)
            }
        }

    }


    // アクアリネーア
    val AQUA_LINEA = object : Invokable {

        override fun findInvokable(player: Player): Consumer<Player>? {
            if (!LockedFunction.AQUA_LINEA.isUnlocked(player)) return null
            if (player.isSneaking) return null
            val block = player.getOrPut(Keys.AQUA_LINEA_SKILL_BLOCK) ?: return null
            if (!block.isCrust) return null
            var canSpell = true
            player.manipulate(CatalogPlayerCache.MANA) {
                if (it.current <= 0.toBigDecimal()) {
                    canSpell = false
                    PlayerMessages.MANA_DISPLAY(it).sendTo(player)
                }
            }
            if (!canSpell) return null
            return Consumer { p ->
                val b = player.remove(Keys.AQUA_LINEA_SKILL_BLOCK) ?: return@Consumer
                AquaLinea().cast(p, b)
            }
        }

    }
}