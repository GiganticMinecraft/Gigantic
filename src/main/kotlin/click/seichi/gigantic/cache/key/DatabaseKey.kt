package click.seichi.gigantic.cache.key

import click.seichi.gigantic.cache.cache.Cache
import org.jetbrains.exposed.dao.Entity

/**
 * @author tar0ss
 */
interface DatabaseKey<S : Cache<S>, V> : Key<S, V> {
    fun read(entity: Entity<*>): V

    fun store(entity: Entity<*>, value: V)

}