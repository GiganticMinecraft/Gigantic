package click.seichi.gigantic.sound.sounds

import click.seichi.gigantic.sound.DetailedSound
import click.seichi.gigantic.util.Random
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
                volume = 0.1F
        )
    }

    val LEVEL_UP = DetailedSound(
            Sound.ENTITY_PLAYER_LEVELUP,
            SoundCategory.PLAYERS,
            pitch = 0.8F,
            volume = 1.0F
    )

    val TELEPORT_AFK = DetailedSound(
            Sound.ITEM_CHORUS_FRUIT_TELEPORT,
            SoundCategory.BLOCKS,
            pitch = 1.4F,
            volume = 1.0F
    )

    val TOGGLE = DetailedSound(
            Sound.BLOCK_LEVER_CLICK,
            SoundCategory.BLOCKS,
            pitch = 1.4F,
            volume = 1.0F
    )

    val FAIL = DetailedSound(
            Sound.BLOCK_DISPENSER_FAIL,
            SoundCategory.BLOCKS,
            pitch = 1.4F,
            volume = 1.0F
    )

    val TELEPORT = DetailedSound(
            Sound.BLOCK_WOODEN_DOOR_OPEN,
            SoundCategory.BLOCKS,
            pitch = 0.6F,
            volume = 1.0F
    )

    val INJURED = DetailedSound(
            Sound.ENTITY_PLAYER_HURT,
            SoundCategory.BLOCKS,
            pitch = 1.0F,
            volume = 1.0F
    )

    val ON_CUT = DetailedSound(
            Sound.ITEM_HOE_TILL,
            SoundCategory.BLOCKS,
            pitch = (1.5 + Random.nextGaussian(variance = 0.4)).toFloat(),
            volume = 0.2F
    )

    val ON_CONDENSE_WATER = DetailedSound(
            Sound.BLOCK_GLASS_PLACE,
            SoundCategory.BLOCKS,
            pitch = (1.5 + Random.nextGaussian(variance = 0.2)).toFloat(),
            volume = 0.1F
    )

    val ON_CONDENSE_LAVA = DetailedSound(
            Sound.BLOCK_LAVA_EXTINGUISH,
            SoundCategory.BLOCKS,
            pitch = (1.8 + Random.nextGaussian(variance = 0.2)).toFloat(),
            volume = 0.5F
    )

    val SWITCH = DetailedSound(
            Sound.ITEM_ARMOR_EQUIP_DIAMOND,
            SoundCategory.BLOCKS,
            pitch = 1.4F,
            volume = 1.0F
    )

}