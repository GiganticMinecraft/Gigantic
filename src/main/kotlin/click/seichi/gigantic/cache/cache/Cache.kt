package click.seichi.gigantic.cache.cache

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.cache.key.Key
import click.seichi.gigantic.cache.manipulator.Manipulator
import org.bukkit.Bukkit


/**
 * マップとして振る舞うキャッシュを表す.
 *
 * @author unicroak
 * @author tar0ss
 */
abstract class Cache<C : Cache<C>> {
    private val keyMap: MutableMap<Key<C, out Any?>, Any?> = mutableMapOf()

    private val manipulatorMap: MutableMap<Class<out Any>, Any> = mutableMapOf()

    @Suppress("UNCHECKED_CAST")
    fun <M : Manipulator<M, C>> find(clazz: Class<M>): M? {
        val manipulator = (manipulatorMap[clazz] as? M ?: clazz.newInstance()).from(this)
        return manipulator?.also { manipulatorMap[clazz] = it }
    }

    @Suppress("UNCHECKED_CAST")
    fun <M : Manipulator<M, C>> manipulate(clazz: Class<M>, manipulating: (M) -> Unit): Boolean {
        return find(clazz)?.apply(manipulating)?.set(this) ?: false
    }

    abstract fun read()

    abstract fun write()

    fun readAsync() {
        Bukkit.getScheduler().runTask(Gigantic.PLUGIN, Runnable { read() })
    }

    fun writeAsync() {
        Bukkit.getScheduler().runTask(Gigantic.PLUGIN, Runnable { write() })
    }

    private fun <V : Any?> registerKey(key: Key<C, out V>, value: V? = null) {
        keyMap[key] = value ?: key.default
    }

    private fun <V : Any?> removeKey(key: Key<C, out V>) = keyMap.remove(key)

    // All return value must be V
    @Suppress("UNCHECKED_CAST")
    protected fun <V : Any?> getOrDefault(key: Key<C, out V>): V {
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