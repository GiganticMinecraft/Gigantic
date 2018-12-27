package click.seichi.gigantic.message.messages.menu

import click.seichi.gigantic.breaker.BreakArea
import click.seichi.gigantic.message.LocalizedText
import org.bukkit.ChatColor
import java.util.*

/**
 * TODO 色が適当なので直すこと
 * @author tar0ss
 */
object ApostolusMenuMessages {

    val TITLE = LocalizedText(
            Locale.JAPANESE to "アポストル詳細設定"
    )

    val CURRENT_AREA = LocalizedText(
            Locale.JAPANESE to "${ChatColor.RESET}${ChatColor.WHITE}" +
                    "現在の範囲"
    )

    val CURRENT_AREA_LORE = { breakArea: BreakArea ->
        setOf(
                LocalizedText(
                        Locale.JAPANESE to "${ChatColor.RESET}${ChatColor.WHITE}" +
                                "横幅:${breakArea.width}"
                ),
                LocalizedText(
                        Locale.JAPANESE to "${ChatColor.RESET}${ChatColor.WHITE}" +
                                "高さ:${breakArea.height}"
                ),
                LocalizedText(
                        Locale.JAPANESE to "${ChatColor.RESET}${ChatColor.WHITE}" +
                                "奥行:${breakArea.depth}"
                ),
                LocalizedText(
                        Locale.JAPANESE to "${ChatColor.RESET}${ChatColor.WHITE}" +
                                "現在の同時破壊数:${breakArea.calcBreakNum()}"
                )
        )
    }

    val LIMIT_OF_BREAK_NUM_LORE = { maxBreakNum: Int ->
        setOf(
                LocalizedText(
                        Locale.JAPANESE to "${ChatColor.RESET}${ChatColor.DARK_GRAY}" +
                                "マナの最大値によって同時破壊数は増えます"
                ),
                LocalizedText(
                        Locale.JAPANESE to "${ChatColor.RESET}${ChatColor.DARK_GRAY}" +
                                "( 最大同時破壊数:$maxBreakNum )"
                )
        )
    }

    // 増減の表記を統一　+:BIGGER -:SMALLER

    val BIGGER_HEIGHT = { breakArea: BreakArea ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.RESET}${ChatColor.WHITE}" +
                        "高さを1上げる(現在:${breakArea.height})"
        )
    }

    val SMALLER_HEIGHT = { breakArea: BreakArea ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.RESET}${ChatColor.WHITE}" +
                        "高さを1下げる(現在:${breakArea.height})"
        )
    }

    val BIGGER_WIDTH = { breakArea: BreakArea ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.RESET}${ChatColor.WHITE}" +
                        "横幅を2上げる(現在:${breakArea.width})"
        )
    }

    val SMALLER_WIDTH = { breakArea: BreakArea ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.RESET}${ChatColor.WHITE}" +
                        "横幅を2下げる(現在:${breakArea.width})"
        )
    }

    val BIGGER_DEPTH = { breakArea: BreakArea ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.RESET}${ChatColor.WHITE}" +
                        "奥行を1上げる(現在:${breakArea.depth})"
        )
    }

    val SMALLER_DEPTH = { breakArea: BreakArea ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.RESET}${ChatColor.WHITE}" +
                        "奥行を1下げる(現在:${breakArea.depth})"
        )
    }

    val LIMIT_OF_BREAK_NUM = LocalizedText(
            Locale.JAPANESE to "${ChatColor.RESET}${ChatColor.RED}" +
                    "最大同時破壊数を超えています"
    )


}