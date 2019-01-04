package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.extension.setLore
import click.seichi.gigantic.extension.switchTool
import click.seichi.gigantic.extension.updateBelt
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.menu.ToolSwitchMessages
import click.seichi.gigantic.sound.sounds.PlayerSounds
import click.seichi.gigantic.tool.Tool
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

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
        Tool.values().forEachIndexed { index, tool ->
            registerButton(index, object : Button {
                override fun findItemStack(player: Player): ItemStack? {
                    return tool.findItemStack(player)?.apply {
                        setLore(
                                *ToolSwitchMessages.TOOL_SWITCHER_SETTING_BUTTON_LORE(tool.canSwitch(player))
                                        .map { it.asSafety(player.wrappedLocale) }
                                        .toTypedArray()
                        )
                    }
                }

                override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
                    if (!tool.toggle(player)) {
                        player.switchTool()
                    }
                    PlayerSounds.SWITCH.playOnly(player)
                    player.updateBelt(true, true)
                    ToolSwitchSettingMenu.reopen(player)
                    return true
                }

            })
        }
    }

}