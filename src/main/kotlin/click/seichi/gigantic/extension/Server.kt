package click.seichi.gigantic.extension

import click.seichi.gigantic.menu.Menu
import org.bukkit.Server

/**
 * @author tar0ss
 */
fun Server.refreshAllPlayerMenu() {
    onlinePlayers.forEach { player ->
        val menu = player.openInventory.topInventory.holder as? Menu ?: return@forEach
        menu.reopen(player)
    }
}