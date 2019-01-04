package click.seichi.gigantic.cache.key

import click.seichi.gigantic.cache.cache.Cache
import click.seichi.gigantic.database.UserEntity

/**
 * @author tar0ss
 */
interface DatabaseKey<S : Cache<S>, V> : Key<S, V> {
    fun read(entity: UserEntity): V

    fun store(entity: UserEntity, value: V)
}