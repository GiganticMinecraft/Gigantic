package click.seichi.gigantic.item.items.menu

import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.menu.menus.ToolSwitchSettingMenu
import click.seichi.gigantic.message.messages.menu.ToolSwitchMessages
import click.seichi.gigantic.sound.sounds.SkillSounds
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
                val switcher = player.find(CatalogPlayerCache.TOOL_SWITCHER) ?: return null
                return tool.findItemStack(player)?.apply {
                    setLore(
                            *ToolSwitchMessages.TOOL_SWITCHER_SETTING_BUTTON_LORE(switcher.canSwitch(tool))
                                    .map { it.asSafety(player.wrappedLocale) }
                                    .toTypedArray()
                    )
                }
            }

            override fun onClick(player: Player, event: InventoryClickEvent) {
                player.manipulate(CatalogPlayerCache.TOOL_SWITCHER) {
                    it.setCanSwitch(tool, !it.canSwitch(tool))
                    if (!it.canSwitch(tool)) {
                        it.switch()
                        SkillSounds.SWITCH.playOnly(player)
                    }
                }
                player.updateBelt(true, true)
                ToolSwitchSettingMenu.reopen(player)
            }

        }
    }
}