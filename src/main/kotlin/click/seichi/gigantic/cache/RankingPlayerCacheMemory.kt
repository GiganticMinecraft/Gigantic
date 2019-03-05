package click.seichi.gigantic.cache

import click.seichi.gigantic.cache.cache.RankingPlayerCache
import java.util.*

/**
 * @author tar0ss
 */
object RankingPlayerCacheMemory {
    private val playerCacheMap = mutableMapOf<UUID, RankingPlayerCache>()

    fun get(uniqueId: UUID) = playerCacheMap[uniqueId]!!

    fun find(uniqueId: UUID) = playerCacheMap[uniqueId]

    fun contains(uniqueId: UUID) = playerCacheMap.contains(uniqueId)

    // transaction内で実行すること
    fun add(uniqueId: UUID) {
        val newCache = RankingPlayerCache(uniqueId)
        newCache.read()
        playerCacheMap[uniqueId] = newCache
    }

    // transaction内で実行すること
    fun addAll(vararg uniqueId: UUID) {
        uniqueId.forEach { add(it) }
    }

    fun remove(uniqueId: UUID) = playerCacheMap.remove(uniqueId)

    fun clearAll() = playerCacheMap.clear()
}