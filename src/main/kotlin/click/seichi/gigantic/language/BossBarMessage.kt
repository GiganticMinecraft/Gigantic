package click.seichi.gigantic.language

import click.seichi.gigantic.extension.wrappedLocale
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class BossBarMessage(
        private val bar: BossBar,
        private val title: LocalizedText,
        private val progress: Double,
        private val color: BarColor,
        private val style: BarStyle
) : Message {
    override fun sendTo(player: Player) {
        bar.title = title.asSafety(player.wrappedLocale)
        bar.progress = progress
        bar.color = color
        bar.style = style
        bar.isVisible = true
    }
}