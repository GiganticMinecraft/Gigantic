package click.seichi.gigantic.message.messages

import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.WorldArmorStand
import click.seichi.gigantic.message.WorldParticle
import click.seichi.gigantic.message.WorldSound
import click.seichi.gigantic.player.components.MineCombo
import click.seichi.gigantic.util.DetailedSound
import click.seichi.gigantic.util.Random
import org.bukkit.ChatColor
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.SoundCategory
import java.util.*

/**
 * @author tar0ss
 */
object SkillMessages {

    val ACTIVATE = LocalizedText(
            Locale.JAPANESE to "ON",
            Locale.ENGLISH to "ON"
    )

    val NOT_ACTIVATE = LocalizedText(
            Locale.JAPANESE to "OFF",
            Locale.ENGLISH to "OFF"
    )

    val LOCKED = LocalizedText(
            Locale.JAPANESE to "%DELETE%",
            Locale.ENGLISH to "%DELETE%"
    )

    val COOLDOWN = LocalizedText(
            Locale.JAPANESE to "クールダウン",
            Locale.ENGLISH to "Cooldown"
    )

    val NOT_SURVIVAL = LocalizedText(
            Locale.JAPANESE to "非サバイバル",
            Locale.ENGLISH to "Not survival"
    )

    val NOT_SEICHI_WORLD = LocalizedText(
            Locale.JAPANESE to "非整地ワールド",
            Locale.ENGLISH to "Not seichi world"
    )

    val FLYING = LocalizedText(
            Locale.JAPANESE to "Flyモード",
            Locale.ENGLISH to "Fly mode"
    )

    val NOT_SEICHI_TOOL = LocalizedText(
            Locale.JAPANESE to "非整地ツール",
            Locale.ENGLISH to "Not seichi tool"
    )


    val UPPER_BLOCK = LocalizedText(
            Locale.JAPANESE to "上方ブロック",
            Locale.ENGLISH to "Upper block"
    )

    val FOOTHOLD_BLOCK = LocalizedText(
            Locale.JAPANESE to "足場ブロック",
            Locale.ENGLISH to "Foothold block"
    )

    val NO_BLOCK = LocalizedText(
            Locale.JAPANESE to "空",
            Locale.ENGLISH to "null"
    )

    val NO_MANA = LocalizedText(
            Locale.JAPANESE to "マナ不足",
            Locale.ENGLISH to "Not enough mana"
    )

    val NO_DURABILITY = LocalizedText(
            Locale.JAPANESE to "耐久不足",
            Locale.ENGLISH to "Not enough durability"
    )

    val MINE_BURST_PARTICLE_ON_BREAK = WorldParticle(
            Particle.SPELL_INSTANT,
            10
    )
    val MINE_BURST_SOUND_ON_BREAK
        get() =
            WorldSound(
                    DetailedSound(
                            Sound.BLOCK_NOTE_CHIME,
                            SoundCategory.BLOCKS,
                            pitch = Random.nextGaussian(1.0, 0.2).toFloat(),
                            volume = 0.3F
                    )
            )

    val MINE_BURST_ON_FIRE = WorldSound(
            DetailedSound(
                    Sound.BLOCK_ENCHANTMENT_TABLE_USE,
                    SoundCategory.BLOCKS,
                    pitch = 1.6F,
                    volume = 0.3F
            )
    )

    val MINE_COMBO_COMBO_DISPLAY = { combo: MineCombo ->
        WorldArmorStand(10L) {
            val color = when (combo.currentCombo) {
                in 0..9 -> ChatColor.WHITE
                in 10..29 -> ChatColor.GREEN
                in 30..69 -> ChatColor.AQUA
                in 70..149 -> ChatColor.BLUE
                in 150..349 -> ChatColor.YELLOW
                in 350..799 -> ChatColor.DARK_AQUA
                in 800..1199 -> ChatColor.LIGHT_PURPLE
                else -> ChatColor.GOLD
            }
            it.run {
                isVisible = false
                setBasePlate(false)
                setArms(true)
                isMarker = true
                isInvulnerable = true
                canPickupItems = false
                setGravity(false)
                isCustomNameVisible = true
                customName = "$color${ChatColor.BOLD}${combo.currentCombo} Combo"
            }
        }
    }


}