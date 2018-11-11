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
        remainTimeToCool = duration
        onStart()
        object : BukkitRunnable() {
            var elapsedSeconds = 0L
            override fun run() {
                remainTimeToCool = duration.minus(elapsedSeconds)
                onFire(remainTimeToCool)
                elapsedSeconds++

                if (elapsedSeconds <= duration && !isCancelled) return
                cancel()

                remainTimeToCool = 0L
                remainTimeToFire = coolTime
                onCompleteFire()

                object : BukkitRunnable() {
                    var elapsedSeconds = 0L
                    override fun run() {
                        remainTimeToFire = coolTime.minus(elapsedSeconds)
                        onCooldown(remainTimeToFire)
                        elapsedSeconds++
                        if (elapsedSeconds <= coolTime && !isCancelled) return
                        cancel()
                        end()
                    }
                }.runTaskTimer(Gigantic.PLUGIN, 0L, 20L)
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