package click.seichi.gigantic.breaker

import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.animation.animations.SkillAnimations
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.event.events.ComboEvent
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.player.skill.Skill
import click.seichi.gigantic.player.spell.Spell
import click.seichi.gigantic.popup.pops.PopUpParameters
import click.seichi.gigantic.popup.pops.SkillPops
import click.seichi.gigantic.sound.sounds.PlayerSounds
import click.seichi.gigantic.sound.sounds.SkillSounds
import org.bukkit.Bukkit
import org.bukkit.Effect
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.block.Block
import org.bukkit.entity.Player

/**
 * 通常破壊時の処理
 *
 * @author tar0ss
 */
open class Miner : Breaker {

    override fun breakBlock(player: Player, block: Block, isBroken: Boolean, showBrokenEffect: Boolean) {
        if (block.isLiquid) {
            if (isBroken) return
            block.type = Material.AIR
        } else {
            if (showBrokenEffect) {
                block.world.spawnParticle(Particle.BLOCK_CRACK, block.centralLocation, 1, block.blockData)
                block.world.playEffect(block.centralLocation, Effect.STEP_SOUND, block.type)
            }
            if (isBroken) return
            block.breakNaturally(player.inventory.itemInMainHand)
        }
    }


    open fun onBreakBlock(player: Player, block: Block) {
        block.update()

        // add exp
        if (!block.isCrust && !block.isTree) return

        player.manipulate(CatalogPlayerCache.EXP) {
            it.inc()
        }

        val currentCombo = player.combo

        if (Achievement.MINE_COMBO.isGranted(player)) {
            player.manipulate(CatalogPlayerCache.MINE_COMBO) {
                it.combo(1L)
                SkillPops.MINE_COMBO(it).pop(block.centralLocation.add(0.0, PopUpParameters.MINE_COMBO_DIFF, 0.0))
            }
        }
        if (currentCombo > player.combo) {
            ((currentCombo + 1)..player.combo).forEach { combo ->
                Bukkit.getPluginManager().callEvent(ComboEvent(combo, player))
            }
        }

        player.updateLevel()

        // play sounds
        val mineBurst = player.getOrPut(Keys.SKILL_MINE_BURST)
        if (Achievement.MINE_COMBO.isGranted(player)) {
            // Sounds
            when {
                mineBurst.duringFire() -> {
                    SkillSounds.MINE_BURST_ON_BREAK(player.combo).playOnly(player)
                    SkillAnimations.MINE_BURST_ON_BREAK.start(block.centralLocation)
                }
                else -> PlayerSounds.OBTAIN_EXP(player.combo).playOnly(player)
            }
        }

        player.offer(Keys.BREAK_BLOCK, block)
        when {
            trySkill(player) -> {
            }
            trySpell(player) -> {
            }
            tryHeal(player) -> {
            }
        }
        player.remove(Keys.BREAK_BLOCK)
    }

    private fun trySkill(player: Player): Boolean {
        return false
    }

    private fun tryHeal(player: Player): Boolean {
        if (Skill.HEAL.tryCast(player)) return true
        if (Spell.STELLA_CLAIR.tryCast(player)) return true
        return false
    }

    private fun trySpell(player: Player): Boolean {
        val toggle = player.getOrPut(Keys.SPELL_TOGGLE)
        if (!toggle) return false
        if (Spell.APOSTOL.tryCast(player)) return true

        return false
    }

}