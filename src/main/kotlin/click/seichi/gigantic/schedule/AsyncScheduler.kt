package click.seichi.gigantic.schedule

import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitScheduler

/**
 * @author tar0ss
 */
class AsyncScheduler(plugin: Plugin, bukkitScheduler: BukkitScheduler) : AbstractScheduler(plugin, bukkitScheduler) {
    override fun schedule(runnable: Runnable) = bukkitScheduler
            .runTaskAsynchronously(plugin, runnable)

    override fun schedule(runnable: Runnable, delay: Long) = bukkitScheduler
            .runTaskLaterAsynchronously(plugin, runnable, delay)

    override fun schedule(runnable: Runnable, delay: Long, interval: Long) = bukkitScheduler
            .runTaskTimerAsynchronously(plugin, runnable, delay, interval)


}