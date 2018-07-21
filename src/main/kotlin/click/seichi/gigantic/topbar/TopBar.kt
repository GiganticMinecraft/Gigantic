package click.seichi.gigantic.topbar

import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar

/**
 * @author tar0ss
 */
class TopBar(
        private val title: String,
        private val progress: Double,
        private val color: BarColor,
        private val style: BarStyle
) {
    fun show(bar: BossBar) {
        bar.title = title
        bar.progress = progress
        bar.color = color
        bar.style = style
        bar.isVisible = true
    }

    fun hide(bar: BossBar) {
        bar.isVisible = false
    }
}