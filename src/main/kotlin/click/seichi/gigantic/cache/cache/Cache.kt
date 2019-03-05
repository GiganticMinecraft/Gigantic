package click.seichi.gigantic.cache.cache

import click.seichi.gigantic.cache.key.Key
import click.seichi.gigantic.cache.manipulator.Manipulator


/**
 * マップとして振る舞うキャッシュを表す.
 *
 * @author unicroak
 * @author tar0ss
 */
abstract class Cache<C : Cache<C>> {
    private val keyMap: MutableMap<Key<C, out Any?>, Any?> = mutableMapOf()

    @Suppress("UNCHECKED_CAST")
    fun <M : Manipulator<M, C>> manipulate(clazz: Class<M>, manipulating: (M) -> Unit): Boolean {
        return clazz.newInstance().from(this)?.apply(manipulating)?.set(this) ?: false
    }

    abstract fun read()

    abstract fun write()

    private fun <V : Any?> registerKey(key: Key<C, out V>, value: V? = null) {
        keyMap[key] = value ?: key.default
    }

    private fun <V : Any?> removeKey(key: Key<C, out V>) = keyMap.remove(key)

    // All return value must be V
    @Suppress("UNCHECKED_CAST")
    fun <V : Any?> getOrDefault(key: Key<C, out V>): V {
        return keyMap.getOrDefault(key, key.default) as V
    }

    // All return value must be V
    @Suppress("UNCHECKED_CAST")
    fun <V : Any?> getOrPut(key: Key<C, out V>, value: V = key.default): V {
        return keyMap.getOrPut(key) { value } as V
    }

    // All return value must be V
    @Suppress("UNCHECKED_CAST")
    fun <V : Any?> find(key: Key<C, out V>): V? {
        return keyMap[key] as V?
    }

    // All return value must be V
    @Suppress("UNCHECKED_CAST")
    fun <V : Any?> remove(key: Key<C, V>) = removeKey(key) as V?

    fun <V : Any?> offer(key: Key<C, V>, value: V): Boolean {
        if (!key.satisfyWith(value)) return false
        registerKey(key, value)
        return true
    }

    /**
     * 強制的に値を書き換える．
     * 安易に利用することを禁ずる
     */
    fun <V : Any?> force(key: Key<C, V>, value: V): Boolean {
        registerKey(key, value)
        return true
    }

    fun <V : Any?> replace(key: Key<C, V>, value: V): Boolean {
        if (!key.satisfyWith(value)) return false
        keyMap.replace(key, value) ?: return false
        return true
    }

    fun <V : Any?> transform(key: Key<C, V>, transforming: (V) -> V): Boolean {
        val oldValue = getOrPut(key) ?: return false
        val newValue = transforming(oldValue)
        return offer(key, newValue)
    }

}