package click.seichi.gigantic.cache.manipulator.manipulators

import click.seichi.gigantic.cache.cache.Cache
import click.seichi.gigantic.cache.cache.PlayerCache
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.Manipulator
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.config.HealthConfig
import click.seichi.gigantic.player.Defaults
import kotlin.properties.Delegates

/**
 * @author tar0ss
 */
class Health : Manipulator<Health, PlayerCache> {
    // wrapした体力
    var current: Long by Delegates.notNull()
        private set
    // wrapした体力の最大値
    var max: Long by Delegates.notNull()
        private set

    lateinit var level: Level

    val isZero: Boolean
        get() = current == 0L

    override fun from(cache: Cache<PlayerCache>): Health? {
        current = cache.getOrPut(Keys.HEALTH)
        level = cache.find(CatalogPlayerCache.LEVEL) ?: return null
        return this
    }

    override fun set(cache: Cache<PlayerCache>): Boolean {
        cache.offer(Keys.HEALTH, current)
        return true
    }

    /**
     * @return 実際に増えた値
     */
    fun increase(other: Long): Long {
        val prev = current
        val next = current + other
        when {
            other < 0 -> throw IllegalArgumentException("$other must be positive.")
            current in (next + 1)..max -> current = max // overflow
            next < current -> {
            } // overflow,current = current
            current <= max -> current = next.coerceAtMost(max)
            else -> {
            } // current = current
        }
        return current - prev
    }

    /**
     * @return 実際に減った値
     */
    fun decrease(other: Long): Long {
        val prev = current
        val next = current - other
        current = when {
            other < 0 -> throw IllegalArgumentException("$other must be positive.")
            next > current -> 0L
            else -> next.coerceAtLeast(0L)
        }
        return prev - next
    }

    fun isMaxHealth() = current >= max

    fun updateMaxHealth() {
        max = HealthConfig.HEALTH_MAP[level.current] ?: Defaults.MAX_MANA
    }
}