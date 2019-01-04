package click.seichi.gigantic.cache

import click.seichi.gigantic.cache.cache.PlayerCache
import java.util.*

/**
 * ランキング用プレイヤー
 * @author tar0ss
 */
object RankingPlayerCacheMemory {

    private val playerCacheMap = mutableMapOf<UUID, PlayerCache>()

    fun get(uniqueId: UUID) = playerCacheMap[uniqueId]!!

    fun contains(uniqueId: UUID) = playerCacheMap.contains(uniqueId)

    fun add(uniqueId: UUID, playerName: String) {

        val newCache = PlayerCache(uniqueId, playerName)

        newCache.read()

        playerCacheMap[uniqueId] = newCache
    }

    fun remove(uniqueId: UUID) = playerCacheMap.remove(uniqueId)

}