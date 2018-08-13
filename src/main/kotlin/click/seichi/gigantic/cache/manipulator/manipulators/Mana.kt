package click.seichi.gigantic.cache.manipulator.manipulators

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.cache.cache.Cache
import click.seichi.gigantic.cache.cache.PlayerCache
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.Manipulator
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.config.ManaConfig
import click.seichi.gigantic.topbar.bars.PlayerBars
import org.bukkit.boss.BossBar
import java.util.*
import kotlin.properties.Delegates

/**
 * @author tar0ss
 */
class Mana : Manipulator<Mana, PlayerCache> {
    var current: Long by Delegates.notNull()
        private set
    var max: Long by Delegates.notNull()
        private set
    private lateinit var level: Level
    private lateinit var locale: Locale
    private lateinit var bar: BossBar

    override fun from(cache: Cache<PlayerCache>): Mana? {
        current = cache.find(Keys.MANA) ?: return null
        level = cache.find(CatalogPlayerCache.LEVEL) ?: return null
        locale = cache.find(Keys.LOCALE) ?: return null
        return this
    }

    override fun set(cache: Cache<PlayerCache>): Boolean {
        return cache.offer(Keys.MANA, current)
    }

    fun increase(other: Long, ignoreMax: Boolean = false) {
        val next = current + other
        when {
            other < 0 -> throw IllegalArgumentException("$other must be positive.")
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
            other < 0 -> throw IllegalArgumentException("$other must be positive.")
            next > current -> 0L
            else -> next.coerceAtLeast(0L)
        }
    }

    fun updateMaxMana() {
        max = ManaConfig.MANA_MAP[level.current] ?: 0L
    }

    fun createBar() {
        bar = Gigantic.createInvisibleBossBar()
    }

    fun display() {
        PlayerBars.MANA(this, locale).show(bar)
    }

}