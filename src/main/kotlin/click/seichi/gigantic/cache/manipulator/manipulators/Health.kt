package click.seichi.gigantic.cache.manipulator.manipulators

import click.seichi.gigantic.cache.cache.Cache
import click.seichi.gigantic.cache.cache.PlayerCache
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.Manipulator

/**
 * @author tar0ss
 */
class Health : Manipulator<Health, PlayerCache> {

    var current: Long = Keys.HEALTH.default
        private set

    override fun from(cache: Cache<PlayerCache>): Health? {
        current = cache.find(Keys.HEALTH) ?: Keys.HEALTH.default
        return this
    }

    override fun set(cache: Cache<PlayerCache>): Boolean {
        return cache.offer(Keys.HEALTH, current)
    }
}