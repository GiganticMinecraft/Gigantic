package click.seichi.gigantic.cache.manipulator.manipulators

import click.seichi.gigantic.cache.cache.Cache
import click.seichi.gigantic.cache.cache.PlayerCache
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.ExpReason
import click.seichi.gigantic.cache.manipulator.Manipulator
import java.math.BigDecimal

class Exp : Manipulator<Exp, PlayerCache> {

    private val map: MutableMap<ExpReason, BigDecimal> = mutableMapOf()

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

    fun add(num: BigDecimal, reason: ExpReason = ExpReason.MINE_BLOCK): BigDecimal {
        val next = (map[reason] ?: BigDecimal.ZERO) + num
        map[reason] = next
        return next
    }

    fun inc(num: BigDecimal = BigDecimal.ONE, reason: ExpReason = ExpReason.MINE_BLOCK): BigDecimal {
        return add(num, reason)
    }

    fun get(reason: ExpReason = ExpReason.MINE_BLOCK) = map[reason] ?: BigDecimal.ZERO

    fun copyMap() = map.toMap()

}
