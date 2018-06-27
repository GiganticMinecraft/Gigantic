package click.seichi.gigantic.util

import org.bukkit.util.Vector

/**
 * @author unicroak
 */
data class NoiseData(
        val size: Vector,
        val noiser: (Double) -> Double = defaultNoiser
) {

    companion object {
        private val defaultNoiser: (Double) -> Double = { it * Random.nextDouble() * Random.nextSign().toDouble() }
    }

    constructor(
            size: Double,
            noiser: (Double) -> Double = defaultNoiser
    ) : this(Vector(size, size, size), noiser)

    constructor(
            sizeX: Double = 0.0,
            sizeY: Double = 0.0,
            sizeZ: Double = 0.0,
            noiser: (Double) -> Double = defaultNoiser
    ) : this(Vector(sizeX, sizeY, sizeZ), noiser)

}