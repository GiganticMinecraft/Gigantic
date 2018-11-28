package click.seichi.gigantic.timer

import click.seichi.gigantic.Gigantic
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable
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
        remainTimeToFire = coolTime
        onStart()
        if (isCancelled) {
            end()
            return
        }

        object : BukkitRunnable() {
            var elapsedSeconds = 0L
            override fun run() {
                remainTimeToFire = coolTime.minus(elapsedSeconds)
                onCooldown(remainTimeToFire)
                Bukkit.getServer().consoleSender.sendMessage("hi!$remainTimeToFire")

                if (elapsedSeconds++ < coolTime && !isCancelled) return
                cancel()
                end()
            }
        }.runTaskTimer(Gigantic.PLUGIN, 0L, 20L)
    }

    private fun end() {
        remainTimeToFire = 0L
        isCancelled = false
        onCompleteCooldown()
    }
}