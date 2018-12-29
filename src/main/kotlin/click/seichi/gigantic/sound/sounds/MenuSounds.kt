package click.seichi.gigantic.sound.sounds

import click.seichi.gigantic.sound.DetailedSound
import org.bukkit.Sound
import org.bukkit.SoundCategory

/**
 * @author tar0ss
 */
object MenuSounds {
    val MENU_OPEN = DetailedSound(
            Sound.BLOCK_FENCE_GATE_OPEN,
            SoundCategory.BLOCKS,
            pitch = 0.1F

    )

    val MENU_CLOSE = DetailedSound(
            Sound.BLOCK_FENCE_GATE_CLOSE,
            SoundCategory.BLOCKS,
            pitch = 0.1F
    )

    val PAGE_CHANGE = DetailedSound(
            Sound.BLOCK_DISPENSER_LAUNCH,
            SoundCategory.BLOCKS,
            pitch = 4.0F
    )

    val EFFECT_MENU = DetailedSound(
            Sound.BLOCK_ENCHANTMENT_TABLE_USE,
            SoundCategory.BLOCKS,
            pitch = 0.6F,
            volume = 0.6F
    )

    val EFFECT_SELECT = DetailedSound(
            Sound.BLOCK_ENCHANTMENT_TABLE_USE,
            SoundCategory.BLOCKS,
            pitch = 0.6F,
            volume = 1.5F
    )

    val EFFECT_BUY = DetailedSound(
            Sound.BLOCK_ANVIL_USE,
            SoundCategory.BLOCKS,
            pitch = 0.5F,
            volume = 1.0F
    )

}