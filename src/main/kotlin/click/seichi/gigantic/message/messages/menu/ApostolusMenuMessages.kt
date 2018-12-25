package click.seichi.gigantic.message.messages.menu

import click.seichi.gigantic.breaker.BreakArea
import click.seichi.gigantic.message.LocalizedText
import java.util.*

/**
 * TODO 色が適当なので直すこと
 * @author tar0ss
 */
object ApostolusMenuMessages {

    val TITLE = LocalizedText(
            Locale.JAPANESE to "破壊範囲設定画面"
    )

    val CURRENT_AREA = LocalizedText(
            Locale.JAPANESE to "現在の範囲"
    )

    val CURRENT_AREA_LORE = { breakArea: BreakArea ->
        setOf(
                LocalizedText(
                        Locale.JAPANESE to "横幅:${breakArea.width}"
                ),
                LocalizedText(
                        Locale.JAPANESE to "高さ:${breakArea.height}"
                ),
                LocalizedText(
                        Locale.JAPANESE to "奥行:${breakArea.depth}"
                )
        )
    }

    // 増減の表記を統一　+:BIGGER -:SMALLER

    val BIGGER_HEIGHT = { breakArea: BreakArea ->
        LocalizedText(
                Locale.JAPANESE to "高さを1上げる(現在:${breakArea.height})"
        )
    }

    val SMALLER_HEIGHT = { breakArea: BreakArea ->
        LocalizedText(
                Locale.JAPANESE to "高さを1下げる(現在:${breakArea.height})"
        )
    }

    val BIGGER_WIDTH = { breakArea: BreakArea ->
        LocalizedText(
                Locale.JAPANESE to "横幅を2上げる(現在:${breakArea.width})"
        )
    }

    val SMALLER_WIDTH = { breakArea: BreakArea ->
        LocalizedText(
                Locale.JAPANESE to "横幅を2下げる(現在:${breakArea.width})"
        )
    }

    val BIGGER_DEPTH = { breakArea: BreakArea ->
        LocalizedText(
                Locale.JAPANESE to "奥行を1上げる(現在:${breakArea.depth})"
        )
    }

    val SMALLER_DEPTH = { breakArea: BreakArea ->
        LocalizedText(
                Locale.JAPANESE to "奥行を1下げる(現在:${breakArea.depth})"
        )
    }


}