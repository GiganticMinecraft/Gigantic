package click.seichi.gigantic.skill

import click.seichi.gigantic.animation.SkillAnimations
import click.seichi.gigantic.belt.Belt
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.find
import click.seichi.gigantic.extension.isSurface
import click.seichi.gigantic.extension.manipulate
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.player.LockedFunction
import click.seichi.gigantic.sound.sounds.SkillSounds
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

    val MINE_BURST = object : Skill {

        val duration = 5L
        val coolTime = 60L

        override fun findInvokable(player: Player): Consumer<Player>? {
            if (!LockedFunction.MINE_BURST.isUnlocked(player)) return null
            val mineBurst = player.find(CatalogPlayerCache.MINE_BURST) ?: return null
            if (!mineBurst.canStart()) return null
            return Consumer { p ->
                mineBurst.coolTime = coolTime
                mineBurst.duration = duration
                mineBurst.onStart {
                    p.removePotionEffect(PotionEffectType.FAST_DIGGING)
                    p.addPotionEffect(PotionEffect(PotionEffectType.FAST_DIGGING, 100, 2, true, false))
                    SkillSounds.MINE_BURST_ON_FIRE.play(p.location)
                    p.find(Keys.BELT)?.wear(p)
                }.onFire {
                    p.find(Keys.BELT)?.wear(p, false)
                }.onCompleteFire {
                    p.find(Keys.BELT)?.wear(p)
                }.onCooldown {
                    p.find(Keys.BELT)?.wear(p, false)
                }.onCompleteCooldown {
                    p.find(Keys.BELT)?.wear(p)
                }.start()
            }
        }
    }

    val FLASH = object : Skill {

        val transparentMaterialSet = setOf(
                Material.AIR,
                Material.WATER,
                Material.STATIONARY_WATER,
                Material.LAVA,
                Material.STATIONARY_LAVA
        )

        val maxDistance = 50

        val coolTime = 15L

        override fun findInvokable(player: Player): Consumer<Player>? {
            if (!LockedFunction.FLASH.isUnlocked(player)) return null
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
                        nextBlock.location.clone().add(0.0, 1.75, 0.0).apply {
                            direction = p.location.direction
                        }
                    }
                    if (tpLocation != null) {
                        SkillAnimations.FLASH_BEFORE.start(p.location)
                        p.teleport(tpLocation)
                        SkillAnimations.FLASH_BEFORE.start(p.location)
                        SkillSounds.FLASH_FIRE.play(p.location)
                    } else {
                        SkillSounds.FLASH_MISS.play(p.location)
                        flash.isCancelled = true
                    }
                    p.find(Keys.BELT)?.wear(p)
                }.onCooldown {
                    p.find(Keys.BELT)?.wear(p, false)
                }.onCompleteCooldown {
                    p.find(Keys.BELT)?.wear(p)
                }.start()
            }
        }

    }

    val HEAL = object : Skill {
        override fun findInvokable(player: Player): Consumer<Player>? {
            if (!LockedFunction.HEAL.isUnlocked(player)) return null
            return Consumer { p ->
                p.manipulate(CatalogPlayerCache.HEALTH) {
                    it.increase(it.max.div(100L))
                    PlayerMessages.HEALTH_DISPLAY(it).sendTo(p)
                }
            }
        }

    }

    val SWITCH = object : Skill {
        override fun findInvokable(player: Player): Consumer<Player>? {
            if (!LockedFunction.SWITCH.isUnlocked(player)) return null
            return Consumer { p ->
                var current: Belt? = null
                p.manipulate(CatalogPlayerCache.BELT_SWITCHER) {
                    current = it.current
                    it.switch()
                }
                val nextBelt = p.find(Keys.BELT) ?: return@Consumer
                if (current == nextBelt) return@Consumer
                nextBelt.wear(p)
                SkillSounds.SWITCH.playOnly(p)

            }
        }

    }

//    val EXPLOSION = object : Skill{
//
//        val coolTime = 45
//
//        override fun findInvokable(player: Player): Consumer<Player>? {
//            if (!LockedFunction.EXPLOSION.isUnlocked(player)) return null
//        }
//
//    }

}