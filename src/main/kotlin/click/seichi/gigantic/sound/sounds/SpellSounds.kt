package click.seichi.gigantic.sound.sounds

import click.seichi.gigantic.sound.DetailedSound
import click.seichi.gigantic.util.Random
import org.bukkit.Sound
import org.bukkit.SoundCategory

/**
 * @author tar0ss
 */
object SpellSounds {

    val TERRA_DRAIN = DetailedSound(
            Sound.ITEM_HOE_TILL,
            SoundCategory.BLOCKS,
            pitch = (1.5 + Random.nextGaussian(variance = 0.4)).toFloat(),
            volume = 0.2F
    )

    val STELLA_CLAIR = DetailedSound(
            Sound.BLOCK_CONDUIT_ACTIVATE,
            SoundCategory.BLOCKS,
            pitch = (1.5 + Random.nextGaussian(variance = 0.4)).toFloat(),
            volume = 0.4F
    )

}