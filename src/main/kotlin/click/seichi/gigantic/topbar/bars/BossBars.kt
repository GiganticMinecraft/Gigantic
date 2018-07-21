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
        val progress = battle.raidBoss.health.div(boss.maxHealth.toDouble()).let { if (it > 1.0) 1.0 else it }
        TopBar(
                "${ChatColor.RED}${ChatColor.BOLD}" +
                        "$bossName ${battle.raidBoss.health} / ${boss.maxHealth}",
                progress,
                BarColor.RED,
                BarStyle.SEGMENTED_12
        )
    }

}