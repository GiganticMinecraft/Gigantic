package click.seichi.gigantic.player.components

import click.seichi.gigantic.config.ManaConfig.MANA_MAP
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

    fun updateMaxMana(level: Level) {
        max = MANA_MAP[level.current] ?: 0L
    }

}
