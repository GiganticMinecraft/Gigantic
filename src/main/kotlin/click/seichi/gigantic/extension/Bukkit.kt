package click.seichi.gigantic.extension

import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar

/**
 * @author tar0ss
 */
fun createInvisibleBossBar(): BossBar = Bukkit.createBossBar(
        "title",
        BarColor.YELLOW,
        BarStyle.SOLID
).apply {
    isVisible = false
}