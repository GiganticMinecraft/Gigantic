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
                volume = 0.1F
        )
    }

    val LEVEL_UP = DetailedSound(
            Sound.ENTITY_PLAYER_LEVELUP,
            SoundCategory.PLAYERS,
            pitch = 0.8F,
            volume = 1.0F
    )

    val SCOOP_WATER = DetailedSound(
            Sound.ITEM_BUCKET_FILL,
            SoundCategory.BLOCKS
    )

    val SCOOP_LAVA = DetailedSound(
            Sound.ITEM_BUCKET_FILL_LAVA,
            SoundCategory.BLOCKS
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

    val SPELL_TOGGLE_ON = DetailedSound(
            Sound.BLOCK_BEACON_ACTIVATE,
            SoundCategory.BLOCKS,
            pitch = 1.4F,
            volume = 1.0F
    )

    val SPELL_TOGGLE_OFF = DetailedSound(
            Sound.BLOCK_BEACON_DEACTIVATE,
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


}