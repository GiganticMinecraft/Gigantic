package click.seichi.gigantic.sound

import org.bukkit.Sound
import org.bukkit.SoundCategory

/**
 * @author tar0ss
 */
object WillSound {

    val SENSE_SUB = SoundPlayer(
            Sound.ENTITY_VEX_AMBIENT,
            SoundCategory.BLOCKS,
            pitch = 0.5F
    )
    //player.location, Sound.ENTITY_VEX_AMBIENT, SoundCategory.BLOCKS, 1.0F, 0.5F)
    val SENSED = SoundPlayer(
            Sound.ITEM_ARMOR_EQUIP_DIAMOND,
            SoundCategory.BLOCKS,
            pitch = 0.3F
    )

    val SPAWN = SoundPlayer(
            Sound.ENTITY_ENDEREYE_DEATH,
            SoundCategory.BLOCKS,
            pitch = 1.8F
    )

    val DEATH = SoundPlayer(
            Sound.ENTITY_ENDEREYE_LAUNCH,
            SoundCategory.BLOCKS,
            pitch = 0.6F
    )

}