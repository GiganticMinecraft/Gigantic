package click.seichi.gigantic.listener

import click.seichi.gigantic.extension.gPlayer
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

/**
 * @author tar0ss
 */
class PlayerMonitor:Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    fun onBlockBreak(event:BlockBreakEvent){
        if(event.isCancelled)return
        val gPlayer = event.player.gPlayer ?: return
        gPlayer.status.mineBlock.add(1L)
        gPlayer.status.seichiLevel.update(gPlayer)
    }
}