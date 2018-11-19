package click.seichi.gigantic.cache.manipulator.manipulators

import click.seichi.gigantic.cache.cache.Cache
import click.seichi.gigantic.cache.cache.PlayerCache
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.ExpReason
import click.seichi.gigantic.cache.manipulator.Manipulator

class Exp : Manipulator<Exp, PlayerCache> {

    private val map: MutableMap<ExpReason, Long> = mutableMapOf()

    override fun from(cache: Cache<PlayerCache>): Exp? {
        map.clear()
        ExpReason.values()
                .map { add(cache.getOrPut(Keys.EXP_MAP[it] ?: return null), it) }
        return this
    }

    override fun set(cache: Cache<PlayerCache>): Boolean {
        ExpReason.values()
                .forEach { cache.offer(Keys.EXP_MAP[it] ?: return false, map[it] ?: return false) }
        return true
    }

    fun add(num: Long, reason: ExpReason = ExpReason.MINE_BLOCK): Long {
        val next = (map[reason] ?: 0L) + num
        map[reason] = next
        return next
    }

    fun get(reason: ExpReason = ExpReason.MINE_BLOCK) = map[reason] ?: 0L

    fun calcExp(): Long {
        return ExpReason.values().fold(0L) { source: Long, reason ->
            source + map.getOrDefault(reason, 0L).run {
                when (reason) {
                    ExpReason.DEATH_PENALTY -> times(-1L)
                    else -> this@run
                }
            }
        }
    }

    fun copyMap() = map.toMap()

}
