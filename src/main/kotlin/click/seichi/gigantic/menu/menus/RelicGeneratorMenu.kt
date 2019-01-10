package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.menu.RelicGeneratorMenuMessages
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
object RelicGeneratorMenu : Menu() {

    override val size: Int
        get() = 5 * 9

    override fun getTitle(player: Player): String {
        return RelicGeneratorMenuMessages.TITLE.asSafety(player.wrappedLocale)
    }

    init {

    }

}