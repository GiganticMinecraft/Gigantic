package click.seichi.gigantic.player

import click.seichi.gigantic.player.components.Level

/**
 * @author tar0ss
 */
enum class LockedFunction(
        private val unlockLevel: Int
) {
    MANA(10),
    ;

    fun isUnlocked(level: Level) = level.current >= unlockLevel
}