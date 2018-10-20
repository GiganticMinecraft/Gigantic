package click.seichi.gigantic.topbar.bars

import click.seichi.gigantic.raid.RaidBattle
import click.seichi.gigantic.topbar.TopBar
import org.bukkit.ChatColor
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle

/**
 * @author tar0ss
 */
object BossBars {

    val RAID_BOSS = { battle: RaidBattle, bossName: String ->
        val boss = battle.boss
        val color = when (boss.maxHealth) {
            in 0L..9999L -> ChatColor.GOLD
            in 10000L..99999L -> ChatColor.RED
            in 100000L..999999L -> ChatColor.LIGHT_PURPLE
            else -> ChatColor.DARK_PURPLE
        }
        val barColor = when (boss.maxHealth) {
            in 0L..9999L -> BarColor.YELLOW
            in 10000L..99999L -> BarColor.RED
            in 100000L..999999L -> BarColor.PINK
            else -> BarColor.PURPLE
        }
        val progress = battle.raidBoss.health.div(boss.maxHealth.toDouble()).coerceIn(0.0, 1.0)
        TopBar(
                "$color" +
                        "$bossName " +
                        "${battle.raidBoss.health}" +
                        " / " +
                        "${boss.maxHealth}",
                progress,
                barColor,
                BarStyle.SEGMENTED_12
        )
    }

}