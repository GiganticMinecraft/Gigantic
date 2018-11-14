package click.seichi.gigantic.listener

import click.seichi.gigantic.extension.findBattle
import click.seichi.gigantic.message.messages.PlayerMessages
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerInteractEvent

/**
 * @author tar0ss
 */
class BattleListener : Listener {

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        val block = event.clickedBlock ?: return
        val player = event.player ?: return
        val battle = player.findBattle() ?: return
        battle.tryDefence(player, block)
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onBlockBreak(event: BlockBreakEvent) {
        val block = event.block ?: return
        val player = event.player ?: return
        val battle = block.findBattle() ?: return
        if (battle.isJoined(player)) return
        event.isCancelled = true
        PlayerMessages.BATTLE.sendTo(player)

    }

}