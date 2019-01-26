package click.seichi.gigantic.spirit

import click.seichi.gigantic.spirit.spawnreason.SpawnReason
import org.bukkit.Chunk

/**
 * @author unicroak
 * @author tar0ss
 */
abstract class Spirit(val spawnReason: SpawnReason, val spawnChunk: Chunk) {

    val lifeExpectancy
        get() = lifespan - count

    val isAlive
        get() = isSummoned && 0 < lifeExpectancy

    var isSummoned = false
        private set

    private var count = 0L

    // ticks
    abstract val lifespan: Long
    abstract val spiritType: SpiritType

    fun render() {
        count++
        if (!isAlive) {
            remove()
            return
        }
        onRender()
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