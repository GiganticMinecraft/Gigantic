package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.button.buttons.menu.ProfileButtons
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.menu.ProfileMessages
import org.bukkit.ChatColor
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
object ProfileMenu : Menu() {

    override val size: Int
        get() = 9

    override fun getTitle(player: Player): String {
        return "${ChatColor.BLACK}" +
                ProfileMessages.TITLE.asSafety(player.wrappedLocale)
    }

    init {
        registerButton(0, ProfileButtons.PROFILE)
    }

}