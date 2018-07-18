package click.seichi.gigantic.util

import org.bukkit.Sound
import org.bukkit.SoundCategory

/**
 * @author tar0ss
 */
data class DetailedSound(
        val sound: Sound,
        val category: SoundCategory,
        val volume: Float = 1.0F,
        val pitch: Float = 1.0F
)