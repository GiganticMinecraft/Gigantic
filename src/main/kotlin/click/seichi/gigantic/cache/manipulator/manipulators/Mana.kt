package click.seichi.gigantic.cache.manipulator.manipulators

import click.seichi.gigantic.cache.cache.Cache
import click.seichi.gigantic.cache.cache.PlayerCache
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.Manipulator
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.config.ManaConfig
import java.math.BigDecimal
import java.util.*
import kotlin.properties.Delegates

/**
 * マナは大気中や物質の中に存在するもの
 *
 * @author tar0ss
 */
class Mana : Manipulator<Mana, PlayerCache> {
    var current: BigDecimal by Delegates.notNull()
        private set
    var max: BigDecimal by Delegates.notNull()
        private set
    private lateinit var level: Level
    private lateinit var locale: Locale

    override fun from(cache: Cache<PlayerCache>): Mana? {
        current = cache.getOrPut(Keys.MANA)
        level = cache.find(CatalogPlayerCache.LEVEL) ?: return null
        locale = cache.getOrPut(Keys.LOCALE)
        return this
    }

    override fun set(cache: Cache<PlayerCache>): Boolean {
        return cache.offer(Keys.MANA, current)
    }

    fun increase(other: BigDecimal, ignoreMax: Boolean = false): BigDecimal {
        val prev = current
        val next = current + other
        when {
            other < 0.toBigDecimal() -> error("$other must be positive.")
            current in (next + 0.toBigDecimal())..max -> current = max // overflow
            next < current -> {
            } // overflow,current = current
            ignoreMax -> current = next
            current < max -> current = next.coerceAtMost(max)
            else -> {
            } // current = current
        }
        return current - prev
    }

    fun decrease(other: BigDecimal): BigDecimal {
        val prev = current
        val next = current - other
        current = when {
            other < 0.toBigDecimal() -> error("$other must be positive.")
            else -> next
        }
        return prev - current
    }

    fun updateMaxMana() {
        max = ManaConfig.MANA_MAP[level.current] ?: 0.toBigDecimal()
    }

    fun isMaxMana() = current >= max

    fun hasMana(other: BigDecimal) = current >= other

}