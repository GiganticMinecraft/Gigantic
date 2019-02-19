package click.seichi.gigantic.message.messages.command

import click.seichi.gigantic.message.LocalizedText
import org.joda.time.DateTime
import java.util.*

/**
 * @author tar0ss
 */
object NowMessages {

    val NOW = { dateTime: DateTime ->
        LocalizedText(
                Locale.JAPANESE to "time:$dateTime"
        )
    }

}