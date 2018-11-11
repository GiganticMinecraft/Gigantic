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
        val block = event.clickedBlock
        val player = event.player
        val locations = player.getOrPut(Keys.ATTACKED_LOCATION_SET)
        if (locations.remove(block.location)) {
            MonsterSpiritSounds.DEFENCE.play(block.centralLocation)
            // TODO
//            block.world.spawnParticle(Particle.BLOCK_CRACK, block.centralLocation.add(0.0,0.5,0.0), 20, attackBlockData)

        }

    }

}