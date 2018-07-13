package click.seichi.gigantic.sound

import click.seichi.gigantic.util.Random
import org.bukkit.Sound
import org.bukkit.SoundCategory

/**
 * @author tar0ss
 */
object PlayerSounds {

    val INCREASE_EXP = SoundPlayer(
            Sound.ENTITY_EXPERIENCE_ORB_PICKUP,
            SoundCategory.PLAYERS,
            pitch = Random.nextGaussian(1.0, 0.2).toFloat()
    )

    val LEVEL_UP = SoundPlayer(
            Sound.ENTITY_PLAYER_LEVELUP,
            SoundCategory.PLAYERS,
            pitch = 0.8F
    )
}