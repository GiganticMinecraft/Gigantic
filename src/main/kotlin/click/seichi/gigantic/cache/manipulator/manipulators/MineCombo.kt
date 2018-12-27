package click.seichi.gigantic.cache.manipulator.manipulators

import click.seichi.gigantic.cache.cache.Cache
import click.seichi.gigantic.cache.cache.PlayerCache
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.Manipulator
import click.seichi.gigantic.player.skill.SkillParameters.COMBO_CONTINUATION_SECONDS
import click.seichi.gigantic.player.skill.SkillParameters.COMBO_DECREASE_INTERVAL
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

    fun combo(count: Long): Long {
        val now = System.currentTimeMillis()
        val elapsedTime = now - lastComboTime
        if (canContinue(elapsedTime)) {
            // コンボ継続可能な場合
            currentCombo += count
            if (currentCombo > maxCombo) {
                maxCombo = currentCombo
            }
        } else {
            // コンボ継続不能な場合
            // 時間経過[COMBO_DECREASE_INTERVAL]秒ごとにコンボ1割減少する
            // 減少パーセント
            val decreaseRate = elapsedTime.div(1000)
                    .minus(COMBO_CONTINUATION_SECONDS)
                    .div(COMBO_DECREASE_INTERVAL)
                    .plus(1)
                    .times(10)
                    .coerceAtMost(100)
            val decreaseCombo = currentCombo.toDouble()
                    .div(100.0)
                    .times(decreaseRate).toInt()
            if (decreaseRate >= 100) {
                currentCombo = count
            } else {
                currentCombo -= decreaseCombo + count
            }
        }

        // 更新
        lastComboTime = now
        return currentCombo
    }

    private fun canContinue(elapsedTime: Long): Boolean {
        return COMBO_CONTINUATION_SECONDS > elapsedTime.div(1000)
    }

}