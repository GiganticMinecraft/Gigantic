package click.seichi.gigantic.listener

import click.seichi.gigantic.event.events.RelicGenerateEvent
import click.seichi.gigantic.event.events.SenseEvent
import click.seichi.gigantic.event.events.TickEvent
import click.seichi.gigantic.player.Defaults
import click.seichi.gigantic.sidebar.bars.EthelLogger
import click.seichi.gigantic.sidebar.bars.MainBar
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

/**
 * @author tar0ss
 */
class SideBarListener : Listener {

    @EventHandler
    fun onTick(event: TickEvent) {
        if (event.ticks % Defaults.SIDEBAR_RECORD_INTERVAL * 20L != 0L) return
        Bukkit.getServer().onlinePlayers
                .filterNotNull()
                .filter { it.isValid }
                .forEach {
                    MainBar.update(it)
                }
    }

    @EventHandler
    fun onSense(event: SenseEvent) {
        EthelLogger.add(event.player, event.will, event.amount)
    }

    @EventHandler
    fun onGenerateRelic(event: RelicGenerateEvent) {
        EthelLogger.use(event.player, event.useWill, event.useAmount)
    }
}