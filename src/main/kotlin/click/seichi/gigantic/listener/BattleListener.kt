package click.seichi.gigantic.listener

import click.seichi.gigantic.extension.findBattle
import click.seichi.gigantic.extension.isCrust
import click.seichi.gigantic.message.messages.BattleMessages
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.monster.SoulMonster
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
    fun defence(event: PlayerInteractEvent) {
        val block = event.clickedBlock ?: return
        val player = event.player ?: return
        val battle = player.findBattle() ?: return
        battle.tryDefence(player, block)
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun cancelOtherPlayersBattleChunk(event: BlockBreakEvent) {
        val block = event.block ?: return
        val player = event.player ?: return
        val battle = block.findBattle() ?: return
        if (battle.isJoined(player) || battle.spawner == player) return
        event.isCancelled = true
        PlayerMessages.BATTLE_ANOTHER_PLAYER.sendTo(player)
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun cancelNotBattleChunk(event: BlockBreakEvent) {
        val block = event.block ?: return
        val player = event.player ?: return
        val battle = player.findBattle() ?: return
        if (battle.chunk == block.chunk) return
        event.isCancelled = true
        PlayerMessages.BREAK_OTHER_CHUNK.sendTo(player)
        if (!SoulMonster.ZOMBIE_VILLAGER.isDefeatedBy(player)) {
            BattleMessages.FIRST_BREAK_OTHER_CHUNK.sendTo(player)
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun attack(event: BlockBreakEvent) {
        val block = event.block ?: return
        val player = event.player ?: return
        if (!block.isCrust) return
        val battle = player.findBattle() ?: return
        if (battle.chunk == block.chunk)
            battle.tryAttack(player, block)
    }

}