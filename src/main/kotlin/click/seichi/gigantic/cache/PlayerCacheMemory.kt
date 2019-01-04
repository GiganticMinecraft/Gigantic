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

    fun write(uniqueId: UUID) {
        write(playerCacheMap[uniqueId] ?: return)
    }

    fun writeThenRemoved(uniqueId: UUID) {
        write(playerCacheMap.remove(uniqueId) ?: return)
    }

    private fun write(playerCache: PlayerCache) {
        if (Gigantic.IS_DEBUG) return
        else playerCache.write()
    }

}