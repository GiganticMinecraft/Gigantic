package click.seichi.gigantic.skill

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.find
import click.seichi.gigantic.player.LockedFunction
import click.seichi.gigantic.sound.sounds.SkillSounds
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
            get() = SkillParameters.MINE_BURST_DURATION
        override val coolTime: Long
            get() = SkillParameters.MINE_BURST_COOLTIME

        override fun findInvokable(player: Player): Consumer<Player>? {
            if (!LockedFunction.MINE_BURST.isUnlocked(player)) return null
            val mineBurst = player.find(CatalogPlayerCache.MINE_BURST) ?: return null
            if (!mineBurst.canStart()) return null
            return Consumer { player ->
                mineBurst.onStart {
                    player.removePotionEffect(PotionEffectType.FAST_DIGGING)
                    player.addPotionEffect(PotionEffect(PotionEffectType.FAST_DIGGING, 100, 2, true, false))
                    SkillSounds.MINE_BURST_ON_FIRE.play(player.location)
                }.onStart {
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

}