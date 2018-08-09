package click.seichi.gigantic.cache.manipulator.manipulators

import click.seichi.gigantic.cache.cache.Cache
import click.seichi.gigantic.cache.cache.PlayerCache
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.Manipulator
import click.seichi.gigantic.config.PlayerLevelConfig

/**
 * @author tar0ss
 */
class Level : Manipulator<Level, PlayerCache> {

    companion object {
        val MAX = PlayerLevelConfig.MAX
    }

    var current: Int = 0
        private set

    var exp: Long = 0L
        private set

    override fun from(cache: Cache<PlayerCache>): Level? {
        exp = cache.find(Keys.EXP) ?: return null
        current = cache.find(Keys.LEVEL) ?: return null
        return this
    }

    override fun set(cache: Cache<PlayerCache>): Boolean {
        return cache.offer(Keys.EXP, exp) && cache.offer(Keys.LEVEL, current)
    }

    fun calculate(
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
            exp >= PlayerLevelConfig.LEVEL_MAP[nextLevel] ?: Long.MAX_VALUE

}