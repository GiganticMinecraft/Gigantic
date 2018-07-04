package click.seichi.gigantic.sound

import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
data class SoundPlayer(
        val sound: Sound,
        val category: SoundCategory,
        val volume: Float = 1.0F,
        val pitch: Float = 1.0F
) {

    fun play(player: Player) =
            player.playSound(player.location, sound, category, volume, pitch)

    fun play(location: Location) =
            location.world.playSound(location, sound, category, volume, pitch)
}