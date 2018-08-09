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
    private val keyMap: MutableMap<Key<C, out Any>, Any> = mutableMapOf()

    private val manipulatorMap: MutableMap<Class<out Any>, Any> = mutableMapOf()

    fun <M : Manipulator<M, C>> register(clazz: Class<M>) {
        manipulatorMap[clazz] = clazz.newInstance()
    }

    @Suppress("UNCHECKED_CAST")
    fun <M : Manipulator<M, C>> find(clazz: Class<M>) = (manipulatorMap[clazz] as M?)?.from(this)

    fun <M : Manipulator<M, C>> offer(manipulator: M) = manipulator.set(this)

    @Suppress("UNCHECKED_CAST")
    fun <M : Manipulator<M, C>> manipulate(clazz: Class<M>, manipulating: (M) -> Unit): Boolean {
        return find(clazz)?.apply(manipulating)?.set(this) ?: false
    }

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
        return keyMap.getOrDefault(key, key.default) as V
    }

    // All return value must be V
    @Suppress("UNCHECKED_CAST")
    fun <V : Any> find(key: Key<C, out V>): V? {
        return keyMap[key] as V?
    }

    fun <V : Any> registerKey(key: Key<C, out V>, value: V? = null) {
        keyMap.putIfAbsent(key, value ?: key.default)
    }

    fun <V : Any> offer(key: Key<C, V>, value: V): Boolean {
        if (!key.satisfyWith(value)) return false
        keyMap.replace(key, value) ?: return false
        return true
    }

    fun <V : Any> transform(key: Key<C, V>, transforming: (V) -> V): Boolean {
        val oldValue = find(key) ?: return false
        val newValue = transforming(oldValue)
        return offer(key, newValue)
    }

}