package click.seichi.gigantic.listener

import click.seichi.gigantic.extension.findBattle
import click.seichi.gigantic.message.messages.PlayerMessages
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerInteractEvent

/**
 * @author tar0ss
 */
class BattleListener : Listener {

    @EventHandler
    fun defence(event: PlayerInteractEvent) {
        val block = event.clickedBlock ?: return
        val player = event.player ?: return
        val battle = player.findBattle() ?: return
        battle.tryDefence(player, block)
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun cancelIfAnotherBattleChunk(event: BlockBreakEvent) {
        val block = event.block ?: return
        val player = event.player ?: return
        val battle = block.findBattle() ?: return
        if (battle.isJoined(player) || battle.spawner == player) return
        event.isCancelled = true
        PlayerMessages.BATTLE.sendTo(player)
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun attack(event: BlockBreakEvent) {
        if (event.isCancelled) return
        val block = event.block ?: return
        val player = event.player ?: return
        val battle = player.findBattle() ?: return
        if (!battle.isStarted) return
        if (battle.chunk == block.chunk)
            battle.tryAttack(player, block)
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun lose(event: PlayerDeathEvent) {
        val player = event.entity ?: return

    }

}