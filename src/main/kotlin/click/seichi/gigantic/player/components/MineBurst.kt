package click.seichi.gigantic.player.components

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.schedule.Scheduler
import io.reactivex.Observable
import org.bukkit.Bukkit
import java.util.concurrent.TimeUnit

/**
 * @author tar0ss
 */
class MineBurst {

    // 発動時間(秒)
    val duration = 5.0
    // クールタイム(秒)
    val coolTime = 60.0
    // 再使用までの残り時間(秒)
    var remainTimeToFire = 0.0
    // クールタイムに入るまでの残り時間(秒)
    var remainTimeToCool = 0.0

    // クールタイム中かどうか
    fun duringCoolTime() = remainTimeToFire != 0.0

    // 発動中かどうか
    fun duringFire() = remainTimeToCool != 0.0

    fun canFire() = !duringCoolTime() && !duringFire()

    fun fire(onStart: () -> Unit, onNext: () -> Unit, onComplete: () -> Unit) {
        remainTimeToCool = duration
        onStart()
        Observable.interval(1, TimeUnit.MILLISECONDS)
                .take(duration.toLong(), TimeUnit.SECONDS)
                .observeOn(Scheduler(Gigantic.PLUGIN, Bukkit.getScheduler()))
                .subscribe({ elapsedTime ->
                    if (elapsedTime % 20L != 0L) return@subscribe
                    remainTimeToCool = duration.minus(elapsedTime.div(1000.0))
                    onNext()
                }, {}, {
                    remainTimeToCool = 0.0
                    onComplete()
                    Observable.interval(1, TimeUnit.MILLISECONDS)
                            .take(coolTime.toLong(), TimeUnit.SECONDS)
                            .observeOn(Scheduler(Gigantic.PLUGIN, Bukkit.getScheduler()))
                            .subscribe({ elapsedTime ->
                                if (elapsedTime % 20L != 0L) return@subscribe
                                remainTimeToFire = coolTime.minus(elapsedTime.div(1000.0))
                                onNext()
                            }, {}, {
                                remainTimeToFire = 0.0
                                onComplete()
                            })
                })
    }
}