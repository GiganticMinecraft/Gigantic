package click.seichi.gigantic.spirit

import click.seichi.gigantic.spirit.spawnreason.SpawnReason
import org.bukkit.Chunk

/**
 * @author unicroak
 */
abstract class Spirit(val spawnReason: SpawnReason, val spawnChunk: Chunk) {

    val lifeExpectancy
        get() = lifespan - count

    val isAlive
        get() = isSummoned && 0 < lifeExpectancy

    var isSummoned = false
        private set

    private var count = 0

    /**
     *  ticks
     *  if lifespan = -1 infinity
     **/
    abstract val lifespan: Int
    abstract val spiritType: SpiritType

    fun render() {
        if (!isAlive) {
            remove()
            return
        }
        if (spawnChunk.isLoaded)
            onRender()

        count++
    }

    fun spawn() {
        onSpawn()

        isSummoned = true
    }

    fun remove() {
        onRemove()

        isSummoned = false
    }

    open fun onRender() {}

    open fun onSpawn() {}

    open fun onRemove() {}

}