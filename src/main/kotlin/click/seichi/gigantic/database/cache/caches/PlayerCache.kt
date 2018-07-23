package click.seichi.gigantic.database.cache.caches

import click.seichi.gigantic.database.UserEntityData
import click.seichi.gigantic.database.cache.DatabaseCache
import click.seichi.gigantic.database.cache.keys.PlayerCacheKeys
import java.util.*

/**
 * @author tar0ss
 */
class PlayerCache(val uniqueId: UUID, val playerName: String) : DatabaseCache<PlayerCache>(
        PlayerCacheKeys.LOCALE,
        PlayerCacheKeys.MANA,
        *PlayerCacheKeys.MINEBLOCK_MAP.values.toTypedArray(),
        *PlayerCacheKeys.MEMORY_MAP.values.toTypedArray(),
        *PlayerCacheKeys.APTITUDE_MAP.values.toTypedArray(),
        *PlayerCacheKeys.BOSS_MAP.values.toTypedArray(),
        *PlayerCacheKeys.RELIC_MAP.values.toTypedArray()
) {
    override fun read() {
        UserEntityData(uniqueId).run {
            PlayerCacheKeys.LOCALE.let {
                put(it, it.read(user))
            }
            PlayerCacheKeys.MANA.let {
                put(it, it.read(user))
            }
            PlayerCacheKeys.MINEBLOCK_MAP.forEach { reason, key ->
                put(key, key.read(userMineBlockMap[reason] ?: return@forEach))
            }
            PlayerCacheKeys.MEMORY_MAP.forEach { will, key ->
                put(key, key.read(userWillMap[will] ?: return@forEach))
            }
            PlayerCacheKeys.APTITUDE_MAP.forEach { will, key ->
                put(key, key.read(userWillMap[will] ?: return@forEach))
            }
            PlayerCacheKeys.BOSS_MAP.forEach { boss, key ->
                put(key, key.read(userBossMap[boss] ?: return@forEach))
            }
            PlayerCacheKeys.RELIC_MAP.forEach { relic, key ->
                put(key, key.read(userRelicMap[relic] ?: return@forEach))
            }
        }
    }

    override fun write() {
        UserEntityData(uniqueId).run {
            PlayerCacheKeys.LOCALE.let {
                it.store(user, get(it) ?: return@let)
            }
            PlayerCacheKeys.MANA.let {
                it.store(user, get(it) ?: return@let)
            }
            PlayerCacheKeys.MINEBLOCK_MAP.forEach { reason, key ->
                key.store(userMineBlockMap[reason] ?: return@forEach, get(key) ?: return@forEach)
            }
            PlayerCacheKeys.MEMORY_MAP.forEach { will, key ->
                key.store(userWillMap[will] ?: return@forEach, get(key) ?: return@forEach)
            }
            PlayerCacheKeys.APTITUDE_MAP.forEach { will, key ->
                key.store(userWillMap[will] ?: return@forEach, get(key) ?: return@forEach)
            }
            PlayerCacheKeys.BOSS_MAP.forEach { boss, key ->
                key.store(userBossMap[boss] ?: return@forEach, get(key) ?: return@forEach)
            }
            PlayerCacheKeys.RELIC_MAP.forEach { relic, key ->
                key.store(userRelicMap[relic] ?: return@forEach, get(key) ?: return@forEach)
            }
        }
    }
}