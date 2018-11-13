package click.seichi.gigantic.listener

import click.seichi.gigantic.battle.BattleManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

/**
 * @author tar0ss
 */
class BattleListener : Listener {

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        val block = event.clickedBlock ?: return
        val player = event.player ?: return
        val battle = BattleManager.findBattle(player) ?: return
        battle.tryDefence(player, block)


    }

}