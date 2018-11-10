package click.seichi.gigantic.util

import org.bukkit.entity.Entity
import kotlin.math.roundToInt

/**
 * @author tar0ss
 */
enum class CardinalDirection(val yaw: Float) {
    NORTH(180.0F), EAST(270.0F), SOUTH(0.0F), WEST(90.0F);

    //degは南向きを正とした時の角度

    companion object {
        fun getCardinalDirection(entity: Entity): CardinalDirection {
            var rot = (entity.location.yaw + 180) % 360
            if (rot < 0) rot += 360
            return when (rot.roundToInt()) {
                in 0 until 45 -> NORTH
                in 45 until 135 -> EAST
                in 135 until 225 -> SOUTH
                in 225 until 315 -> WEST
                else -> NORTH
            }

        }
    }
}