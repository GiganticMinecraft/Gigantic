package click.seichi.gigantic.player.skill

import click.seichi.gigantic.animation.animations.SkillAnimations
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.player.Invokable
import click.seichi.gigantic.popup.pops.PopUpParameters
import click.seichi.gigantic.popup.pops.SkillPops
import click.seichi.gigantic.sound.sounds.SkillSounds
import click.seichi.gigantic.util.Random
import org.bukkit.Material
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.function.Consumer

/**
 * @author tar0ss
 */
object Skills {

    val MINE_BURST = object : Invokable {

        val duration = SkillParameters.MINE_BURST_DURATION
        val coolTime = SkillParameters.MINE_BURST_COOLTIME

        override fun findInvokable(player: Player): Consumer<Player>? {
            val mineBurst = player.find(CatalogPlayerCache.MINE_BURST) ?: return null
            if (!mineBurst.canStart()) return null
            return Consumer { p ->
                mineBurst.coolTime = coolTime
                mineBurst.duration = duration
                mineBurst.onStart {
                    p.removePotionEffect(PotionEffectType.FAST_DIGGING)
                    p.addPotionEffect(PotionEffect(PotionEffectType.FAST_DIGGING, 100, 2, true, false))
                    SkillSounds.MINE_BURST_ON_FIRE.play(player.eyeLocation)
                    p.updateBelt(true, false)
                }.onFire {
                    p.updateBelt(false, false)
                }.onCompleteFire {
                    p.updateBelt(true, false)
                }.onCooldown {
                    p.updateBelt(false, false)
                }.onCompleteCooldown {
                    p.updateBelt(true, false)
                }.start()
            }
        }
    }

    val FLASH = object : Invokable {

        val transparentMaterialSet = setOf(
                Material.AIR,
                Material.CAVE_AIR,
                Material.VOID_AIR,
                Material.WATER,
                Material.LAVA
        )

        val maxDistance = 50

        val coolTime = SkillParameters.FLASH_COOLTIME

        override fun findInvokable(player: Player): Consumer<Player>? {
            val flash = player.find(CatalogPlayerCache.FLASH) ?: return null
            if (!flash.canStart()) return null
            return Consumer { p ->
                flash.coolTime = coolTime
                flash.onStart {
                    val tpLocation = p.getTargetBlock(transparentMaterialSet, maxDistance).let { block ->
                        if (block.type == Material.AIR) return@let null
                        var nextBlock = block ?: return@let null
                        while (!nextBlock.isSurface) {
                            nextBlock = nextBlock.getRelative(BlockFace.UP)
                        }
                        nextBlock.centralLocation.add(0.0, 1.75, 0.0).apply {
                            direction = p.location.direction
                        }
                    }
                    if (tpLocation != null) {
                        SkillAnimations.FLASH_FIRE.start(p.location)
                        p.teleport(tpLocation)
                        SkillAnimations.FLASH_FIRE.start(p.location)
                        SkillSounds.FLASH_FIRE.play(p.location)
                    } else {
                        SkillSounds.FLASH_MISS.play(p.location)
                        flash.isCancelled = true
                    }
                    p.updateBelt(true, false)
                }.onCooldown {
                    p.updateBelt(false, false)
                }.onCompleteCooldown {
                    p.updateBelt(true, false)
                }.start()
            }
        }

    }

    val HEAL = object : Invokable {
        override fun findInvokable(player: Player): Consumer<Player>? {
            if (SkillParameters.HEAL_PROBABILITY_PERCENT < Random.nextInt(100)) return null
            val health = player.find(CatalogPlayerCache.HEALTH) ?: return null
            if (health.isMaxHealth()) return null
            return Consumer { p ->
                val block = player.getOrPut(Keys.BREAK_BLOCK) ?: return@Consumer
                p.manipulate(CatalogPlayerCache.HEALTH) {
                    val wrappedAmount = it.increase(it.max.div(100L).times(SkillParameters.HEAL_AMOUNT_PERCENT))
                    SkillAnimations.HEAL.start(p.location.clone().add(0.0, 1.7, 0.0))
                    SkillPops.HEAL(wrappedAmount).pop(block.centralLocation.add(0.0, PopUpParameters.HEAL_SKILL_DIFF, 0.0))
                    PlayerMessages.HEALTH_DISPLAY(it).sendTo(p)
                }
            }
        }

    }

}