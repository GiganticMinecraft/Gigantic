package click.seichi.gigantic.sound.sounds

import click.seichi.gigantic.sound.DetailedSound
import click.seichi.gigantic.util.Random
import org.bukkit.Sound
import org.bukkit.SoundCategory

/**
 * @author tar0ss
 */
object SkillSounds {

    val MINE_BURST_ON_BREAK = { combo: Long ->
        DetailedSound(
                Sound.BLOCK_NOTE_BLOCK_CHIME,
                SoundCategory.BLOCKS,
                pitchLevel = (combo % 25).toInt(),
                volume = 0.3F
        )
    }


    val MINE_BURST_ON_FIRE = DetailedSound(
            Sound.BLOCK_ENCHANTMENT_TABLE_USE,
            SoundCategory.BLOCKS,
            pitch = 1.6F,
            volume = 0.3F
    )

    val FLASH_FIRE = DetailedSound(
            Sound.ENTITY_SNOWBALL_THROW,
            SoundCategory.BLOCKS,
            pitch = 2.0F,
            volume = 1.0F
    )

    val FLASH_MISS = DetailedSound(
            Sound.ENTITY_PIG_AMBIENT,
            SoundCategory.BLOCKS,
            pitch = 2.0F,
            volume = 1.0F
    )

    val HEAL = DetailedSound(
            Sound.ITEM_SHOVEL_FLATTEN,
            SoundCategory.BLOCKS,
            pitch = (0.2 + Random.nextGaussian(variance = 0.4)).toFloat(),
            volume = 1.0F
    )

    val TOTEM_COMPLETE = DetailedSound(
            Sound.ITEM_TOTEM_USE,
            SoundCategory.BLOCKS,
            pitch = 2.0F,
            volume = 0.1F
    )

}