package click.seichi.gigantic.database.cache.keys

import click.seichi.gigantic.database.cache.CacheKey
import click.seichi.gigantic.database.cache.caches.PlayerCache
import click.seichi.gigantic.database.dao.User
import org.jetbrains.exposed.dao.Entity
import java.util.*

/**
 * @author tar0ss
 */
object PlayerCacheKeys {

    val LOCALE = object : CacheKey<PlayerCache, Locale> {
        override val default
            get() = Locale.JAPANESE

        override fun read(entity: Entity<*>): Locale? {
            val user = entity as User
            return Locale(user.localeString)
        }

        override fun store(entity: Entity<*>, value: Locale) {
            val user = entity as User
            user.localeString = value.language.substringBefore("_")
        }
    }

}