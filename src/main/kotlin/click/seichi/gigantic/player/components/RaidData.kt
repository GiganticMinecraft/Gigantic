package click.seichi.gigantic.player.components

import click.seichi.gigantic.boss.Boss
import click.seichi.gigantic.relic.Relic

class RaidData(
        private val bossMap: MutableMap<Boss, Long>,
        private val relicMap: MutableMap<Relic, Long>
) {

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
