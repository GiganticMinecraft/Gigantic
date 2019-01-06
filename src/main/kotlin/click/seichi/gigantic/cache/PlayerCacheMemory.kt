package click.seichi.gigantic.cache

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.cache.cache.PlayerCache
import click.seichi.gigantic.config.Config
import kotlinx.coroutines.delay
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @author tar0ss
 */
object PlayerCacheMemory {
    private val playerCacheMap = mutableMapOf<UUID, PlayerCache>()

    fun get(uniqueId: UUID) = playerCacheMap[uniqueId]!!

    fun contains(uniqueId: UUID) = playerCacheMap.contains(uniqueId)

    suspend fun add(uniqueId: UUID, playerName: String) {

        val newCache = PlayerCache(uniqueId, playerName)

        // 読み込み可能状態になるまで繰り返す
        var count = 0
        while (!newCache.canRead() && count++ < Config.LOAD_TIME) {
            // 1秒待機
            delay(TimeUnit.SECONDS.convert(1L, TimeUnit.MILLISECONDS))
        }
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