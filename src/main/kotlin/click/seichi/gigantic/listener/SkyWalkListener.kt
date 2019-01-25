package click.seichi.gigantic.listener

import click.seichi.gigantic.event.events.TickEvent
import click.seichi.gigantic.player.spell.Spell
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

/**
 * @author tar0ss
 */
class SkyWalkListener : Listener {

    @EventHandler
    fun onTick(event: TickEvent) {
        Bukkit.getOnlinePlayers()
                .asSequence()
                .filterNotNull()
                .filter { it.isValid }
                .filter { it.gameMode == GameMode.SURVIVAL }
                .forEach { player ->
                    Spell.SKY_WALK.tryCast(player)
                }
    }

}