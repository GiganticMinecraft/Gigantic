package click.seichi.gigantic.data.caches

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.data.keys.Key
import org.bukkit.Bukkit


/**
 * マップとして振る舞うキャッシュを表す.
 *
 * @author unicroak
 * @author tar0ss
 */
abstract class Cache<C : Cache<C>> {
    private val map: MutableMap<Key<C, out Any>, Any> = mutableMapOf()

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
    protected fun <V : Any> getOrDefault(key: Key<C, out V>): V {
        return map.getOrDefault(key, key.default) as V
    }

    // All return value must be V
    @Suppress("UNCHECKED_CAST")
    fun <V : Any> find(key: Key<C, out V>): V? {
        return map[key] as V?
    }

    fun <V : Any> registerKey(key: Key<C, out V>) {
        map.putIfAbsent(key, key.default)
    }

    fun <V : Any> removeKey(key: Key<C, out V>) {
        map.remove(key)
    }

    fun <V : Any> offer(key: Key<C, V>, value: V): Boolean {
        if (!key.satisfyWith(value)) return false
        map.replace(key, value) ?: return false
        return true
    }

    fun <V : Any> transform(key: Key<C, V>, transforming: (V) -> V): Boolean {
        val oldValue = find(key) ?: return false
        val newValue = transforming(oldValue)
        return offer(key, newValue)
    }

    fun <V : Any> support(key: Key<C, out V>): Boolean {
        return map.containsKey(key)
    }

}