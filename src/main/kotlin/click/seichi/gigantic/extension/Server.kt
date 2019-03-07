package click.seichi.gigantic.extension

import click.seichi.gigantic.Gigantic
import org.bukkit.scheduler.BukkitRunnable

/**
 * @author tar0ss
 */

fun runTaskLater(delay: Long, action: () -> Unit) {
    object : BukkitRunnable() {
        override fun run() {
            action()
        }
    }.runTaskLater(Gigantic.PLUGIN, delay)
}

fun runTaskAsync(action: () -> Unit) {
    object : BukkitRunnable() {
        override fun run() {
            action()
        }
    }.runTaskAsynchronously(Gigantic.PLUGIN)
}

fun runTaskLaterAsync(delay: Long, action: () -> Unit) {
    object : BukkitRunnable() {
        override fun run() {
            action()
        }
    }.runTaskLaterAsynchronously(Gigantic.PLUGIN, delay)
}
