package click.seichi.gigantic.message.messages.menu

import click.seichi.gigantic.message.LocalizedText
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object BeltSwitchMessages {

    val TITLE = LocalizedText(
            Locale.JAPANESE to "ベルト切り替え詳細設定"
    )

    val BELT_SWITCHER_SETTING_BUTTON_LORE = { canSwitch: Boolean ->
        if (canSwitch) {
            listOf(
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.GREEN}選択済"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.WHITE}----------------"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.WHITE}${ChatColor.UNDERLINE}クリックで切り替え"
                    )
            )

        } else {
            listOf(
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.RED}未選択"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.WHITE}----------------"
                    ),
                    LocalizedText(
                            Locale.JAPANESE to "${ChatColor.WHITE}${ChatColor.UNDERLINE}クリックで切り替え"
                    )
            )
        }
    }

}