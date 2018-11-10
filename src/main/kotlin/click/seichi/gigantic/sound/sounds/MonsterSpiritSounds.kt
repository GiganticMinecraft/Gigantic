package click.seichi.gigantic.sound.sounds

import click.seichi.gigantic.sound.DetailedSound
import org.bukkit.Sound
import org.bukkit.SoundCategory

/**
 * @author tar0ss
 */
object MonsterSpiritSounds {

    val SPAWN = DetailedSound(
            Sound.ENTITY_WITHER_SPAWN,
            SoundCategory.HOSTILE,
            pitch = 1.4F,
            volume = 0.5F
    )

}