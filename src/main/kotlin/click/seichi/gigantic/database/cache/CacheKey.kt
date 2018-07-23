package click.seichi.gigantic.database.cache

import org.jetbrains.exposed.dao.Entity


/**
 * [DatabaseCache]のデータに対応したキーを表す.
 *
 * @author unicroak
 * @author tar0ss
 */
interface CacheKey<S : DatabaseCache<S>, V> {

    val default: V

    fun read(entity: Entity<*>): V?

    fun store(entity: Entity<*>, value: V)

}