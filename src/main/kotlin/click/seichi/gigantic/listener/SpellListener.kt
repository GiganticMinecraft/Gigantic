package click.seichi.gigantic.listener

import click.seichi.gigantic.event.events.TickEvent
import click.seichi.gigantic.player.spell.Spell
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

/**
 * @author tar0ss
 */
class SpellListener : Listener {

    @EventHandler
    fun onTick(event: TickEvent) {
        Bukkit.getServer().onlinePlayers
                .asSequence()
                .filterNotNull()
                .filter { it.isValid }
                .forEach { player ->
                    Spell.SKY_WALK.tryCast(player)
                    // 毎秒発火
                    if (event.ticks % 20L == 0L) {
                        Spell.LUNA_FLEX.tryCast(player)
                    }
                }
    }

}