package click.seichi.gigantic.relic.relics

import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.relic.Relic
import java.util.*

/**
 * Relic
 * id in 1000 until 2000
 *
 * @author tar0ss
 */
sealed class BeachRelics {

    val SHELLS = Relic(
            1000,
            LocalizedText(
                    Locale.JAPANESE to "貝殻"
            ),
            LocalizedText(
                    Locale.JAPANESE to "綺麗な模様の貝殻"
            ),
            LocalizedText(
                    Locale.JAPANESE to "誰かが食べた跡が見える"
            )
    )

}