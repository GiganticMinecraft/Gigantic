package click.seichi.gigantic.sidebar

import click.seichi.gigantic.player.Defaults
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
            " ${Defaults.SIDEBAR_HIDE_COLOR}" +
            id
            )
        private set

}