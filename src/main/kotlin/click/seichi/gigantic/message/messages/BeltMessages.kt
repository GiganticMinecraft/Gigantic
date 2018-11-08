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

    val SCOOP = LocalizedText(
            Locale.JAPANESE to "${ChatColor.AQUA}バケツベルト"
    )

    val BELT_SWITCHER_SETTING = LocalizedText(
            Locale.JAPANESE to "ツール切り替え詳細設定"
    )

}