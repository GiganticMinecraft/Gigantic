package click.seichi.gigantic.player.components

import click.seichi.gigantic.config.PlayerLevelConfig
import click.seichi.gigantic.config.PlayerLevelConfig.LEVEL_MAP

class Level {

    companion object {
        val MAX = PlayerLevelConfig.MAX
    }

    var current: Int = 0
        private set

    var exp: Long = 0L
        private set

    fun updateLevel(
            nextExp: Long,
            onLevelUp: (Int) -> Unit
    ): Boolean {
        exp = nextExp
        var isLevelUp = false
        while (canLevelUp(current + 1, exp)) {
            if (current + 1 > MAX) {
                break
            }
            current++
            isLevelUp = true
            onLevelUp(current)
        }

        return isLevelUp
    }

    private fun canLevelUp(nextLevel: Int, exp: Long) =
            exp >= LEVEL_MAP[nextLevel] ?: Long.MAX_VALUE

}
