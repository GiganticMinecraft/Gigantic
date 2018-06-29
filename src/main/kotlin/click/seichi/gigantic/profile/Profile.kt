package click.seichi.gigantic.profile

import org.joda.time.DateTime
import java.util.*

/**
 * @author tar0ss
 */
data class Profile(
        val uniqueId: UUID,
        val playerName: String,
        val lastSaveDate: DateTime,
        val seichiLevel: Long,
        val skillPreferences: SkillPreferences,
        var locale: Locale
)