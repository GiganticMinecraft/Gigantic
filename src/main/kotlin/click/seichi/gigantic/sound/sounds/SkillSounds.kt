package click.seichi.gigantic.sound.sounds

import click.seichi.gigantic.sound.DetailedSound
import click.seichi.gigantic.util.Random
import org.bukkit.Sound
import org.bukkit.SoundCategory

/**
 * @author tar0ss
 */
object SkillSounds {

    val MINE_BURST_ON_BREAK
        get() = DetailedSound(
                Sound.BLOCK_NOTE_CHIME,
                SoundCategory.BLOCKS,
                pitch = Random.nextGaussian(1.0, 0.2).toFloat(),
                volume = 0.3F
        )


    val MINE_BURST_ON_FIRE = DetailedSound(
            Sound.BLOCK_ENCHANTMENT_TABLE_USE,
            SoundCategory.BLOCKS,
            pitch = 1.6F,
            volume = 0.3F
    )

}