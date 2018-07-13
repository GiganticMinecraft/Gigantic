package click.seichi.gigantic.player.components

import click.seichi.gigantic.config.ManaConfig
import click.seichi.gigantic.extension.gPlayer
import org.bukkit.entity.Player
import kotlin.properties.Delegates

class Mana(current: Long) {

    var current: Long = current
        private set
    var max: Long by Delegates.notNull()
        private set


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

    operator fun plus(other: Long) = increase(other, false)
    operator fun minus(other: Long) = decrease(other)

    fun update(player: Player) {
        max = ManaConfig.MANA_MAP[player.gPlayer?.level?.current ?: 1] ?: 0L
    }

}
