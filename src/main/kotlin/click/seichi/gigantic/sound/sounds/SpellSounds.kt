package click.seichi.gigantic.sound.sounds

import click.seichi.gigantic.sound.DetailedSound
import click.seichi.gigantic.util.Random
import org.bukkit.Sound
import org.bukkit.SoundCategory

/**
 * @author tar0ss
 */
object SpellSounds {

    val TOGGLE_ON = DetailedSound(
            Sound.ITEM_TRIDENT_RETURN,
            SoundCategory.BLOCKS,
            pitch = (1.5 + Random.nextGaussian(variance = 0.4)).toFloat(),
            volume = 0.3F
    )

    val TOGGLE_OFF = DetailedSound(
            Sound.ITEM_TRIDENT_THROW,
            SoundCategory.BLOCKS,
            pitch = (1.5 + Random.nextGaussian(variance = 0.4)).toFloat(),
            volume = 0.3F
    )

    val TERRA_DRAIN_ON_FIRE = DetailedSound(
            Sound.ITEM_TRIDENT_THROW,
            SoundCategory.BLOCKS,
            pitch = (1.5 + Random.nextGaussian(variance = 0.4)).toFloat(),
            volume = 0.2F
    )

    val TERRA_DRAIN_ON_BREAK = DetailedSound(
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

    val GRAND_NATURA_ON_FIRE = DetailedSound(
            Sound.ENTITY_ILLUSIONER_MIRROR_MOVE,
            SoundCategory.BLOCKS,
            pitch = (1.5 + Random.nextGaussian(variance = 0.4)).toFloat(),
            volume = 0.2F
    )

    val GRAND_NATURA_ON_BREAK = DetailedSound(
            Sound.ENTITY_ILLUSIONER_CAST_SPELL,
            SoundCategory.BLOCKS,
            pitch = (1.5 + Random.nextGaussian(variance = 0.4)).toFloat(),
            volume = 0.2F
    )

    val AQUA_LINER_ON_FIRE = DetailedSound(
            Sound.ITEM_TRIDENT_RIPTIDE_1,
            SoundCategory.BLOCKS,
            pitch = (1.5 + Random.nextGaussian(variance = 0.4)).toFloat(),
            volume = 0.2F
    )

    val AQUA_LINER_ON_BREAK = DetailedSound(
            Sound.ENTITY_HUSK_CONVERTED_TO_ZOMBIE,
            SoundCategory.BLOCKS,
            pitch = (1.5 + Random.nextGaussian(variance = 0.4)).toFloat(),
            volume = 0.2F
    )
}