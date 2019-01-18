package click.seichi.gigantic.spirit.spirits

import click.seichi.gigantic.animation.animations.WillSpiritAnimations
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.isCrust
import click.seichi.gigantic.extension.transform
import click.seichi.gigantic.message.messages.SideBarMessages
import click.seichi.gigantic.message.messages.WillMessages
import click.seichi.gigantic.relic.WillRelic
import click.seichi.gigantic.sound.sounds.WillSpiritSounds
import click.seichi.gigantic.spirit.Sensor
import click.seichi.gigantic.spirit.Spirit
import click.seichi.gigantic.spirit.SpiritType
import click.seichi.gigantic.spirit.spawnreason.SpawnReason
import click.seichi.gigantic.util.Random
import click.seichi.gigantic.will.Will
import click.seichi.gigantic.will.WillSize
import org.bukkit.Location
import org.bukkit.entity.Player


/**
 * @author unicroak
 * @author tar0ss
 */
class WillSpirit(
        spawnReason: SpawnReason,
        val location: Location,
        val will: Will,
        val targetPlayer: Player? = null,
        val willSize: WillSize = Random.nextWillSizeWithRegularity()
) : Spirit(spawnReason, location.chunk) {

    companion object {
        val relicTypeMap = WillRelic.values().groupBy { it.will }
    }

    var maxDistance: Double? = null
        private set

    private val sensor = Sensor(
            location,
            { player ->
                player ?: return@Sensor false
                when {
                    // 距離の制約(無ければ無限)
                    player.location.distance(location) >= maxDistance ?: Double.MAX_VALUE -> false
                    // 物理的な制約
                    location.block.isCrust -> false
                    // プレイヤーの制約
                    targetPlayer == null -> true
                    player.uniqueId == targetPlayer.uniqueId -> true
                    else -> false
                }
            },
            { player, count ->
                player ?: return@Sensor
                WillSpiritAnimations.SENSE(will.color).link(player, location, meanY = 0.9)
                if (count % 10 == 0L) {
                    WillSpiritSounds.SENSE_SUB.playOnly(player)
                }
            },
            { player ->
                player ?: return@Sensor
                WillMessages.SENSED_WILL(this).sendTo(player)
                WillSpiritSounds.SENSED.playOnly(player)
                addEthel(player, willSize.memory)
                SideBarMessages.MEMORY_SIDEBAR(
                        player,
                        false
                ).sendTo(player)
                remove()
            },
            {

            },
            60
    )

    private fun addEthel(player: Player, amount: Long) = player.transform(Keys.ETHEL_MAP[will]!!) { it + amount }

    override val lifespan = 60 * 20L

    override val spiritType: SpiritType = SpiritType.WILL

    private val speed = 6 + Random.nextGaussian()

    private val multiplier = 0.18 + Random.nextGaussian(variance = 0.05)

    override fun onRender() {
        // 意志がブロックの中に入った場合は終了
        if (location.block.isCrust) {
            remove()
            return
        }

        sensor.update()
        val renderLocation = location.clone().add(
                0.0,
                Math.sin(Math.toRadians(lifeExpectancy.times(speed) % 360.0)) * multiplier,
                0.0
        )
        WillSpiritAnimations.RENDER(willSize.renderingData, will.color, lifeExpectancy).start(renderLocation)
    }

    override fun onSpawn() {
        targetPlayer ?: return
        maxDistance = calcMaxDistance(targetPlayer)
        WillSpiritSounds.SPAWN.playOnly(targetPlayer)
    }

    private fun calcMaxDistance(player: Player): Double {
        // 持っているレリック
        val pRelicList = relicTypeMap[will]!!.filter { it.relic.has(player) }
        val allNum = pRelicList.fold(0L) { source, willRelic ->
            source + willRelic.relic.getDroppedNum(player)
        }
        // コンプリート
        return if (pRelicList.size == relicTypeMap[will]!!.size) {
            when {
                // かつ総数が100以上
                allNum >= 100 -> Double.MAX_VALUE
                allNum >= 25 -> 14.0
                else -> 8.0
            }
        } else {
            when {
                // 持っているレリックの種類が７つ以上
                pRelicList.size >= 7 -> 5.5
                pRelicList.size >= 5 -> 4.0
                else -> 3.0
            }
        }
    }

}
