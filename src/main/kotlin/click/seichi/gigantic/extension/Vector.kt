package click.seichi.gigantic.extension

import org.bukkit.util.Vector

/**
 * @author tar0ss
 */

fun Vector.rotateXZ(degree: Int): Vector {
    return when (degree) {
        0, 360 -> this
        90 -> Vector(-this.z, this.y, this.x)
        180 -> Vector(-this.x, this.y, -this.z)
        270 -> Vector(this.z, this.y, -this.x)
        else -> throw IllegalArgumentException()
    }
}