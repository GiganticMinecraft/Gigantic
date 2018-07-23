package click.seichi.gigantic.database.cache.caches

import click.seichi.gigantic.database.UserEntityData
import click.seichi.gigantic.database.cache.DatabaseCache
import click.seichi.gigantic.database.cache.keys.PlayerCacheKeys
import java.util.*

/**
 * @author tar0ss
 */
class PlayerCache(val uniqueId: UUID, val playerName: String) : DatabaseCache<PlayerCache>(
        PlayerCacheKeys.LOCALE
) {
    override fun read() {
        val userEntityData = UserEntityData(uniqueId)
        PlayerCacheKeys.LOCALE.let {
            put(it, it.read(userEntityData.user))
        }
    }

    override fun write() {
        val userEntityData = UserEntityData(uniqueId)
        PlayerCacheKeys.LOCALE.let {
            it.store(userEntityData.user, get(it) ?: return@let)
        }
    }
}