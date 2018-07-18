package click.seichi.gigantic.message.sounds

import click.seichi.gigantic.message.WorldSound
import click.seichi.gigantic.util.DetailedSound
import org.bukkit.Sound
import org.bukkit.SoundCategory

/**
 * @author tar0ss
 */
object MenuSounds {
    val MENU_OPEN = WorldSound(
            DetailedSound(
                    Sound.BLOCK_FENCE_GATE_OPEN,
                    SoundCategory.BLOCKS,
                    pitch = 0.1F
            )
    )

    val MENU_CLOSE = WorldSound(
            DetailedSound(
                    Sound.BLOCK_FENCE_GATE_CLOSE,
                    SoundCategory.BLOCKS,
                    pitch = 0.1F
            )
    )

}