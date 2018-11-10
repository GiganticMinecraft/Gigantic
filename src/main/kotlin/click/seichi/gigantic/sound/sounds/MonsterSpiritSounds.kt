package click.seichi.gigantic.sound.sounds

import click.seichi.gigantic.sound.DetailedSound
import org.bukkit.Sound
import org.bukkit.SoundCategory

/**
 * @author tar0ss
 */
object MonsterSpiritSounds {

    val SPAWN = DetailedSound(
            Sound.ENTITY_ELDER_GUARDIAN_HURT,
            SoundCategory.HOSTILE,
            pitch = 0.6F,
            volume = 0.5F
    )

    val SENSE_SUB = DetailedSound(
            Sound.ENTITY_ZOMBIE_HORSE_AMBIENT,
            SoundCategory.HOSTILE,
            pitch = 0.2F,
            volume = 0.5F
    )

    val DISAPPEAR = DetailedSound(
            Sound.ENTITY_ENDER_EYE_DEATH,
            SoundCategory.HOSTILE,
            pitch = 0.4F,
            volume = 0.5F
    )



}