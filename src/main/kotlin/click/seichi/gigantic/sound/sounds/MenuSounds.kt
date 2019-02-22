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
            pitch = 1.4F,
            volume = 0.6F
    )

    val CATEGORY_CHANGE = DetailedSound(
            Sound.BLOCK_PISTON_CONTRACT,
            SoundCategory.BLOCKS,
            pitch = 1.4F,
            volume = 0.4F
    )

    val EFFECT_BUY = DetailedSound(
            Sound.BLOCK_ENCHANTMENT_TABLE_USE,
            SoundCategory.BLOCKS,
            pitch = 0.6F,
            volume = 0.6F
    )

    val RELIC_MENU_OPEN = DetailedSound(
            Sound.BLOCK_ENDER_CHEST_OPEN,
            SoundCategory.BLOCKS,
            pitch = 0.1F
    )

    val RELIC_MENU_CLOSE = DetailedSound(
            Sound.BLOCK_ENDER_CHEST_CLOSE,
            SoundCategory.BLOCKS,
            pitch = 0.1F
    )

    val WILL_SELECT = DetailedSound(
            Sound.BLOCK_END_PORTAL_FRAME_FILL,
            SoundCategory.BLOCKS,
            volume = 0.6F
    )

    val RELIC_GENERATE = DetailedSound(
            Sound.BLOCK_ANVIL_USE,
            SoundCategory.BLOCKS,
            pitch = 2.0F,
            volume = 0.4F
    )


}