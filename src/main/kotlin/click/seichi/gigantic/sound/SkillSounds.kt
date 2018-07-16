package click.seichi.gigantic.sound

import click.seichi.gigantic.util.Random
import org.bukkit.Sound
import org.bukkit.SoundCategory

/**
 * @author tar0ss
 */
object SkillSounds {
    val MINE_BURST
        get() = SoundPlayer(
                Sound.BLOCK_ENCHANTMENT_TABLE_USE,
                SoundCategory.BLOCKS,
                pitch = Random.nextDouble(1.6, 2.0).toFloat(),
                volume = 0.3F
        )
}