package click.seichi.gigantic.animation

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.schedule.Scheduler
import io.reactivex.Observable
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Entity
import java.util.concurrent.TimeUnit

/**
 * [Location] に 視覚的なエフェクトを表示する
 *
 * @param ticks 継続時間(ticks)
 * @param rendering tick毎の処理
 *
 * check this command
 * "/particle explode ~ ~ ~1 5 5 5 3 2000"
 *
 * @author tar0ss
 */
class Animation(
        private val ticks: Long,
        private val rendering: (Location, Long) -> Unit
) {

    fun start(location: Location) {
        Observable.interval(50L, TimeUnit.MILLISECONDS)
                .observeOn(Scheduler(Gigantic.PLUGIN, Bukkit.getScheduler()))
                .take(ticks)
                .subscribe {
                    val elapsedTicks = it
                    rendering(location, elapsedTicks)
                }
    }

    fun follow(entity: Entity,
               meanX: Double = 0.0,
               meanY: Double = 0.0,
               meanZ: Double = 0.0
    ) {
        Observable.interval(50L, TimeUnit.MILLISECONDS)
                .observeOn(Scheduler(Gigantic.PLUGIN, Bukkit.getScheduler()))
                .take(ticks)
                .subscribe {
                    if (!entity.isValid) return@subscribe
                    val elapsedTicks = it
                    rendering(entity.location.clone().add(
                            meanX,
                            meanY,
                            meanZ
                    ), elapsedTicks)
                }
    }

    /**
     * 吸収する
     * @param entity 吸収するエンティティ
     * @param startLocation 開始位置
     */
    fun absorb(entity: Entity, startLocation: Location) {
        Observable.interval(50L, TimeUnit.MILLISECONDS)
                .observeOn(Scheduler(Gigantic.PLUGIN, Bukkit.getScheduler()))
                .take(ticks)
                .subscribe {
                    if (!entity.isValid) return@subscribe
                    val elapsedTicks = it
                    val diff = entity.location.toVector().subtract(startLocation.toVector()).multiply(elapsedTicks.div(ticks.toDouble()))
                    val spawnLocation = startLocation.clone().add(diff)
                    rendering(spawnLocation, elapsedTicks)
                }
    }

}