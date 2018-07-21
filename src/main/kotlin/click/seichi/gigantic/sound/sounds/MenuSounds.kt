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

    val BATTLE_MENU_OPEN = DetailedSound(
            Sound.BLOCK_ENDERCHEST_OPEN,
            SoundCategory.BLOCKS,
            pitch = 0.3F
    )

    val BATTLE_MENU_CLOSE = DetailedSound(
            Sound.BLOCK_ENDERCHEST_CLOSE,
            SoundCategory.BLOCKS,
            pitch = 0.3F
    )
}