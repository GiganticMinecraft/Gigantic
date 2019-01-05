package click.seichi.gigantic.listener

import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.animation.animations.ElytraAnimations
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.config.Config
import click.seichi.gigantic.event.events.TickEvent
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.offer
import click.seichi.gigantic.item.items.HandItems
import click.seichi.gigantic.player.Defaults
import click.seichi.gigantic.sound.sounds.ElytraSounds
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerToggleSneakEvent
import org.bukkit.util.Vector

/**
 * @author tar0ss
 */
class ElytraListener : Listener {

    private val speed = Config.ELYTRA_SPEED_MULTIPLIER.times(Defaults.ELYTRA_BASE_SPEED)
    private val launchStrength = Config.ELYTRA_LAUNCH_MULTIPLIER.times(Defaults.ELYTRA_BASE_LAUNCH)
    private val chargeUpTime = Config.ELYTRA_CHARGE_UP_TIME

    @EventHandler
    fun onTick(event: TickEvent) {
        Bukkit.getOnlinePlayers()
                .filterNotNull()
                .filter { it.isValid }
                .filter { it.gameMode == GameMode.SURVIVAL }
                .filter { it.isOnGround }
                .filter { it.getOrPut(Keys.ELYTRA_CHARGE_UP_TICKS) >= 0L }
                .forEach { player ->
                    var ticks = player.getOrPut(Keys.ELYTRA_CHARGE_UP_TICKS)
                    ticks++
                    // チャージ処理
                    player.offer(Keys.ELYTRA_CHARGE_UP_TICKS, ticks)

                    ElytraAnimations.CHARGING.start(player.location)
                    if (ticks % 3L == 0L) {
                        ElytraSounds.CHARGING.play(player.location)
                        if (ticks >= chargeUpTime) {
                            ElytraAnimations.READY_TO_JUMP.start(player.location)
                            ElytraSounds.READY_TO_JUMP.play(player.location)
                        }
                    }
                }
    }

    @EventHandler(ignoreCancelled = true)
    fun onMove(event: PlayerMoveEvent) {
        val player = event.player ?: return
        if (!player.isGliding) return
        // 飛行中の加速
        val unitVector = Vector(0.0, player.location.direction.y, 0.0)
        player.velocity = player.velocity.add(unitVector.multiply(speed))
    }

    @EventHandler(ignoreCancelled = true)
    fun onToggleSneak(event: PlayerToggleSneakEvent) {
        val player = event.player ?: return
        if (!player.isOnGround) return
        val slot = player.inventory.heldItemSlot
        if (player.getOrPut(Keys.BELT).findItem(slot) != HandItems.JUMP) return
        if (!Achievement.JUMP.isGranted(player)) return

        // チャージ開始
        if (event.isSneaking) {
            player.offer(Keys.ELYTRA_CHARGE_UP_TICKS, 0L)
        } else {
            if (player.getOrPut(Keys.ELYTRA_CHARGE_UP_TICKS) >= chargeUpTime) {
                // 発射
                val dir = player.location.direction.add(Vector(0.0, launchStrength, 0.0))

                player.velocity = player.velocity.add(dir)

                ElytraAnimations.JUMP.start(player.location)
                ElytraSounds.JUMP.play(player.location)
            }
            player.offer(Keys.ELYTRA_CHARGE_UP_TICKS, -1L)
        }

    }

}