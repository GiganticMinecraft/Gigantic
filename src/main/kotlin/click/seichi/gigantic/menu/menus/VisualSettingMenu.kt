package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.item.items.menu.BackButton
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.menu.SkillMenuMessages
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
object VisualSettingMenu : Menu() {

    override val size: Int
        get() = 9

    override fun getTitle(player: Player): String {
        return SkillMenuMessages.TITLE.asSafety(player.wrappedLocale)
    }

    init {
        registerButton(0, BackButton(this, SettingMenu))
    }

}