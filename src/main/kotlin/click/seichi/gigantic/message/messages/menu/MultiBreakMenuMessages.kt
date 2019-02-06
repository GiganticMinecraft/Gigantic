package click.seichi.gigantic.message.messages.menu

import click.seichi.gigantic.breaker.BreakArea
import click.seichi.gigantic.config.Config
import click.seichi.gigantic.message.LocalizedText
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object MultiBreakMenuMessages {

    val TITLE = LocalizedText(
            Locale.JAPANESE to "マルチ・ブレイク詳細設定"
    )

    val CURRENT_AREA = LocalizedText(
            Locale.JAPANESE to "${ChatColor.WHITE}" +
                    "現在の範囲"
    )

    val CURRENT_AREA_LORE = { breakArea: BreakArea ->
        setOf(
                LocalizedText(
                        Locale.JAPANESE to "${ChatColor.WHITE}" +
                                "横幅:${breakArea.width}"
                ),
                LocalizedText(
                        Locale.JAPANESE to "${ChatColor.WHITE}" +
                                "高さ:${breakArea.height}"
                ),
                LocalizedText(
                        Locale.JAPANESE to "${ChatColor.WHITE}" +
                                "奥行:${breakArea.depth}"
                ),
                LocalizedText(
                        Locale.JAPANESE to "${ChatColor.WHITE}" +
                                "現在の同時破壊数:${breakArea.calcBreakNum()}"
                )
        )
    }

    val LIMIT_SIZE = LocalizedText(
            Locale.JAPANESE to "${ChatColor.YELLOW}" +
                    "各パラメーターは " +
                    "${ChatColor.RED}${ChatColor.BOLD}" + Config.SPELL_MULTI_BREAK_LIMIT_SIZE +
                    " ${ChatColor.RESET}${ChatColor.YELLOW}" +
                    "より小さくしよう"
    )

    val LIMIT_OF_BREAK_NUM_LORE = { maxBreakNum: Int ->
        setOf(
                LocalizedText(
                        Locale.JAPANESE to "${ChatColor.YELLOW}" +
                                "マナの最大値によって同時破壊数は増える"
                ),
                LocalizedText(
                        Locale.JAPANESE to "${ChatColor.YELLOW}" +
                                "( " +
                                "${ChatColor.RESET}${ChatColor.RED}${ChatColor.BOLD}" +
                                "最大" +
                                "${ChatColor.RESET}${ChatColor.YELLOW}" +
                                "同時破壊数:$maxBreakNum )"
                )
        )
    }

    // 増減の表記を統一　+:BIGGER -:SMALLER

    val BIGGER_HEIGHT = { breakArea: BreakArea ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.WHITE}" +
                        "高さを1上げる(現在:${breakArea.height})"
        )
    }

    val SMALLER_HEIGHT = { breakArea: BreakArea ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.WHITE}" +
                        "高さを1下げる(現在:${breakArea.height})"
        )
    }

    val BIGGER_WIDTH = { breakArea: BreakArea ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.WHITE}" +
                        "横幅を2上げる(現在:${breakArea.width})"
        )
    }

    val SMALLER_WIDTH = { breakArea: BreakArea ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.WHITE}" +
                        "横幅を2下げる(現在:${breakArea.width})"
        )
    }

    val BIGGER_DEPTH = { breakArea: BreakArea ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.WHITE}" +
                        "奥行を1上げる(現在:${breakArea.depth})"
        )
    }

    val SMALLER_DEPTH = { breakArea: BreakArea ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.WHITE}" +
                        "奥行を1下げる(現在:${breakArea.depth})"
        )
    }

    val LIMIT_OF_BREAK_NUM = LocalizedText(
            Locale.JAPANESE to "${ChatColor.RED}" +
                    "最大同時破壊数を超えています"
    )


}