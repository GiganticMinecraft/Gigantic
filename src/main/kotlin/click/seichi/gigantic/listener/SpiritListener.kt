package click.seichi.gigantic.listener

import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.extension.summonSpirit
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDeathEvent

/**
 * @unicroak
 * @author tar0ss
 */
class SpiritListener : Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    fun onBlockBreak(event: BlockBreakEvent) {
        val player = event.player ?: return
        if (player.gameMode != GameMode.SURVIVAL) return
        if (!Achievement.WILL_BASIC_1.isGranted(player)) return
        event.summonSpirit()
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onEntityDeath(event: EntityDeathEvent) {
        val player = event.entity.killer ?: return
        if (player.gameMode != GameMode.SURVIVAL) return
        if (!Achievement.WILL_BASIC_1.isGranted(player)) return
        event.summonSpirit()
    }

}