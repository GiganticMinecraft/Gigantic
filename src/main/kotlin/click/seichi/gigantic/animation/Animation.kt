package click.seichi.gigantic.animation

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.util.Random
import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.scheduler.BukkitRunnable

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
        val ticks: Long,
        private val rendering: (Location, Long) -> Unit
) {

    fun start(location: Location) {
        object : BukkitRunnable() {
            var t = 0L
            override fun run() {
                rendering(location.clone(), t++)
                if (t > ticks) cancel()
            }
        }.runTaskTimer(Gigantic.PLUGIN, 0L, 1L)
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
        object : BukkitRunnable() {
            var t = 0L
            override fun run() {
                if (!entity.isValid) {
                    cancel()
                    return
                }
                rendering(entity.location.clone().add(
                        meanX,
                        meanY,
                        meanZ
                ), t++)
                if (t > ticks) cancel()
            }
        }.runTaskTimer(Gigantic.PLUGIN, 0L, 1L)
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
        object : BukkitRunnable() {
            var t = 0L
            override fun run() {
                if (!entity.isValid) {
                    cancel()
                    return
                }
                val entityLocation = entity.location.clone().add(meanX, meanY, meanZ)
                val diff = entityLocation.toVector().subtract(startLocation.toVector()).multiply(t.div(ticks.toDouble()))
                val spawnLocation = startLocation.clone().add(diff)
                rendering(spawnLocation, t)
                t++
                if (t > ticks) cancel()
            }
        }.runTaskTimer(Gigantic.PLUGIN, 0L, 1L)
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
        object : BukkitRunnable() {
            var t = 0L
            override fun run() {
                if (!entity.isValid) {
                    cancel()
                    return
                }
                val entityLocation = entity.location.clone().add(meanX, meanY, meanZ)
                val diff = entityLocation.toVector().subtract(startLocation.toVector()).multiply(Random.nextDouble())
                val spawnLocation = startLocation.clone().add(diff)
                rendering(spawnLocation, t)
                t++
                if (t > ticks) cancel()
            }
        }.runTaskTimer(Gigantic.PLUGIN, 0L, 1L)
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
        object : BukkitRunnable() {
            var t = 0L
            override fun run() {
                if (!entity.isValid) {
                    cancel()
                    return
                }
                val entityLocation = entity.location.clone().add(meanX, meanY, meanZ)
                val diff = entityLocation.toVector().subtract(startLocation.toVector()).multiply(1.0 - t.div(ticks.toDouble()))
                val spawnLocation = startLocation.clone().add(diff)
                rendering(spawnLocation, t)
                if (t++ > ticks) cancel()
            }
        }.runTaskTimer(Gigantic.PLUGIN, 0L, 1L)
    }

}