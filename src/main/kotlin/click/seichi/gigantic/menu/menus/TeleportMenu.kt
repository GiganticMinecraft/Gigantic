package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.button.buttons.menu.TeleportButtons
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.menu.TeleportMessages
import org.bukkit.ChatColor
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
object TeleportMenu : Menu() {

    override val size: Int
        get() = 9

    override fun getTitle(player: Player): String {
        return "${ChatColor.BLACK}" +
                TeleportMessages.TITLE.asSafety(player.wrappedLocale)
    }

    init {
        registerButton(0, TeleportButtons.TELEPORT_TO_RANDOM_CHUNK)
        registerButton(1, TeleportButtons.TELEPORT_TO_PLAYER)
        registerButton(8, TeleportButtons.TELEPORT_TOGGLE)
    }

}