package click.seichi.gigantic.cache.manipulator.manipulators

import click.seichi.gigantic.cache.cache.Cache
import click.seichi.gigantic.cache.cache.PlayerCache
import click.seichi.gigantic.cache.manipulator.Manipulator

/**
 * @author tar0ss
 */
class MineCombo : Manipulator<MineCombo, PlayerCache> {
    override fun from(cache: Cache<PlayerCache>): MineCombo? {
        return this
    }

    override fun set(cache: Cache<PlayerCache>): Boolean {
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