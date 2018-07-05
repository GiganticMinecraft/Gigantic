package click.seichi.gigantic.player

import org.joda.time.DateTime
import java.util.*

/**
 * @author tar0ss
 */
data class GiganticPlayer(
        val uniqueId: UUID,
        val playerName: String,
        val lastSaveDate: DateTime,
        var locale: Locale
)