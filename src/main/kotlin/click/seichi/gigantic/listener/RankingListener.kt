package click.seichi.gigantic.listener

import click.seichi.gigantic.event.events.TickEvent
import click.seichi.gigantic.extension.runTaskAsync
import click.seichi.gigantic.player.Defaults
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

/**
 * @author tar0ss
 */
class RankingListener : Listener {

    @EventHandler
    fun updateRanking(event: TickEvent) {
        if (event.ticks % Defaults.RANKING_UPDATE_INTERVAL != 0L) return
        runTaskAsync {
            RankCacheMemory.update()
        }
    }
}