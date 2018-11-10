package click.seichi.gigantic.spirit.spirits

import click.seichi.gigantic.animation.animations.WillSpiritAnimations
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.find
import click.seichi.gigantic.extension.manipulate
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.message.messages.WillMessages
import click.seichi.gigantic.sound.sounds.WillSpiritSounds
import click.seichi.gigantic.spirit.Spirit
import click.seichi.gigantic.spirit.SpiritType
import click.seichi.gigantic.spirit.spawnreason.SpawnReason
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
        val location: Location,
        val will: Will,
        targetPlayer: Player? = null,
        val willSize: WillSize = Random.nextWillSizeWithRegularity()
) : Spirit(spawnReason, location.chunk) {

    private val sensor = Sensor(
            location,
            { player ->
                player ?: return@Sensor false
                when {
                    player.find(CatalogPlayerCache.APTITUDE)?.has(will)?.not() ?: true -> false
                    targetPlayer == null -> true
                    player.uniqueId == targetPlayer.uniqueId -> true
                    else -> false
                }
            },
            { player, count ->
                player ?: return@Sensor
                WillSpiritAnimations.SENSE(will.color).link(player, location, meanY = 0.9)
                if (count % 10 == 0) {
                    WillSpiritSounds.SENSE_SUB.playOnly(player)
                }
            },
            { player ->
                player ?: return@Sensor
                WillMessages.SENSED_WILL(this).sendTo(player)
                WillSpiritSounds.SENSED.playOnly(player)
                player.manipulate(CatalogPlayerCache.MEMORY) {
                    it.add(will, willSize.memory.toLong())
                }
                PlayerMessages.MEMORY_SIDEBAR(
                        player.find(CatalogPlayerCache.MEMORY) ?: return@Sensor,
                        player.find(CatalogPlayerCache.APTITUDE) ?: return@Sensor,
                        false
                ).sendTo(player)
                remove()
            }
    )

    override val lifespan = -1

    override val spiritType: SpiritType = SpiritType.WILL

    override fun onRender() {
        sensor.update()
        WillSpiritAnimations.RENDER(willSize.renderingData, will.color, lifeExpectancy).start(location)
    }

    override fun onSpawn() {
        WillSpiritSounds.SPAWN.play(location)
    }

}
