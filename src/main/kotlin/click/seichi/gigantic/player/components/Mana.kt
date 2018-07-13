package click.seichi.gigantic.player.components

import click.seichi.gigantic.config.ManaConfig.MANA_MAP
import click.seichi.gigantic.event.events.LevelUpEvent
import click.seichi.gigantic.extension.gPlayer
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.boss.BossBar
import org.bukkit.entity.Player
import kotlin.properties.Delegates

class Mana(current: Long) {

    var current: Long = current
        private set
    var max: Long by Delegates.notNull()
        private set
    val bar: BossBar by lazy {
        Bukkit.createBossBar("title", BarColor.YELLOW, BarStyle.SOLID).apply {
            isVisible = false
        }
    }

    fun increase(other: Long, ignoreMax: Boolean = false) {
        val next = current + other
        when {
            other < 0 -> throw IllegalArgumentException("$other must not be negative.")
            next < current && ignoreMax -> current = Long.MAX_VALUE // overflow
            current in next..max -> current = max // overflow
            next < current -> {
            } // overflow,current = current
            ignoreMax -> current = next
            current < max -> current = next.coerceAtMost(max)
            else -> {
            } // current = current
        }
    }

    fun decrease(other: Long) {
        val next = current - other
        current = when {
            other > 0 -> throw IllegalArgumentException("$other must not be positive.")
            next > current -> 0L
            else -> next.coerceAtLeast(0L)
        }
    }

    fun init(player: Player) {
        if (isUnlocked(player)) {
            updateMaxMana(player)
            display(player)
        }
    }

    fun display(player: Player) {
        if (!isUnlocked(player)) return
        bar.run {
            if (!players.contains(player)) {
                addPlayer(player)
                bar.isVisible = true
            }
            title = "${ChatColor.AQUA}${ChatColor.BOLD}$current / $max"
            progress = current.div(max.toDouble()).let { if (it > 1.0) 1.0 else it }
            color = when (progress) {
                1.00 -> BarColor.WHITE
                in 0.99..1.00 -> BarColor.PURPLE
                in 0.10..0.99 -> BarColor.BLUE
                in 0.01..0.10 -> BarColor.PINK
                in 0.00..0.01 -> BarColor.RED
                else -> BarColor.YELLOW
            }
        }
    }

    fun finish(player: Player) {
        bar.removeAll()
    }

    fun onLevelUp(event: LevelUpEvent) {
        updateMaxMana(event.player)
        display(event.player)
        increase(max, true)
    }

    private fun updateMaxMana(player: Player) {
        max = MANA_MAP[player.gPlayer?.level?.current ?: 1] ?: 0L
    }

    private fun isUnlocked(player: Player): Boolean {
        val gPlayer = player.gPlayer ?: return false
        return gPlayer.level.current >= 10
    }

}
