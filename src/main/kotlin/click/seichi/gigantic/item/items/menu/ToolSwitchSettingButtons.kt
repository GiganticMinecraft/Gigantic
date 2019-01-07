package click.seichi.gigantic.item.items.menu

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.menu.menus.ToolSwitchSettingMenu
import click.seichi.gigantic.message.messages.menu.ToolSwitchMessages
import click.seichi.gigantic.sound.sounds.PlayerSounds
import click.seichi.gigantic.tool.Tool
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object ToolSwitchSettingButtons {

    val TOOL: (Tool) -> Button = { tool: Tool ->
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

            override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
                if (!tool.toggle(player)) {
                    player.switchTool()
                }
                PlayerSounds.SWITCH.playOnly(player)
                player.updateBelt(true, true)
                ToolSwitchSettingMenu.reopen(player)
                return true
            }
        }
    }

    val AUTO_SWITCH = object : Button {
        override fun findItemStack(player: Player): ItemStack? {
            val autoSwitch = player.getOrPut(Keys.AUTO_SWITCH)
            return ItemStack(Material.COMPARATOR).apply {
                setDisplayName("${ChatColor.WHITE}" +
                        ToolSwitchMessages.AUTO_SWITCH.asSafety(player.wrappedLocale) +
                        if (autoSwitch) "${ChatColor.GREEN}${ChatColor.BOLD}ON"
                        else "${ChatColor.RED}${ChatColor.BOLD}OFF"
                )
                setLore(ToolSwitchMessages.CLICK_TO_TOGGLE.asSafety(player.wrappedLocale))
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
            player.transform(Keys.AUTO_SWITCH) { !it }
            PlayerSounds.TOGGLE.playOnly(player)
            ToolSwitchSettingMenu.reopen(player)
            return true
        }
    }
}