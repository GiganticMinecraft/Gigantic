package click.seichi.gigantic.cache.key

import click.seichi.gigantic.cache.cache.Cache
import click.seichi.gigantic.database.RankEntity

/**
 * @author tar0ss
 */
interface RankKey<S : Cache<S>, V> : Key<S, V> {
    fun read(entity: RankEntity): V
}