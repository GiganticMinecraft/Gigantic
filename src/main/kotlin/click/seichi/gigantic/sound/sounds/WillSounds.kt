package click.seichi.gigantic.sound.sounds

import click.seichi.gigantic.sound.DetailedSound
import org.bukkit.Sound
import org.bukkit.SoundCategory

/**
 * @author tar0ss
 */
object WillSounds {

    val SENSE_SUB = DetailedSound(
            Sound.ENTITY_VEX_AMBIENT,
            SoundCategory.BLOCKS,
            pitch = 0.5F
    )


    val SENSED = DetailedSound(
            Sound.ITEM_ARMOR_EQUIP_DIAMOND,
            SoundCategory.BLOCKS,
            pitch = 0.3F
    )

    val SPAWN = DetailedSound(
            Sound.ENTITY_ENDER_EYE_DEATH,
            SoundCategory.BLOCKS,
            pitch = 1.8F
    )

    val DEATH = DetailedSound(
            Sound.ENTITY_ENDER_EYE_LAUNCH,
            SoundCategory.BLOCKS,
            pitch = 0.6F
    )

}