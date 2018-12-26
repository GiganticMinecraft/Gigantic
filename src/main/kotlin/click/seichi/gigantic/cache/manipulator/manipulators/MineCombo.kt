package click.seichi.gigantic.cache.manipulator.manipulators

import click.seichi.gigantic.cache.cache.Cache
import click.seichi.gigantic.cache.cache.PlayerCache
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.Manipulator
import kotlin.properties.Delegates

/**
 * @author tar0ss
 */
class MineCombo : Manipulator<MineCombo, PlayerCache> {

    var maxCombo: Long by Delegates.notNull()
        private set

    var currentCombo: Long = 0L
        private set

    private var lastComboTime = System.currentTimeMillis()

    override fun from(cache: Cache<PlayerCache>): MineCombo? {
        maxCombo = cache.getOrPut(Keys.MAX_COMBO)
        currentCombo = cache.getOrPut(Keys.MINE_COMBO)
        lastComboTime = cache.getOrPut(Keys.LAST_COMBO_TIME)
        return this
    }

    override fun set(cache: Cache<PlayerCache>): Boolean {
        cache.offer(Keys.MAX_COMBO, maxCombo)
        cache.offer(Keys.MINE_COMBO, currentCombo)
        cache.offer(Keys.LAST_COMBO_TIME, lastComboTime)
        return true
    }

    companion object {
        const val COMBO_CONTINUATION_SECONDS = 3L
    }

    fun combo(count: Long): Long {
        if (canContinue()) {
            currentCombo += count
            if (currentCombo > maxCombo) {
                maxCombo = currentCombo
            }
        } else {
            currentCombo = count
        }
        updateComboTime()
        return currentCombo
    }

    private fun updateComboTime() {
        lastComboTime = System.currentTimeMillis()
    }

    private fun canContinue(): Boolean {
        val now = System.currentTimeMillis()
        val diff = now - lastComboTime
        return COMBO_CONTINUATION_SECONDS > diff.div(1000)
    }
}