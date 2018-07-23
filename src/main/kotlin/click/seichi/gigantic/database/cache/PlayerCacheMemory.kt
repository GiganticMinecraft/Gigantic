package click.seichi.gigantic.database.cache

import click.seichi.gigantic.database.cache.caches.PlayerCache
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @author tar0ss
 */
object PlayerCacheMemory {
    private val playerCacheMap = mutableMapOf<UUID, PlayerCache>()

    fun find(uniqueId: UUID) = playerCacheMap[uniqueId]

    fun preLoginAsync(uniqueId: UUID, playerName: String) = runBlocking {
        delay(3L, TimeUnit.SECONDS)

        val newCache = PlayerCache(uniqueId, playerName)

        transaction {
            newCache.read()
        }

        playerCacheMap[uniqueId] = newCache
    }

    fun quit(uniqueId: UUID) {
        val cache = playerCacheMap.remove(uniqueId) ?: return
        cache.writeAsync()
    }

    fun stopServerWith(uniqueId: UUID) {
        val cache = playerCacheMap.remove(uniqueId) ?: return
        cache.write()
    }

}