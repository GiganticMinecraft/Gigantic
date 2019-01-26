package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.menu.RelicMenuMessages
import click.seichi.gigantic.will.Will
import org.bukkit.ChatColor
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
abstract class WillRelicMenu(val will: Will, override val size: Int) : Menu() {

    override fun getTitle(player: Player): String {
        return "" + will.chatColor +
                "${ChatColor.BOLD}" +
                will.getName(player.wrappedLocale) +
                RelicMenuMessages.WILL.asSafety(player.wrappedLocale) +
                " " +
                "${ChatColor.RESET}${ChatColor.BLACK}" +
                RelicMenuMessages.RELICS.asSafety(player.wrappedLocale)
    }
}