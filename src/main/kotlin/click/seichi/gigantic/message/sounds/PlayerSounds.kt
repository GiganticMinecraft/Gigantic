package click.seichi.gigantic.message.sounds

import click.seichi.gigantic.message.WorldSound
import click.seichi.gigantic.util.DetailedSound
import click.seichi.gigantic.util.Random
import org.bukkit.Sound
import org.bukkit.SoundCategory

/**
 * @author tar0ss
 */
object PlayerSounds {
    val OBTAIN_EXP
        get() = WorldSound(
                DetailedSound(
                        Sound.ENTITY_EXPERIENCE_ORB_PICKUP,
                        SoundCategory.PLAYERS,
                        pitch = Random.nextGaussian(1.0, 0.2).toFloat(),
                        volume = 0.2F
                )
        )

    val LEVEL_UP = WorldSound(
            DetailedSound(
                    Sound.ENTITY_PLAYER_LEVELUP,
                    SoundCategory.PLAYERS,
                    pitch = 0.8F,
                    volume = 0.5F
            )
    )
}