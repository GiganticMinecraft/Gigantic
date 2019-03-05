package click.seichi.gigantic.extension

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.menu.Menu
import org.bukkit.Server
import org.bukkit.scheduler.BukkitRunnable

/**
 * @author tar0ss
 */
fun Server.refreshAllPlayerMenu() {
    onlinePlayers.forEach { player ->
        val menu = player.openInventory.topInventory.holder as? Menu ?: return@forEach
        menu.reopen(player)
    }
}

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
