package click.seichi.gigantic.timer

import click.seichi.gigantic.extension.runTaskTimer
import kotlin.properties.Delegates

/**
 * @author tar0ss
 */
open class SimpleTimer : Timer {

    var isCancelled = false

    var remainTimeToFire: Long = 0L
        private set

    var coolTime: Long by Delegates.notNull()

    private var onStart: () -> Unit = {}

    fun onStart(starting: () -> Unit): SimpleTimer {
        onStart = starting
        return this
    }

    private var onCooldown: (Long) -> Unit = {}

    fun onCooldown(cooling: (Long) -> Unit): SimpleTimer {
        onCooldown = cooling
        return this
    }

    private var onCompleteCooldown: () -> Unit = {}

    fun onCompleteCooldown(completeCooling: () -> Unit): SimpleTimer {
        onCompleteCooldown = completeCooling
        return this
    }

    override fun duringCoolTime() = remainTimeToFire != 0L

    override fun canStart() = !duringCoolTime()

    override fun start() {
        isCancelled = false
        remainTimeToFire = coolTime
        onStart()
        if (isCancelled) {
            end()
            return
        }

        runTaskTimer(0L, 20L) { seconds ->
            if (seconds >= coolTime || isCancelled) {
                end()
                return@runTaskTimer false
            }
            remainTimeToFire = coolTime.minus(seconds).plus(1)
            onCooldown(remainTimeToFire)
            return@runTaskTimer true
        }
    }

    private fun end() {
        remainTimeToFire = 0L
        isCancelled = false
        onCompleteCooldown()
    }
}