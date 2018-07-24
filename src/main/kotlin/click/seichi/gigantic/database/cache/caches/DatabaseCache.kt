package click.seichi.gigantic.database.cache.caches

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.database.cache.keys.CacheKey
import org.bukkit.Bukkit


/**
 * データベースに対しマップとして振る舞うキャッシュを表す.
 *
 * @author unicroak
 * @author tar0ss
 */
abstract class DatabaseCache<C : DatabaseCache<C>>(vararg keys: CacheKey<C, out Any>) {
    private val cacheMap: MutableMap<CacheKey<C, out Any>, Any?> = keys
            .map { it to null }
            .toMap()
            .toMutableMap()

    abstract fun read()

    abstract fun write()

    fun readAsync() {
        Bukkit.getScheduler().runTask(Gigantic.PLUGIN) {
            read()
        }
    }

    fun writeAsync() {
        Bukkit.getScheduler().runTask(Gigantic.PLUGIN) {
            write()
        }
    }

    // All return value must be V
    @Suppress("UNCHECKED_CAST")
    fun <V : Any> get(key: CacheKey<C, out V>): V {
        return cacheMap.getOrPut(key) { key.default } as V
    }

    fun <V : Any> put(key: CacheKey<C, out V>, value: V?) {
        cacheMap[key] = value
    }

}