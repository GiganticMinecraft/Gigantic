package click.seichi.gigantic.breaker

import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.animation.animations.SkillAnimations
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.player.skill.Skill
import click.seichi.gigantic.player.spell.Spell
import click.seichi.gigantic.sound.sounds.PlayerSounds
import click.seichi.gigantic.sound.sounds.SkillSounds
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

        player.updateLevel()

        // play sounds
        val mineBurst = player.getOrPut(Keys.SKILL_MINE_BURST)
        if (Achievement.SKILL_MINE_COMBO.isGranted(player)) {
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
        // スキル間干渉のないもの
        Skill.MINE_COMBO.tryCast(player)
        // スキル間干渉のあるもの
        when {
            Skill.HEAL.tryCast(player) -> {
            }
            Spell.STELLA_CLAIR.tryCast(player) -> {
            }
            !player.getOrPut(Keys.SPELL_TOGGLE) -> {
            }
            Spell.APOSTOL.tryCast(player) -> {
            }
        }
        player.remove(Keys.BREAK_BLOCK)
    }

}