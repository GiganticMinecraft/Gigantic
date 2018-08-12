package click.seichi.gigantic.skill

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.schedule.Scheduler
import io.reactivex.Observable
import org.bukkit.Bukkit
import java.util.concurrent.TimeUnit

/**
 * @author tar0ss
 */
abstract class LingeringSkillTimer {

    abstract val duration: Long
    abstract val coolTime: Long

    var remainTimeToFire: Long = 0L
        private set

    var remainTimeToCool: Long = 0L
        private set

    private var onStart: () -> Unit = {}

    fun onStart(starting: () -> Unit): LingeringSkillTimer {
        onStart = starting
        return this
    }

    private var onFire: (Long) -> Unit = {}

    fun onFire(firing: (Long) -> Unit): LingeringSkillTimer {
        onFire = firing
        return this
    }

    private var onCompleteFire: () -> Unit = {}

    fun onCompleteFire(completeFiring: () -> Unit): LingeringSkillTimer {
        onCompleteFire = completeFiring
        return this
    }

    private var onCooldown: (Long) -> Unit = {}

    fun onCooldown(cooling: (Long) -> Unit): LingeringSkillTimer {
        onCooldown = cooling
        return this
    }

    private var onCompleteCooldown: () -> Unit = {}

    fun onCompleteCooldown(completeCooling: () -> Unit): LingeringSkillTimer {
        onCompleteCooldown = completeCooling
        return this
    }

    fun duringCoolTime() = remainTimeToFire != 0L

    fun duringFire() = remainTimeToCool != 0L

    fun canStart() = !duringCoolTime() && !duringFire()

    fun start() {
        remainTimeToCool = duration
        onStart()
        Observable.interval(1, TimeUnit.SECONDS)
                .take(duration, TimeUnit.SECONDS)
                .observeOn(Scheduler(Gigantic.PLUGIN, Bukkit.getScheduler()))
                .subscribe({ elapsedSeconds ->
                    remainTimeToCool = duration.minus(elapsedSeconds + 1)
                    onFire(remainTimeToCool)
                }, {}, {
                    remainTimeToCool = 0L
                    remainTimeToFire = coolTime
                    onCompleteFire()
                    Observable.interval(1, TimeUnit.SECONDS)
                            .take(coolTime, TimeUnit.SECONDS)
                            .observeOn(Scheduler(Gigantic.PLUGIN, Bukkit.getScheduler()))
                            .subscribe({ elapsedSeconds ->
                                remainTimeToFire = coolTime.minus(elapsedSeconds + 1)
                                onCooldown(remainTimeToFire)
                            }, {}, {
                                remainTimeToFire = 0L
                                onCompleteCooldown()
                            })
                })
    }


}