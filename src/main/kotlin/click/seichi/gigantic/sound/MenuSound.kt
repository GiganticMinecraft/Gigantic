package click.seichi.gigantic.sound

import org.bukkit.Sound
import org.bukkit.SoundCategory

/**
 * @author tar0ss
 */
object MenuSound {
    val MENU_OPEN = SoundPlayer(
            Sound.BLOCK_FENCE_GATE_OPEN,
            SoundCategory.BLOCKS,
            pitch = 0.1F
    )

    val MENU_CLOSE = SoundPlayer(
            Sound.BLOCK_FENCE_GATE_CLOSE,
            SoundCategory.BLOCKS,
            pitch = 0.1F
    )
}