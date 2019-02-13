package click.seichi.gigantic.spirit.spirits

import click.seichi.gigantic.animation.animations.WillSpiritAnimations
import click.seichi.gigantic.event.events.SenseEvent
import click.seichi.gigantic.extension.isCrust
import click.seichi.gigantic.extension.relationship
import click.seichi.gigantic.extension.updateSideBar
import click.seichi.gigantic.message.messages.WillMessages
import click.seichi.gigantic.player.Defaults
import click.seichi.gigantic.sound.sounds.WillSpiritSounds
import click.seichi.gigantic.spirit.Sensor
import click.seichi.gigantic.spirit.Spirit
import click.seichi.gigantic.spirit.SpiritType
import click.seichi.gigantic.spirit.spawnreason.SpawnReason
import click.seichi.gigantic.util.Random
import click.seichi.gigantic.will.Will
import click.seichi.gigantic.will.WillSize
import org.bukkit.Bukkit
import org.bukkit.GameMode
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

    private val sensor = Sensor(
            location,
            { player ->
                player ?: return@Sensor false
                when {
                    // 距離の制約(無ければ無限)
                    player.location.distance(location) >= player.relationship(will).maxDistance -> false
                    // 物理的な制約
                    location.block.isCrust -> false
                    // ゲームモード制約
                    player.gameMode != GameMode.SURVIVAL -> false
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
                will.addEthel(player, willSize.memory)
                Bukkit.getPluginManager().callEvent(SenseEvent(will, player, willSize.memory))
                player.updateSideBar()
                remove()
            },
            {

            },
            60
    )

    override val lifespan = 60 * 20L

    override val spiritType: SpiritType = SpiritType.WILL

    private val speed = 6 + Random.nextGaussian()

    private val multiplier = 0.18 + Random.nextGaussian(variance = 0.05)

    private var deathCount = 0L

    override fun onRender() {
        targetPlayer ?: return
        // 意志がブロックの中に入った場合は終了前処理
        if (location.block.isCrust) {
            deathCount++
            if (deathCount > Defaults.WILL_SPIRIT_DEATH_DURATION) {
                WillSpiritSounds.DEATH.playOnly(targetPlayer)
                remove()
                return
            }
        } else deathCount = 0L

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
        WillSpiritSounds.SPAWN.playOnly(targetPlayer)
    }

}
