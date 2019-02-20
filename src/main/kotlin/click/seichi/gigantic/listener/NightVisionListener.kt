package click.seichi.gigantic.listener

import click.seichi.gigantic.event.events.TickEvent
import click.seichi.gigantic.extension.isDay
import click.seichi.gigantic.player.ToggleSetting
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

/**
 * @author tar0ss
 */
class NightVisionListener : Listener {

    // 確認する間隔(秒)
    private val INTERVAL = 5L
    // 付与するポーションエフェクトの秒数
    private val DURATION = 30

    @EventHandler
    fun onTick(event: TickEvent) {
        if (event.ticks % 20L.times(INTERVAL) != 0L) return
        Bukkit.getServer().onlinePlayers
                .asSequence()
                .filterNotNull()
                .filter { it.isValid }
                .forEach { player ->
                    if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                        player.removePotionEffect(PotionEffectType.NIGHT_VISION)
                    }
                    if (ToggleSetting.NIGHT_VISION.getToggle(player) && !player.world.isDay) {
                        player.addPotionEffect(PotionEffect(PotionEffectType.NIGHT_VISION, 20 * DURATION, 5, false, false, false))
                    }
                }
    }
}