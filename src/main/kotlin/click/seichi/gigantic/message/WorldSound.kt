package click.seichi.gigantic.message

import click.seichi.gigantic.util.DetailedSound
import org.bukkit.Location
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class WorldSound(
        private val detailedSound: DetailedSound
) : Message {

    override fun sendTo(player: Player) =
            player.playSound(
                    player.location,
                    detailedSound.sound,
                    detailedSound.category,
                    detailedSound.volume,
                    detailedSound.pitch
            )

    fun sendTo(location: Location) =
            location.world.playSound(
                    location,
                    detailedSound.sound,
                    detailedSound.category,
                    detailedSound.volume,
                    detailedSound.pitch
            )
}