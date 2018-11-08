package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.button.buttons.menu.SpellButtons
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.menu.SpellMenuMessages
import org.bukkit.ChatColor
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
object SpellMenu : Menu() {

    override val size: Int
        get() = 9

    override fun getTitle(player: Player): String {
        return "${ChatColor.BLACK}" +
                SpellMenuMessages.TITLE.asSafety(player.wrappedLocale)
    }

    init {
        registerButton(0, SpellButtons.STELLA_CLAIR)
        registerButton(1, SpellButtons.TERRA_DRAIN)
        registerButton(2, SpellButtons.GRAND_NATURA)
        registerButton(3, SpellButtons.AQUA_LINEA)
    }

}