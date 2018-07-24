package click.seichi.gigantic.database.cache

import click.seichi.gigantic.database.cache.caches.PlayerCache
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

/**
 * @author tar0ss
 */
object PlayerCacheMemory {
    private val playerCacheMap = mutableMapOf<UUID, PlayerCache>()

    fun find(uniqueId: UUID) = playerCacheMap[uniqueId]

    fun get(uniqueId: UUID) = playerCacheMap[uniqueId]!!

    fun add(uniqueId: UUID, playerName: String) {

        val newCache = PlayerCache(uniqueId, playerName)

        transaction {
            newCache.read()
        }

        playerCacheMap[uniqueId] = newCache
    }

    fun remove(uniqueId: UUID, isAsync: Boolean) {
        playerCacheMap.remove(uniqueId)?.run {
            transaction {
                if (isAsync) writeAsync()
                else write()
            }
        }
    }

}