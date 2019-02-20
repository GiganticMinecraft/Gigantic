package click.seichi.gigantic.message.messages.menu

import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.player.Defaults
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object EffectMenuMessages {

    val TITLE = LocalizedText(
            Locale.JAPANESE to "エフェクト"
    )

    val CURRENT_EFFECT = LocalizedText(
            Locale.JAPANESE to "${ChatColor.AQUA}現在使用中のエフェクト"
    )

    val CAUTION = LocalizedText(
            Locale.JAPANESE to "${ChatColor.GREEN}" +
                    "購入します。よろしいですか？"
    )

    val CAN_BUY = LocalizedText(
            Locale.JAPANESE to "${ChatColor.GREEN}" +
                    "${ChatColor.UNDERLINE}" +
                    "クリックで承認"
    )

    val CAN_BUY_DOUBLE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.YELLOW}" +
                    "${ChatColor.UNDERLINE}" +
                    "クリックで購入"
    )

    val CANT_BUY = LocalizedText(
            Locale.JAPANESE to "${ChatColor.RED}" +
                    "${ChatColor.UNDERLINE}" +
                    "購入できない"
    )

    val REMAIN = LocalizedText(
            Locale.JAPANESE to "${ChatColor.AQUA}" +
                    "${ChatColor.BOLD}" +
                    "残り"
    )


    val VOTE_POINT = LocalizedText(
            Locale.JAPANESE to "${ChatColor.YELLOW}" +
                    "${ChatColor.BOLD}" +
                    "投票p"
    )

    val POMME = LocalizedText(
            Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}" +
                    "${ChatColor.BOLD}" +
                    "ポム"
    )

    val DONATION = LocalizedText(
            Locale.JAPANESE to "${ChatColor.GOLD}" +
                    "${ChatColor.BOLD}" +
                    "寄付p"
    )

    val BUY_TYPE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.WHITE}" +
                    " で購入できる"
    )

    val PLAYER = LocalizedText(
            Locale.JAPANESE to "${ChatColor.GREEN}" +
                    "${ChatColor.UNDERLINE}" +
                    "プレイヤー情報"
    )

    val VOTE_POINT_DESCRIPTION = LocalizedText(
            Locale.JAPANESE to "${ChatColor.GRAY}" +
                    "JMSから投票することで${Defaults.VOTE_POINT_PER_VOTE}投票ポイント獲得できる"
    )

    val POMME_DESCRIPTION = LocalizedText(
            Locale.JAPANESE to "${ChatColor.GRAY}" +
                    "SPADEをプレイすることで一定数のポムを獲得できる"
    )

    val DONATION_DESCRIPTION = LocalizedText(
            Locale.JAPANESE to "${ChatColor.GRAY}" +
                    "寄付${Defaults.DONATITON_PER_DONATE_POINT}円につき1寄付ポイント獲得できる"
    )

    val CLICK_TO_SELECT = LocalizedText(
            Locale.JAPANESE to "${ChatColor.WHITE}" +
                    "${ChatColor.UNDERLINE}" +
                    "クリックで選択"
    )

    val SELECTED = LocalizedText(
            Locale.JAPANESE to "${ChatColor.YELLOW}" +
                    "${ChatColor.UNDERLINE}" +
                    "選択中"
    )

    val GENERAL_BREAK = LocalizedText(
            Locale.JAPANESE to "${ChatColor.GREEN}" +
                    "${ChatColor.BOLD}" +
                    "通常破壊 :"
    )

    val MULTI_BREAK = LocalizedText(
            Locale.JAPANESE to "${ChatColor.AQUA}" +
                    "${ChatColor.BOLD}" +
                    "複数破壊 :"
    )

}