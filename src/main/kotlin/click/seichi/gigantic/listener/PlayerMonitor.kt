package click.seichi.gigantic.listener

import click.seichi.gigantic.extension.gPlayer
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
        gPlayer.level.update(player)
    }
}