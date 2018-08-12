package click.seichi.gigantic.skill.timer

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.schedule.Scheduler
import click.seichi.gigantic.skill.LingeringSkill
import io.reactivex.Observable
import org.bukkit.Bukkit
import java.util.concurrent.TimeUnit

/**
 * @author tar0ss
 */
open class LingeringSkillTimer(skill: LingeringSkill) : SkillTimer {

    var isCancelled = false

    val duration: Long = skill.duration
    val coolTime: Long = skill.coolTime

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

    override fun duringCoolTime() = remainTimeToFire != 0L

    fun duringFire() = remainTimeToCool != 0L

    override fun canStart() = !duringCoolTime()


    override fun start() {
        remainTimeToCool = duration
        onStart()
        if (isCancelled) {
            return
        }
        Observable.interval(1, TimeUnit.SECONDS)
                .takeWhile {
                    it < duration && !isCancelled
                }.observeOn(Scheduler(Gigantic.PLUGIN, Bukkit.getScheduler()))
                .subscribe({ elapsedSeconds ->
                    remainTimeToCool = duration.minus(elapsedSeconds + 1)
                    onFire(remainTimeToCool)
                }, {}, {
                    remainTimeToCool = 0L
                    remainTimeToFire = coolTime
                    onCompleteFire()
                    if (isCancelled) {
                        end()
                        return@subscribe
                    }
                    Observable.interval(1, TimeUnit.SECONDS)
                            .takeWhile {
                                it < coolTime && !isCancelled
                            }.observeOn(Scheduler(Gigantic.PLUGIN, Bukkit.getScheduler()))
                            .subscribe({ elapsedSeconds ->
                                remainTimeToFire = coolTime.minus(elapsedSeconds + 1)
                                onCooldown(remainTimeToFire)
                            }, {}, {
                                end()
                            })
                })
    }

    private fun end() {
        remainTimeToCool = 0L
        remainTimeToFire = 0L
        isCancelled = false
        onCompleteCooldown()
    }
}