package click.seichi.gigantic.message.sounds

import click.seichi.gigantic.message.WorldSound
import click.seichi.gigantic.util.DetailedSound
import org.bukkit.Sound
import org.bukkit.SoundCategory

/**
 * @author tar0ss
 */
object WillSounds {
    val SENSE_SUB = WorldSound(
            DetailedSound(
                    Sound.ENTITY_VEX_AMBIENT,
                    SoundCategory.BLOCKS,
                    pitch = 0.5F
            )
    )

    val SENSED = WorldSound(
            DetailedSound(
                    Sound.ITEM_ARMOR_EQUIP_DIAMOND,
                    SoundCategory.BLOCKS,
                    pitch = 0.3F
            )
    )
    val SPAWN = WorldSound(
            DetailedSound(
                    Sound.ENTITY_ENDEREYE_DEATH,
                    SoundCategory.BLOCKS,
                    pitch = 1.8F
            )
    )
    val DEATH = WorldSound(
            DetailedSound(
                    Sound.ENTITY_ENDEREYE_LAUNCH,
                    SoundCategory.BLOCKS,
                    pitch = 0.6F
            )
    )

}