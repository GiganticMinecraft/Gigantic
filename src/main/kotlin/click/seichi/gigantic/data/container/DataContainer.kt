package click.seichi.gigantic.data.container

import click.seichi.gigantic.data.key.Key

/**
 * @author tar0ss
 */
abstract class DataContainer<C : DataContainer<C>>(vararg keys: Key<C, out Any>) {
    private val map: MutableMap<Key<C, out Any>, Any?> = keys
            .map { it to it.default }
            .toMap()
            .toMutableMap()

    fun <V : Any> registerKey(key: Key<C, out V>) {
        map.putIfAbsent(key, key.default)
    }

    fun <V : Any> removeKey(key: Key<C, out V>) {
        map.remove(key)
    }

    @Suppress("UNCHECKED_CAST")
    fun <V : Any> get(key: Key<C, out V>): V {
        return map.getOrDefault(key, key.default) as V
    }

    fun <V : Any> offer(key: Key<C, V>, value: V): Boolean {
        if (!key.satisfyWith(value)) return false
        map.replace(key, value) ?: return false
        return true
    }

    fun <V : Any> transform(key: Key<C, V>, transforming: (V) -> V): Boolean {
        val oldValue = get(key)
        val newValue = transforming(oldValue)
        return offer(key, newValue)
    }

    fun <V : Any> support(key: Key<C, out V>): Boolean {
        return map.containsKey(key)
    }

}