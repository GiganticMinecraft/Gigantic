package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.item.items.menu.BackButton
import click.seichi.gigantic.item.items.menu.ToolSwitchSettingButtons
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.menu.ToolSwitchMessages
import click.seichi.gigantic.tool.Tool
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
object ToolSwitchSettingMenu : Menu() {

    override val size: Int
        get() = 9

    override fun getTitle(player: Player): String {
        return ToolSwitchMessages.TITLE.asSafety(player.wrappedLocale)
    }

    init {
        registerButton(0, BackButton(this, SettingMenu))
        Tool.values().forEachIndexed { index, tool ->
            registerButton(index + 2, ToolSwitchSettingButtons.TOOL(tool))
        }
        registerButton(8, ToolSwitchSettingButtons.AUTO_SWITCH)
    }

}