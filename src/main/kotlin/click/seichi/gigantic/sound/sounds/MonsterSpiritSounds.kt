package click.seichi.gigantic.sound.sounds

import click.seichi.gigantic.sound.DetailedSound
import org.bukkit.Sound
import org.bukkit.SoundCategory

/**
 * @author tar0ss
 */
object MonsterSpiritSounds {

    val SPAWN = DetailedSound(
            Sound.ENTITY_WITHER_AMBIENT,
            SoundCategory.HOSTILE,
            pitch = 1.7F,
            volume = 0.4F
    )

}