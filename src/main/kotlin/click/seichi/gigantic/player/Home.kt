package click.seichi.gigantic.player

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.message.messages.HomeMessages
import org.bukkit.Location

/**
 * @author tar0ss
 */
data class Home(
        val id: Int,
        val location: Location,
        var name: String = HomeMessages.DEFAULT_NAME.asSafety(Gigantic.DEFAULT_LOCALE) + "${id.plus(1)}"
)