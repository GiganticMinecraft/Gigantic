package click.seichi.gigantic.animation

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.schedule.Scheduler
import io.reactivex.Observable
import org.bukkit.Bukkit
import org.bukkit.Location
import java.util.concurrent.TimeUnit

/**
 * @author tar0ss
 */
class Animation(
        private val ticks: Long,
        private val rendering: (Location, Long) -> Unit
) {

    fun start(location: Location) {
        Observable.interval(50L, TimeUnit.MILLISECONDS)
                .observeOn(Scheduler(Gigantic.PLUGIN, Bukkit.getScheduler()))
                .take(ticks * 50L)
                .subscribe {
                    val ticks = it.div(50L)
                    rendering(location, ticks)
                }
    }
}