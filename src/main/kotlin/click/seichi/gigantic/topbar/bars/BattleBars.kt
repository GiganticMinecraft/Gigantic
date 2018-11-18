package click.seichi.gigantic.topbar.bars

import click.seichi.gigantic.message.messages.MonsterSpiritMessages
import click.seichi.gigantic.monster.SoulMonster
import click.seichi.gigantic.topbar.TopBar
import org.bukkit.ChatColor
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import java.util.*

/**
 * @author tar0ss
 */
object BattleBars {

    val SEAL = { progress: Double, monster: SoulMonster, locale: Locale ->
        val prev = progress
        TopBar(
                "${ChatColor.RED}" +
                        MonsterSpiritMessages.SEAL(monster.getName(locale))
                                .asSafety(locale),
                if (progress > prev) progress else prev,
                BarColor.RED,
                BarStyle.SOLID
        )
    }

    val RESET = { monster: SoulMonster, locale: Locale ->
        TopBar(
                "${ChatColor.RED}" +
                        MonsterSpiritMessages.SEAL(monster.getName(locale))
                                .asSafety(locale),
                0.0,
                BarColor.RED,
                BarStyle.SOLID
        )
    }

    val AWAKE = { health: Long, monster: SoulMonster, locale: Locale ->
        val maxHealth = monster.parameter.health.toBigDecimal()
        val titleColor = when (maxHealth.toLong()) {
            in 0 until 500 -> ChatColor.LIGHT_PURPLE
            in 500 until 2000 -> ChatColor.DARK_PURPLE
            in 2000 until 10000 -> ChatColor.DARK_BLUE
            in 10000 until 70000 -> ChatColor.BLUE
            else -> ChatColor.WHITE
        }
        val progress = health.toDouble().div(maxHealth.toDouble()).coerceIn(0.0, 1.0)
        val barColor = when (progress) {
            in 0.0..0.05 -> BarColor.RED
            in 0.05..0.20 -> BarColor.PINK
            in 0.20..0.70 -> BarColor.BLUE
            else -> BarColor.WHITE
        }
        val bossName = MonsterSpiritMessages.AWAKE(monster.getName(locale)).asSafety(locale)
        TopBar(
                "$titleColor" +
                        "$bossName " +
                        "$health" +
                        " / " +
                        "${maxHealth.setScale(1)}",
                progress,
                barColor,
                BarStyle.SEGMENTED_20
        )
    }

}