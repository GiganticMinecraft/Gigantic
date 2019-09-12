package click.seichi.gigantic.message.messages.command

import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.will.Will
import org.bukkit.ChatColor
import java.util.*

/**
 * @author tar0ss
 */
object WillMessages {

    val SPAWN = { will: Will ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.GREEN}" +
                        "will " + will.getName(Locale.JAPANESE) + " is spawned."
        )
    }

    val WILL_INFO = { will: Will ->
        LocalizedText(
                Locale.JAPANESE to "${ChatColor.RED}" +
                        will.name + " : " + will.getName(Locale.JAPANESE)
        )
    }
}