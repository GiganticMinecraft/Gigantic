package click.seichi.gigantic.listener

import click.seichi.gigantic.animation.SkillAnimations
import click.seichi.gigantic.event.events.LevelUpEvent
import click.seichi.gigantic.extension.central
import click.seichi.gigantic.extension.centralLocation
import click.seichi.gigantic.extension.gPlayer
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.player.ExpProducer
import click.seichi.gigantic.popup.SkillPops
import click.seichi.gigantic.sound.sounds.PlayerSounds
import click.seichi.gigantic.sound.sounds.SkillSounds
import org.bukkit.Bukkit
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
        SkillPops.MINE_COMBO(gPlayer.mineCombo).pop(event.block.centralLocation.add(0.0, 0.0, 0.0))

        val level = player.gPlayer?.level ?: return
        val isLevelUp = level.updateLevel(ExpProducer.calcExp(player)) {
            Bukkit.getPluginManager().callEvent(LevelUpEvent(it, player))
        }

        PlayerMessages.LEVEL_DISPLAY(level).sendTo(player)

        val location = event.block.location.central

        if (gPlayer.mineBurst.duringFire()) {
            SkillAnimations.MINE_BURST_ON_BREAK.start(location)
        }

        when {
            isLevelUp -> PlayerSounds.LEVEL_UP.play(location)
            gPlayer.mineBurst.duringFire() -> SkillSounds.MINE_BURST_ON_BREAK.play(location)
            else -> PlayerSounds.OBTAIN_EXP.playOnly(player)
        }
    }


}