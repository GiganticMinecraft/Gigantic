package click.seichi.gigantic.cache.manipulator.manipulators

import click.seichi.gigantic.cache.cache.Cache
import click.seichi.gigantic.cache.cache.PlayerCache
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.Manipulator
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.config.HealthConfig
import kotlin.properties.Delegates

/**
 * @author tar0ss
 */
class Health : Manipulator<Health, PlayerCache> {
    var current: Long by Delegates.notNull()
        private set
    var max: Long by Delegates.notNull()
        private set
    private lateinit var level: Level

    override fun from(cache: Cache<PlayerCache>): Health? {
        current = cache.find(Keys.HEALTH) ?: Keys.HEALTH.default
        level = cache.find(CatalogPlayerCache.LEVEL) ?: return null
        return this
    }

    override fun set(cache: Cache<PlayerCache>): Boolean {
        return cache.offer(Keys.HEALTH, current)
    }

    fun increase(other: Long) {
        val next = current + other
        when {
            other < 0 -> throw IllegalArgumentException("$other must be positive.")
            current in next..max -> current = max // overflow
            next < current -> {
            } // overflow,current = current
            current < max -> current = next.coerceAtMost(max)
            else -> {
            } // current = current
        }
    }

    fun decrease(other: Long) {
        val next = current - other
        current = when {
            other < 0 -> throw IllegalArgumentException("$other must be positive.")
            next > current -> 0L
            else -> next.coerceAtLeast(0L)
        }
    }

    fun updateMaxHealth() {
        max = HealthConfig.HEALTH_MAP[level.current] ?: 0L
    }

}