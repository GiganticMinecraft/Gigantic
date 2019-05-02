package click.seichi.gigantic.sidebar

import org.bukkit.ChatColor
import org.joda.time.DateTime

/**
 * @author tar0ss
 */
class Log(
        val id: Int,
        _text: String
) {

    val createdAt = DateTime.now()

    var text = (_text +
            " ${ChatColor.DARK_GRAY}" +
            id
            )
        private set

}