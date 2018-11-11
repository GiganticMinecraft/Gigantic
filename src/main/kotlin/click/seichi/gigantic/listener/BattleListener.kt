package click.seichi.gigantic.listener

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.centralLocation
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.sound.sounds.MonsterSpiritSounds
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
        if (locations.remove(block.location)) {
            MonsterSpiritSounds.DEFENCE.play(block.centralLocation)
        }

    }

}