package click.seichi.gigantic.cache

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.cache.cache.PlayerCache
import java.util.*

/**
 * @author tar0ss
 */
object PlayerCacheMemory {
    private val playerCacheMap = mutableMapOf<UUID, PlayerCache>()

    fun get(uniqueId: UUID) = playerCacheMap[uniqueId]!!

    fun contains(uniqueId: UUID) = playerCacheMap.contains(uniqueId)

    fun add(uniqueId: UUID, playerName: String) {

        val newCache = PlayerCache(uniqueId, playerName)

        newCache.read()

        playerCacheMap[uniqueId] = newCache
    }

    fun write(uniqueId: UUID, isAsync: Boolean) {
        write(playerCacheMap[uniqueId] ?: return, isAsync)
    }

    fun writeThenRemoved(uniqueId: UUID, isAsync: Boolean) {
        write(playerCacheMap.remove(uniqueId) ?: return, isAsync)
    }

    private fun write(playerCache: PlayerCache, isAsync: Boolean) {
        if (Gigantic.IS_DEBUG) return
        if (isAsync) playerCache.writeAsync()
        else playerCache.write()
    }

}