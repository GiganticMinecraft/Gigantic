package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.item.items.menu.SkillButtons
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.menu.SkillMenuMessages
import click.seichi.gigantic.player.skill.Skill
import org.bukkit.ChatColor
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
object SkillMenu : Menu() {

    override val size: Int
        get() = 9

    override fun getTitle(player: Player): String {
        return "${ChatColor.BLACK}" +
                SkillMenuMessages.TITLE.asSafety(player.wrappedLocale)
    }

    init {
        Skill.values().forEach { skill ->
            registerButton(skill.slot, SkillButtons.SKILL(skill))
        }
    }

}