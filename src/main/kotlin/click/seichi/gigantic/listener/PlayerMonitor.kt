package click.seichi.gigantic.listener

import click.seichi.gigantic.event.events.LevelUpEvent
import click.seichi.gigantic.extension.central
import click.seichi.gigantic.extension.centralLocation
import click.seichi.gigantic.extension.gPlayer
import click.seichi.gigantic.language.messages.PlayerMessages
import click.seichi.gigantic.player.ExpProducer
import click.seichi.gigantic.sound.PlayerSounds
import click.seichi.gigantic.sound.SkillSounds
import org.bukkit.Bukkit
import org.bukkit.Particle
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

/**
 * @author tar0ss
 */
class PlayerMonitor : Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    fun onBlockBreak(event: BlockBreakEvent) {
        if (event.isCancelled) return
        val player = event.player ?: return
        val gPlayer = player.gPlayer ?: return

        gPlayer.mineBlock.add(1L)
        gPlayer.mineCombo.combo(1L)
        gPlayer.mineCombo.display(event.block.centralLocation.add(0.0, 0.0, 0.0))

        val level = player.gPlayer?.level ?: return
        val isLevelUp = level.updateLevel(ExpProducer.calcExp(player)) {
            Bukkit.getPluginManager().callEvent(LevelUpEvent(it, player))
        }

        PlayerMessages.LEVEL_DISPLAY(level).sendTo(player)

        val location = event.block.location.central

        if (gPlayer.mineBurst.duringFire()) {
            location.world.spawnParticle(Particle.ENCHANTMENT_TABLE, location, 10)
        }

        when {
            isLevelUp -> PlayerSounds.LEVEL_UP.play(player.location)
            gPlayer.mineBurst.duringFire() -> SkillSounds.MINE_BURST_ON_BREAK.play(location)
            else -> PlayerSounds.OBTAIN_EXP.play(player)
        }
    }


}