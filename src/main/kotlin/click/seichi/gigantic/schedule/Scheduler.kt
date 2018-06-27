package click.seichi.gigantic.schedule

import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitScheduler

/**
 * @author tar0ss
 */
class Scheduler(plugin: Plugin, bukkitScheduler: BukkitScheduler) : AbstractScheduler(plugin, bukkitScheduler) {
    override fun schedule(runnable: Runnable) = bukkitScheduler
            .runTask(plugin, runnable)

    override fun schedule(runnable: Runnable, delay: Long) = bukkitScheduler
            .runTaskLater(plugin, runnable, delay)

    override fun schedule(runnable: Runnable, delay: Long, interval: Long) = bukkitScheduler
            .runTaskTimer(plugin, runnable, delay, interval)


}