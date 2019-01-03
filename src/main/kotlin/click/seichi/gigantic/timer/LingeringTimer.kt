package click.seichi.gigantic.timer

import click.seichi.gigantic.Gigantic
import org.bukkit.scheduler.BukkitRunnable
import kotlin.properties.Delegates

/**
 * @author tar0ss
 */
open class LingeringTimer : Timer {

    var isCancelled = false

    var coolTime: Long by Delegates.notNull()

    var duration: Long by Delegates.notNull()

    var remainTimeToFire: Long = 0L
        private set
    var remainTimeToCool: Long = 0L
        private set

    private var onStart: () -> Unit = {}

    fun onStart(starting: () -> Unit): LingeringTimer {
        onStart = starting
        return this
    }

    private var onFire: (Long) -> Unit = {}

    fun onFire(firing: (Long) -> Unit): LingeringTimer {
        onFire = firing
        return this
    }

    private var onCompleteFire: () -> Unit = {}

    fun onCompleteFire(completeFiring: () -> Unit): LingeringTimer {
        onCompleteFire = completeFiring
        return this
    }

    private var onCooldown: (Long) -> Unit = {}

    fun onCooldown(cooling: (Long) -> Unit): LingeringTimer {
        onCooldown = cooling
        return this
    }

    private var onCompleteCooldown: () -> Unit = {}

    fun onCompleteCooldown(completeCooling: () -> Unit): LingeringTimer {
        onCompleteCooldown = completeCooling
        return this
    }

    override fun duringCoolTime() = remainTimeToFire != 0L

    fun duringFire() = remainTimeToCool != 0L

    override fun canStart() = !duringCoolTime()

    override fun start() {
        isCancelled = false
        remainTimeToCool = duration
        onStart()
        object : BukkitRunnable() {
            var elapsedSeconds = 0L
            override fun run() {
                // 前に分岐を置くことでExceptionでの無限ループ発生を回避
                if (elapsedSeconds++ >= duration || isCancelled) {
                    cancel()
                    remainTimeToCool = 0L
                    remainTimeToFire = coolTime
                    onCompleteFire()

                    object : BukkitRunnable() {
                        var elapsedSeconds = 0L
                        override fun run() {
                            // 前に分岐を置くことでExceptionでの無限ループ発生を回避
                            if (elapsedSeconds++ >= coolTime || isCancelled) {
                                cancel()
                                end()
                                return
                            }
                            remainTimeToFire = coolTime.minus(elapsedSeconds).plus(1)
                            onCooldown(remainTimeToFire)
                        }
                    }.runTaskTimer(Gigantic.PLUGIN, 0L, 20L)
                    return
                }
                remainTimeToCool = duration.minus(elapsedSeconds).plus(1)
                onFire(remainTimeToCool)
            }
        }.runTaskTimer(Gigantic.PLUGIN, 0L, 20L)
    }

    private fun end() {
        remainTimeToCool = 0L
        remainTimeToFire = 0L
        isCancelled = false
        onCompleteCooldown()
    }
}