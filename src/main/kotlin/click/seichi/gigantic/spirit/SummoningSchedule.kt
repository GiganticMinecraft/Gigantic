package click.seichi.gigantic.spirit

import click.seichi.gigantic.event.events.ScheduleSummoningEvent
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

/**
 * @author unicroak
 */
class SummoningSchedule : BukkitRunnable() {

    override fun run() {
        Bukkit.getPluginManager().callEvent(ScheduleSummoningEvent())
    }

}