package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.extension.setDisplayName
import click.seichi.gigantic.extension.setLore
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.item.items.menu.SettingButtons
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.menu.SettingMenuMessages
import click.seichi.gigantic.message.messages.menu.ToolSwitchMessages
import click.seichi.gigantic.player.ToggleSetting
import click.seichi.gigantic.sound.sounds.PlayerSounds
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object SettingMenu : Menu() {

    override val size: Int
        get() = 18

    override fun getTitle(player: Player): String {
        return SettingMenuMessages.TITLE.asSafety(player.wrappedLocale)
    }

    init {
        // 色々トグル設定(1..16)
        ToggleSetting.values().forEachIndexed { index, display ->
            registerButton(index, object : Button {
                override fun toShownItemStack(player: Player): ItemStack? {
                    val itemStack = if (display.getToggle(player)) {
                        ItemStack(Material.ENDER_EYE)
                    } else {
                        ItemStack(Material.ENDER_PEARL)
                    }
                    return itemStack.apply {
                        setDisplayName("${ChatColor.WHITE}${ChatColor.BOLD}" +
                                display.getName(player.wrappedLocale) + ": " +
                                if (display.getToggle(player)) {
                                    "${ChatColor.GREEN}${ChatColor.BOLD}ON"
                                } else {
                                    "${ChatColor.RED}${ChatColor.BOLD}OFF"
                                }
                        )
                        setLore(ToolSwitchMessages.CLICK_TO_TOGGLE.asSafety(player.wrappedLocale))
                    }
                }

                override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
                    display.toggle(player)
                    PlayerSounds.TOGGLE.playOnly(player)
                    reopen(player)
                    return true
                }
            })
        }
        // ツール切り替え設定
        registerButton(17, SettingButtons.TOOL_SWITCH_SETTING)
    }
}