package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.item.items.menu.SpellButtons
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.menu.SpellMenuMessages
import click.seichi.gigantic.player.spell.Spell
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
        Spell.values().forEach { spell ->
            registerButton(spell.slot, SpellButtons.SPELL(spell))
        }
    }

}