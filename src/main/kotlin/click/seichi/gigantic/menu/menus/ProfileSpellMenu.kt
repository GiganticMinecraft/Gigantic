package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.button.buttons.MenuButtons
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.MenuMessages
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
object ProfileSpellMenu : Menu() {

    override val size: Int
        get() = 9

    override fun getTitle(player: Player): String {
        return MenuMessages.PROFILE_SPELL.asSafety(player.wrappedLocale)
    }

    init {
        registerButton(0, MenuButtons.PROFILE_SKILL_STELLA_CLAIR)
        registerButton(1, MenuButtons.PROFILE_SKILL_TERRA_DRAIN)
    }

}