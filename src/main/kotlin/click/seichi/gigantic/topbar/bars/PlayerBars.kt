package click.seichi.gigantic.topbar.bars

import click.seichi.gigantic.player.components.Mana
import click.seichi.gigantic.topbar.TopBar
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle

/**
 * @author tar0ss
 */
object PlayerBars {
    val MANA = { mana: Mana, title: String ->
        mana.run {
            val progress = current.div(max.toDouble()).let { if (it > 1.0) 1.0 else it }
            TopBar(
                    title,
                    progress,
                    when (progress) {
                        1.00 -> BarColor.WHITE
                        in 0.99..1.00 -> BarColor.PURPLE
                        in 0.10..0.99 -> BarColor.BLUE
                        in 0.01..0.10 -> BarColor.PINK
                        in 0.00..0.01 -> BarColor.RED
                        else -> BarColor.YELLOW
                    },
                    BarStyle.SOLID
            )
        }
    }

}