package click.seichi.gigantic.animation

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.schedule.Scheduler
import click.seichi.gigantic.util.Random
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
                .take(ticks)
                .observeOn(Scheduler(Gigantic.PLUGIN, Bukkit.getScheduler()))
                .subscribe({
                    val elapsedTicks = it
                    rendering(location, elapsedTicks)
                }, {}, {})
    }

    /**
     * 追従
     * @param entity 追従するエンティティ
     * @param meanX 相対距離
     * @param meanY 相対距離
     * @param meanZ 相対距離
     *
     */
    fun follow(entity: Entity,
               meanX: Double = 0.0,
               meanY: Double = 0.0,
               meanZ: Double = 0.0
    ) {
        Observable.interval(50L, TimeUnit.MILLISECONDS)
                .take(ticks)
                .observeOn(Scheduler(Gigantic.PLUGIN, Bukkit.getScheduler()))
                .subscribe({
                    if (!entity.isValid) return@subscribe
                    val elapsedTicks = it
                    rendering(entity.location.clone().add(
                            meanX,
                            meanY,
                            meanZ
                    ), elapsedTicks)
                }, {}, {})
    }

    /**
     * 吸収する
     * @param entity 吸収するエンティティ
     * @param startLocation 開始位置
     * @param meanX 相対距離
     * @param meanY 相対距離
     * @param meanZ 相対距離
     */
    fun absorb(
            entity: Entity,
            startLocation: Location,
            meanX: Double = 0.0,
            meanY: Double = 0.0,
            meanZ: Double = 0.0
    ) {
        Observable.interval(50L, TimeUnit.MILLISECONDS)
                .take(ticks)
                .observeOn(Scheduler(Gigantic.PLUGIN, Bukkit.getScheduler()))
                .subscribe({
                    if (!entity.isValid) return@subscribe
                    val elapsedTicks = it
                    val entityLocation = entity.location.clone().add(meanX, meanY, meanZ)
                    val diff = entityLocation.toVector().subtract(startLocation.toVector()).multiply(elapsedTicks.div(ticks.toDouble()))
                    val spawnLocation = startLocation.clone().add(diff)
                    rendering(spawnLocation, elapsedTicks)
                }, {}, {})
    }

    /**
     * 繋がる
     * @param entity 吸収するエンティティ
     * @param startLocation 開始位置
     * @param meanX 相対距離
     * @param meanY 相対距離
     * @param meanZ 相対距離
     */
    fun link(
            entity: Entity,
            startLocation: Location,
            meanX: Double = 0.0,
            meanY: Double = 0.0,
            meanZ: Double = 0.0
    ) {
        Observable.interval(50L, TimeUnit.MILLISECONDS)
                .take(ticks)
                .observeOn(Scheduler(Gigantic.PLUGIN, Bukkit.getScheduler()))
                .subscribe({
                    if (!entity.isValid) return@subscribe
                    val elapsedTicks = it
                    val entityLocation = entity.location.clone().add(meanX, meanY, meanZ)
                    val diff = entityLocation.toVector().subtract(startLocation.toVector()).multiply(Random.nextDouble())
                    val spawnLocation = startLocation.clone().add(diff)
                    rendering(spawnLocation, elapsedTicks)
                }, {}, {})
    }

    /**
     * 吸収される
     * @param entity 吸収されるエンティティ
     * @param startLocation 終点
     * @param meanX 相対距離
     * @param meanY 相対距離
     * @param meanZ 相対距離
     */
    fun exhaust(
            entity: Entity,
            startLocation: Location,
            meanX: Double = 0.0,
            meanY: Double = 0.0,
            meanZ: Double = 0.0
    ) {
        Observable.interval(50L, TimeUnit.MILLISECONDS)
                .take(ticks)
                .observeOn(Scheduler(Gigantic.PLUGIN, Bukkit.getScheduler()))
                .subscribe({
                    if (!entity.isValid) return@subscribe
                    val elapsedTicks = it
                    val entityLocation = entity.location.clone().add(meanX, meanY, meanZ)
                    val diff = entityLocation.toVector().subtract(startLocation.toVector()).multiply(1 - elapsedTicks.div(ticks.toDouble()))
                    val spawnLocation = startLocation.clone().add(diff)
                    rendering(spawnLocation, elapsedTicks)
                }, {}, {})
    }

}