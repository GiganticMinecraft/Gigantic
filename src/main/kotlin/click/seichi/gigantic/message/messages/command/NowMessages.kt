package click.seichi.gigantic.message.messages.command

import click.seichi.gigantic.message.LocalizedText
import org.joda.time.DateTime
import java.util.*

/**
 * @author tar0ss
 */
object NowMessages {

    val TIME_IN_REAL = { dateTime: DateTime ->
        LocalizedText(
                Locale.JAPANESE to "現在時間:$dateTime"
        )
    }

    val TIME_IN_MINECRAFT = { time: Long ->
        LocalizedText(
                Locale.JAPANESE to "マイクラ時間: $time"
        )
    }

    val JUNISHI_IN_MINECRAFT = { junishi: String ->
        LocalizedText(
                Locale.JAPANESE to "マイクラ十二時辰: ${junishi}の刻"
        )
    }

    val MOONPHASE_IN_MINECRAFT = { moonPhase: String ->
        LocalizedText(
                Locale.JAPANESE to "マイクラ月齢: ${moonPhase}"
        )
    }

}