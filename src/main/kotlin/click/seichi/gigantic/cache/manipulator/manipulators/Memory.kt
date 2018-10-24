package click.seichi.gigantic.cache.manipulator.manipulators

import click.seichi.gigantic.cache.cache.Cache
import click.seichi.gigantic.cache.cache.PlayerCache
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.Manipulator
import click.seichi.gigantic.will.Will

/**
 * @author tar0ss
 */
class Memory : Manipulator<Memory, PlayerCache> {

    val map = mutableMapOf<Will, Long>()


    override fun from(cache: Cache<PlayerCache>): Memory? {
        map.clear()
        Will.values().forEach { add(it, cache.getOrPut(Keys.MEMORY_MAP[it] ?: return null)) }
        return this
    }

    override fun set(cache: Cache<PlayerCache>): Boolean {
        Will.values().forEach { cache.offer(Keys.MEMORY_MAP[it] ?: return false, map[it] ?: return false) }
        return true
    }

    fun add(will: Will, n: Long): Long {
        val next = (map[will] ?: 0L) + n
        map[will] = next
        return next
    }

    fun get(will: Will) = map[will] ?: 0L

    fun copyMap() = map.toMap()

}