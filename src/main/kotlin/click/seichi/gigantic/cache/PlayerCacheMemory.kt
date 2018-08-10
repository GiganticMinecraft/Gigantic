package click.seichi.gigantic.cache

import click.seichi.gigantic.cache.cache.PlayerCache
import java.util.*

/**
 * @author tar0ss
 */
object PlayerCacheMemory {
    private val playerCacheMap = mutableMapOf<UUID, PlayerCache>()

    fun get(uniqueId: UUID) = playerCacheMap[uniqueId]!!

    fun add(uniqueId: UUID, playerName: String) {

        val newCache = PlayerCache(uniqueId, playerName)

        newCache.read()

        playerCacheMap[uniqueId] = newCache
    }

    fun remove(uniqueId: UUID, isAsync: Boolean) {
        playerCacheMap.remove(uniqueId)?.run {
            if (isAsync) writeAsync()
            else write()
        }
    }

}