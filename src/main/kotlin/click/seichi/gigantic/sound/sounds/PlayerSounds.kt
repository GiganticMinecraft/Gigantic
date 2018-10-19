package click.seichi.gigantic.sound.sounds

import click.seichi.gigantic.sound.DetailedSound
import org.bukkit.Sound
import org.bukkit.SoundCategory

/**
 * @author tar0ss
 */
object PlayerSounds {
    val OBTAIN_EXP = { combo: Long ->
        DetailedSound(
                Sound.ENTITY_EXPERIENCE_ORB_PICKUP,
                SoundCategory.PLAYERS,
                pitchLevel = (combo % 25).toInt(),
                volume = 0.2F
        )
    }

    val LEVEL_UP = DetailedSound(
            Sound.ENTITY_PLAYER_LEVELUP,
            SoundCategory.PLAYERS,
            pitch = 0.8F,
            volume = 1.0F
    )

}