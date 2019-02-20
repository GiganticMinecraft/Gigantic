package click.seichi.gigantic.message.messages.menu

import click.seichi.gigantic.message.LocalizedText
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object ShopMessages {

    val VOTE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.YELLOW}${ChatColor.BOLD}" +
                    "投票特典" +
                    "${ChatColor.WHITE}" +
                    "を見る"
    )

    val DONATION = LocalizedText(
            Locale.JAPANESE to "${ChatColor.AQUA}${ChatColor.BOLD}" +
                    "寄付特典" +
                    "${ChatColor.WHITE}" +
                    "を見る"
    )

    val DONATE_GIFT_EFFECT_SHOP_TITLE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.BLACK}" +
                    "寄付特典 エフェクト"
    )

    val VOTE_GIFT_EFFECT_SHOP_TITLE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.BLACK}" +
                    "投票特典 エフェクト"
    )

    val EFFECT_MENU = LocalizedText(
            Locale.JAPANESE to "${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}" +
                    "エフェクト選択メニューへ"
    )

}