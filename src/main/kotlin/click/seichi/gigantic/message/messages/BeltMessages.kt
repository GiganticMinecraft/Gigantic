package click.seichi.gigantic.message.messages

import click.seichi.gigantic.message.LocalizedText
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object BeltMessages {

    val DIG = LocalizedText(
            Locale.JAPANESE to "${ChatColor.GOLD}掘削ベルト"
    )

    val MINE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.GRAY}採掘ベルト"
    )

    val CUT = LocalizedText(
            Locale.JAPANESE to "${ChatColor.YELLOW}樵ベルト"
    )

}