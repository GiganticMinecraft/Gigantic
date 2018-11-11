package click.seichi.gigantic.listener

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.getOrPut
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
        val locations = player.getOrPut(Keys.ATTACK_WAIT_LOCATION_SET)
        val battle = player.getOrPut(Keys.MONSTER_SPIRIT)?.battle ?: return
        if (locations.remove(block.location)) {
            battle.defenceEnemyAttack(block)
        }

    }

}