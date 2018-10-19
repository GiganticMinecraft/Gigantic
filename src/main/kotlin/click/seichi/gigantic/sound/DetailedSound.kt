package click.seichi.gigantic.sound

import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.entity.Player

/**
 * @author tar0ss
 * @author unicroak
 */
class DetailedSound(
        private val sound: Sound,
        private val category: SoundCategory,
        private val volume: Float = 1.0F,
        private val pitch: Float = 1.0F
) {
    constructor(sound: Sound, category: SoundCategory, volume: Float, pitchLevel: Int)
            : this(sound, category, volume, when (pitchLevel) {
        0 -> 0.5F //F#
        1 -> 0.53F//G
        2 -> 0.56F//G#
        3 -> 0.6F//A
        4 -> 0.63F//A#
        5 -> 0.67F//B
        6 -> 0.7F//C
        7 -> 0.76F//C#
        8 -> 0.8F//D
        9 -> 0.84F//D#
        10 -> 0.9F//E
        11 -> 0.94F//F
        12 -> 1.0F//F#
        13 -> 1.06F//G
        14 -> 1.12F//G#
        15 -> 1.18F//A
        16 -> 1.26F//A#
        17 -> 1.34F//B
        18 -> 1.42F//C
        19 -> 1.5F//C#
        20 -> 1.6F//D
        21 -> 1.68F//D#
        22 -> 1.78F//E
        23 -> 1.88F//F
        24 -> 2.0F//F#
        else -> 0.0F
    })

    fun playOnly(player: Player) =
            player.playSound(
                    player.location,
                    sound,
                    category,
                    volume,
                    pitch
            )

    fun play(location: Location) = location.world.playSound(
            location,
            sound,
            category,
            volume,
            pitch
    )

}