package click.seichi.gigantic.listener

import click.seichi.gigantic.event.events.RelicGenerateEvent
import click.seichi.gigantic.event.events.SenseEvent
import click.seichi.gigantic.sidebar.SideBarType
import click.seichi.gigantic.sidebar.bars.EthelLogger
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

/**
 * @author tar0ss
 */
class SideBarListener : Listener {

    @EventHandler
    fun onSense(event: SenseEvent) {
        val ethelLogger = SideBarType.Ethel.sideBar as EthelLogger
        ethelLogger.add(event.player, event.will, event.amount)
    }

    @EventHandler
    fun onGenerateRelic(event: RelicGenerateEvent) {
        val ethelLogger = SideBarType.Ethel.sideBar as EthelLogger
        ethelLogger.use(event.player, event.useWill, event.useAmount)
    }
}