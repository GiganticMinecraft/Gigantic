package click.seichi.gigantic.extension

import click.seichi.gigantic.menu.Menu
import org.bukkit.Server
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar

/**
 * @author tar0ss
 */
fun Server.refreshAllPlayerMenu() {
    onlinePlayers.forEach { player ->
        val menu = player.openInventory.topInventory.holder as? Menu ?: return@forEach
        menu.reopen(player)
    }
}

fun Server.createInvisibleBossBar(): BossBar = createBossBar(
        "title",
        BarColor.YELLOW,
        BarStyle.SOLID
).apply {
    isVisible = false
}