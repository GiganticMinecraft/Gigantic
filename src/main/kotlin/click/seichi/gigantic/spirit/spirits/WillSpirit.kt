package click.seichi.gigantic.spirit.spirits

import click.seichi.gigantic.extension.spawnColoredParticle
import click.seichi.gigantic.extension.spawnColoredParticleSpherically
import click.seichi.gigantic.message.lang.WillLang
import click.seichi.gigantic.sound.WillSound
import click.seichi.gigantic.spirit.Spirit
import click.seichi.gigantic.spirit.SpiritType
import click.seichi.gigantic.spirit.spawnreason.SpawnReason
import click.seichi.gigantic.spirit.spawnreason.WillSpawnReason
import click.seichi.gigantic.util.NoiseData
import click.seichi.gigantic.util.Random
import click.seichi.gigantic.will.Sensor
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
        location: Location,
        val willType: Will,
        targetPlayer: Player? = null
) : Spirit(spawnReason, location) {

    private val sensor = Sensor(
            location,
            {
                when {
                    it == null -> true
                    targetPlayer == null -> true
                    it.uniqueId == targetPlayer.uniqueId -> true
                    else -> false
                }
            },
            { player, count ->
                player ?: return@Sensor
                player.world.spawnColoredParticle(
                        player.location.clone().add(0.0, 1.0, 0.0).let { playerLocation ->
                            playerLocation.add(location.clone().subtract(playerLocation).multiply(Random.nextDouble()))
                        },
                        willType.color,
                        noiseData = NoiseData(0.1)
                )

                if (count % 10 == 0) {
                    WillSound.SENSE_SUB.play(player)
                }
            },
            { player ->
                player ?: return@Sensor
                WillLang.SENSED_WILL.sendTo(player, this)
                WillSound.SENSED.play(player)
                // TODO implements
            }
    )

    val willSize: WillSize = Random.nextWillSizeWithRegularity()

    override val lifespan = when (spawnReason) {
        WillSpawnReason.AWAKE, WillSpawnReason.RELEASE -> Sensor.DURATION + 1
        else -> throw IllegalStateException()
    }

    override val spiritType: SpiritType = SpiritType.WILL

    override fun onRender() {
        sensor.update()

        val renderingData = willSize.renderingData
        location.world.spawnColoredParticleSpherically(
                location,
                willType.color,
                if (lifeExpectancy < 10 * 20 && (renderingData.beatTiming == 0 || lifeExpectancy % renderingData.beatTiming == 0)) {
                    renderingData.min
                } else {
                    renderingData.max
                },
                renderingData.radius
        )
    }

    override fun onSpawn() {
        WillSound.SPAWN.play(location)
    }

    override fun onRemove() {
        WillSound.DEATH.play(location)
    }

}
