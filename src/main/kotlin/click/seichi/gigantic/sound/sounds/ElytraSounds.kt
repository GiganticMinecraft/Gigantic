package click.seichi.gigantic.sound.sounds

import click.seichi.gigantic.sound.DetailedSound
import org.bukkit.Sound
import org.bukkit.SoundCategory

/**
 * @author tar0ss
 */
object ElytraSounds {

    val CHARGING = DetailedSound(
            Sound.ENTITY_TNT_PRIMED,
            SoundCategory.PLAYERS,
            0.1F,
            0.1F
    )

    val READY_TO_JUMP = DetailedSound(
            Sound.ENTITY_BAT_TAKEOFF,
            SoundCategory.PLAYERS,
            0.1F,
            0.1F
    )

    val JUMP = DetailedSound(
            Sound.ENTITY_ENDER_DRAGON_FLAP,
            SoundCategory.PLAYERS,
            0.1F,
            2.0F
    )


}