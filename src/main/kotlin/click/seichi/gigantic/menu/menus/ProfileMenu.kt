package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.button.buttons.MenuButtons
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.MenuMessages
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
                MenuMessages.PROFILE_TITLE.asSafety(player.wrappedLocale)
    }

    init {
        registerButton(0, MenuButtons.PROFILE_PROFILE)
        registerButton(3, MenuButtons.PROFILE_SKILL)
        registerButton(4, MenuButtons.PROFILE_SPELL)
    }

}