package click.seichi.gigantic.spirit.spirits

import click.seichi.gigantic.animation.animations.WillSpiritAnimations
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.transform
import click.seichi.gigantic.message.messages.SideBarMessages
import click.seichi.gigantic.message.messages.WillMessages
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

    private val sensor = Sensor(
            location,
            { player ->
                player ?: return@Sensor false
                when {
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
                addMemory(player, will, willSize.memory)
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

    private fun addMemory(player: Player, will: Will, amount: Long) = player.transform(Keys.MEMORY_MAP[will]!!) { it + amount }

    override val lifespan = -1

    override val spiritType: SpiritType = SpiritType.WILL

    override fun onRender() {
        sensor.update()
        WillSpiritAnimations.RENDER(willSize.renderingData, will.color, lifeExpectancy).start(location)
    }

    override fun onSpawn() {
        targetPlayer ?: return
        WillSpiritSounds.SPAWN.playOnly(targetPlayer)
    }

}
