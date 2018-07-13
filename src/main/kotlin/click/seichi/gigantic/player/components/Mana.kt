package click.seichi.gigantic.player.components

import click.seichi.gigantic.config.ManaConfig
import click.seichi.gigantic.extension.gPlayer
import org.bukkit.Bukkit
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
    lateinit var bar: BossBar

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

    fun isUnlocked(player: Player): Boolean {
        val gPlayer = player.gPlayer ?: return false
        return gPlayer.level.current >= 10
    }

    fun init(player: Player) {
        updateMaxMana(player)
        bar = Bukkit.createBossBar("title", BarColor.YELLOW, BarStyle.SOLID)
        bar.isVisible = false
        bar.addPlayer(player)
        display()
        bar.isVisible = true
    }

    fun display() {
        bar.run {
            title = "$current / $max"
            progress = current.div(max.toDouble())
            color = when (progress) {
                in 0.00..0.01 -> BarColor.RED
                in 0.01..0.10 -> BarColor.PINK
                in 0.10..0.99 -> BarColor.BLUE
                in 0.99..1.00 -> BarColor.PURPLE
                else -> BarColor.YELLOW
            }
        }
    }

    fun updateMaxMana(player: Player) {
        max = ManaConfig.MANA_MAP[player.gPlayer?.level?.current ?: 1] ?: 0L
    }

    fun finish(player: Player) {
        bar.removeAll()
    }

}
