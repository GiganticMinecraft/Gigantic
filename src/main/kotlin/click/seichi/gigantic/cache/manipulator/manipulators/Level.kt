package click.seichi.gigantic.cache.manipulator.manipulators

import click.seichi.gigantic.cache.cache.Cache
import click.seichi.gigantic.cache.cache.PlayerCache
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.Manipulator
import click.seichi.gigantic.config.PlayerLevelConfig
import java.math.BigDecimal

/**
 * @author tar0ss
 */
class Level : Manipulator<Level, PlayerCache> {

    companion object {
        val MAX = PlayerLevelConfig.MAX
    }

    var current: Int = 1
        private set

    override fun from(cache: Cache<PlayerCache>): Level? {
        current = cache.getOrPut(Keys.LEVEL)
        return this
    }

    override fun set(cache: Cache<PlayerCache>): Boolean {
        cache.offer(Keys.LEVEL, current)
        return true
    }

    fun calculate(
            nextExp: BigDecimal
    ): Boolean {
        var isLevelUp = false
        while (canLevelUp(current + 1, nextExp)) {
            if (current + 1 > MAX) {
                break
            }
            current++
            isLevelUp = true
        }

        return isLevelUp
    }

    private fun canLevelUp(nextLevel: Int, exp: BigDecimal): Boolean {
        val nextExp = PlayerLevelConfig.LEVEL_MAP[nextLevel] ?: return false
        return exp >= nextExp
    }

}