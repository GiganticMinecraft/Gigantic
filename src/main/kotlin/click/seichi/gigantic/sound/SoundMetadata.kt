package click.seichi.gigantic.sound

import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
data class SoundMetadata(
        private val sound: Sound,
        private val category: SoundCategory,
        private val volume: Float = 1.0F,
        private val pitch: Float = 1.0F
) {

    fun play(player: Player) =
            player.playSound(player.location, sound, category, volume, pitch)

    fun play(location: Location) =
            location.world.playSound(location, sound, category, volume, pitch)
}