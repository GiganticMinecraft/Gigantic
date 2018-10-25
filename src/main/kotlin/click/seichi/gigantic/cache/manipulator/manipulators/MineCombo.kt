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

    override fun from(cache: Cache<PlayerCache>): MineCombo? {
        maxCombo = cache.getOrPut(Keys.MAX_COMBO)
        return this
    }

    override fun set(cache: Cache<PlayerCache>): Boolean {
        cache.offer(Keys.MAX_COMBO, maxCombo)
        return true
    }

    var currentCombo: Long = 0L
        private set

    private var lastComboTime = System.currentTimeMillis()

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

    fun reset(count: Long) {
        currentCombo = count
        updateComboTime()
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