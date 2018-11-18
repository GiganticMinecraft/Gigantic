package click.seichi.gigantic.message.messages

import click.seichi.gigantic.message.LocalizedText
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object BagMessages {

    val PROFILE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.AQUA}${ChatColor.UNDERLINE}プロフィール"
    )

    val REST = LocalizedText(
            Locale.JAPANESE to "${ChatColor.GREEN}${ChatColor.UNDERLINE}休憩する"
    )

    val BACK_FROM_REST = LocalizedText(
            Locale.JAPANESE to "${ChatColor.YELLOW}${ChatColor.UNDERLINE}戻る"
    )

    val SPECIAL_THANKS_TITLE = LocalizedText(
            Locale.JAPANESE to "Special Thanks"
    )

    val QUEST = LocalizedText(
            Locale.JAPANESE to "クエスト"
    )

    val NO_QUEST = LocalizedText(
            Locale.JAPANESE to "クエストがありません"
    )

    val RELIC = LocalizedText(
            Locale.JAPANESE to "レリック"
    )

    val NO_RELIC = LocalizedText(
            Locale.JAPANESE to "レリックがありません"
    )


}