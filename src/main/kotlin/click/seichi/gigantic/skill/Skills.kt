package click.seichi.gigantic.skill

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.find
import click.seichi.gigantic.extension.isSurface
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

    val MINE_BURST = object : LingeringSkill {
        override val duration: Long
            get() = 5L
        override val coolTime: Long
            get() = 60L

        override fun findInvokable(player: Player): Consumer<Player>? {
            if (!LockedFunction.MINE_BURST.isUnlocked(player)) return null
            val mineBurst = player.find(CatalogPlayerCache.MINE_BURST) ?: return null
            if (!mineBurst.canStart()) return null
            return Consumer { player ->
                mineBurst.onStart {
                    player.removePotionEffect(PotionEffectType.FAST_DIGGING)
                    player.addPotionEffect(PotionEffect(PotionEffectType.FAST_DIGGING, 100, 2, true, false))
                    SkillSounds.MINE_BURST_ON_FIRE.play(player.location)
                    player.find(Keys.BELT)?.wear(player)
                }.onFire {
                    player.find(Keys.BELT)?.wear(player, false)
                }.onCompleteFire {
                    player.find(Keys.BELT)?.wear(player)
                }.onCooldown {
                    player.find(Keys.BELT)?.wear(player, false)
                }.onCompleteCooldown {
                    player.find(Keys.BELT)?.wear(player)
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

        override val coolTime: Long
            get() = 5L

        override fun findInvokable(player: Player): Consumer<Player>? {
            if (!LockedFunction.FLASH.isUnlocked(player)) return null
            val flash = player.find(CatalogPlayerCache.FLASH) ?: return null
            if (!flash.canStart()) return null
            return Consumer { player ->
                flash.onStart {
                    val tpLocation = player.getTargetBlock(transparentMaterialSet, maxDistance).let { block ->
                        if (block.type == Material.AIR) return@let null
                        var nextBlock = block ?: return@let null
                        while (!nextBlock.isSurface) {
                            nextBlock = nextBlock.getRelative(BlockFace.UP)
                        }
                        nextBlock.location.clone().add(0.0, 1.75, 0.0)
                    }
                    if (tpLocation != null) {
                        player.teleport(tpLocation)
                        SkillSounds.FLASH_FIRE.play(player.location)
                    } else {
                        SkillSounds.FLASH_MISS.play(player.location)
                        flash.isCancelled = true
                    }
                    player.find(Keys.BELT)?.wear(player)
                }.onCooldown {
                    player.find(Keys.BELT)?.wear(player, false)
                }.onCompleteCooldown {
                    player.find(Keys.BELT)?.wear(player)
                }.start()
            }
        }

    }

}