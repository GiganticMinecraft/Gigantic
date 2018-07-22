package click.seichi.gigantic.sound.sounds

import click.seichi.gigantic.sound.DetailedSound
import org.bukkit.Sound
import org.bukkit.SoundCategory

/**
 * @author tar0ss
 */
object BattleSounds {

    val WIN = DetailedSound(
            Sound.ENTITY_ENDERDRAGON_DEATH,
            SoundCategory.HOSTILE,
            pitch = 1.4F,
            volume = 0.5F
    )

}