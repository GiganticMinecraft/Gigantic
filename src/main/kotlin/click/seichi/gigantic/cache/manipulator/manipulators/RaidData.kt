package click.seichi.gigantic.cache.manipulator.manipulators

import click.seichi.gigantic.cache.cache.Cache
import click.seichi.gigantic.cache.cache.PlayerCache
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.Manipulator
import click.seichi.gigantic.raid.boss.Boss
import click.seichi.gigantic.relic.Relic

class RaidData : Manipulator<RaidData, PlayerCache> {

    private val bossMap: MutableMap<Boss, Long> = mutableMapOf()
    private val relicMap: MutableMap<Relic, Long> = mutableMapOf()

    override fun from(cache: Cache<PlayerCache>): RaidData? {
        bossMap.clear()
        relicMap.clear()
        Boss.values().forEach {
            bossMap[it] = cache.getOrPut(Keys.BOSS_MAP[it] ?: return null)
        }
        Relic.values().forEach {
            relicMap[it] = cache.getOrPut(Keys.RELIC_MAP[it] ?: return null)
        }
        return this
    }

    override fun set(cache: Cache<PlayerCache>): Boolean {
        Boss.values().forEach {
            cache.offer(Keys.BOSS_MAP[it] ?: return false, bossMap[it] ?: return false)
        }
        Relic.values().forEach {
            cache.offer(Keys.RELIC_MAP[it] ?: return false, relicMap[it] ?: return false)
        }
        return true
    }

    fun defeat(boss: Boss): Long {
        val next = (bossMap[boss] ?: 0L) + 1L
        bossMap[boss] = next
        return next
    }

    fun addRelic(relic: Relic): Long {
        val next = (relicMap[relic] ?: 0L) + 1L
        relicMap[relic] = next
        return next
    }

    fun getDefeatCount(boss: Boss) = bossMap[boss] ?: 0L
    fun getRelicAmount(relic: Relic) = relicMap[relic] ?: 0L

    fun copyBossMap() = bossMap.toMap()
    fun copyRelicMap() = relicMap.toMap()

}
