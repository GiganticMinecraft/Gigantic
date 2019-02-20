package click.seichi.gigantic.listener

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.animation.animations.SkillAnimations
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.player.Defaults
import click.seichi.gigantic.sound.sounds.PlayerSounds
import click.seichi.gigantic.util.Random
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.scheduler.BukkitRunnable

/**
 * @author tar0ss
 */
class SkillListener : Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onBlockBreak(event: BlockBreakEvent) {
        if (event.player.gameMode != GameMode.SURVIVAL) return

        if (Random.nextDouble() > Defaults.PIECE_PROBABILITY) return

        val player = event.player ?: return
        val block = event.block ?: return

        val totem = player.getOrPut(Keys.TOTEM)

        if (totem > 0) return

        val piece = player.getOrPut(Keys.TOTEM_PIECE)

        SkillAnimations.TOTEM_PIECE.absorb(player, block.centralLocation)

        if (piece + 1 >= Defaults.MAX_TOTEM_PIECE) {
            player.transform(Keys.TOTEM) { it.plus(1) }
            player.offer(Keys.TOTEM_PIECE, 0)

            // 取得時の音
            object : BukkitRunnable() {
                override fun run() {
                    PlayerSounds.NOTICE.playOnly(player)
                }
            }.runTaskLater(Gigantic.PLUGIN, SkillAnimations.TOTEM_PIECE.ticks)

        } else {
            player.transform(Keys.TOTEM_PIECE) { it.plus(1) }
            // 取得時の音
            object : BukkitRunnable() {
                override fun run() {
                    PlayerSounds.PICK_UP.playOnly(player)
                }
            }.runTaskLater(Gigantic.PLUGIN, SkillAnimations.TOTEM_PIECE.ticks)
        }
        player.updateBelt(applyMainHand = false, applyOffHand = false)
    }
}