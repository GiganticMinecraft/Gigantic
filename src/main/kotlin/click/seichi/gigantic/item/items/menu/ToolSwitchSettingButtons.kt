package click.seichi.gigantic.item.items.menu

import click.seichi.gigantic.extension.setLore
import click.seichi.gigantic.extension.switchTool
import click.seichi.gigantic.extension.updateBelt
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.menu.menus.ToolSwitchSettingMenu
import click.seichi.gigantic.message.messages.menu.ToolSwitchMessages
import click.seichi.gigantic.sound.sounds.PlayerSounds
import click.seichi.gigantic.tool.Tool
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object ToolSwitchSettingButtons {
    val TOOL_SWITCHER_SETTING: (Tool) -> Button = { tool ->
        object : Button {
            override fun findItemStack(player: Player): ItemStack? {
                return tool.findItemStack(player)?.apply {
                    setLore(
                            *ToolSwitchMessages.TOOL_SWITCHER_SETTING_BUTTON_LORE(tool.canSwitch(player))
                                    .map { it.asSafety(player.wrappedLocale) }
                                    .toTypedArray()
                    )
                }
            }

            override fun onClick(player: Player, event: InventoryClickEvent) {
                if (!tool.toggle(player)) {
                    player.switchTool()
                    PlayerSounds.SWITCH.playOnly(player)
                }
                player.updateBelt(true, true)
                ToolSwitchSettingMenu.reopen(player)
            }

        }
    }
}