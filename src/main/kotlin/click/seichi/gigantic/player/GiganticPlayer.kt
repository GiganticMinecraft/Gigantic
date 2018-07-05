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
        val mineBlock: Long,
        val seichiLevel: Int,
        val mana: Long,
        val skillPreferences: SkillPreferences,
        var locale: Locale
)