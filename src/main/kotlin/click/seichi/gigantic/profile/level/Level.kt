package click.seichi.gigantic.profile.level

import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
abstract class Level {

    protected abstract val max: Int
    protected val levelRange
        get() = (1..max)

    abstract fun canLevelUp(currentLevel: Int, player: Player): Boolean
    abstract fun calcLevel(player: Player): Int
}