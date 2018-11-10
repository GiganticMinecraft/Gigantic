package click.seichi.gigantic.sound.sounds

import click.seichi.gigantic.sound.DetailedSound
import org.bukkit.Sound
import org.bukkit.SoundCategory

/**
 * @author tar0ss
 */
object BattleSounds {

    val WIN = DetailedSound(
            Sound.ENTITY_WITHER_DEATH,
            SoundCategory.HOSTILE,
            pitch = 2.0F,
            volume = 0.2F
    )

    val START = DetailedSound(
            Sound.ENTITY_WITHER_SPAWN,
            SoundCategory.HOSTILE,
            pitch = 2.0F,
            volume = 0.2F
    )

}