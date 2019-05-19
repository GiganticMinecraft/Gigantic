package click.seichi.gigantic.item.items.menu

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.config.Config
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.menu.menus.setting.ToolSwitchSettingMenu
import click.seichi.gigantic.message.messages.BagMessages
import click.seichi.gigantic.sound.sounds.PlayerSounds
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object SettingButtons {

    val TOOL_SWITCH_SETTING = object : Button {

        override fun toShownItemStack(player: Player): ItemStack? {
            return itemStackOf(Material.LADDER) {
                setDisplayName("${ChatColor.AQUA}${ChatColor.UNDERLINE}"
                        + BagMessages.SWITCH_DETAIL.asSafety(player.wrappedLocale))
                setLore(BagMessages.SWITCH_DETAIL_LORE.asSafety(player.wrappedLocale))
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            if (event.inventory.holder === ToolSwitchSettingMenu) return false
            ToolSwitchSettingMenu.open(player)
            return true
        }

    }

    val TEXTURE = object : Button {
        override fun toShownItemStack(player: Player): ItemStack? {
            return (if (player.isNormalTexture)
                itemStackOf(Material.GLISTERING_MELON_SLICE) {
                    setDisplayName(player, BagMessages.NORMAL_TEXTURE)
                }
            else itemStackOf(Material.MELON_SLICE) {
                setDisplayName(player, BagMessages.LIGHT_TEXTURE)
            }).apply {
                setLore(*BagMessages.SERVER_RESOURCE_PACK.map { it.asSafety(player.wrappedLocale) }.toTypedArray())
            }
        }

        override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
            player.transform(Keys.IS_NORMAL_TEXTURE) {
                !it
            }
            player.updateBag()
            PlayerSounds.TOGGLE.playOnly(player)

            if (player.isNormalTexture)
                player.setResourcePack(Config.RESOURCE_DEFAULT)
            else
                player.setResourcePack(Config.RESOURCE_NO_PARTICLE)

            return true
        }

    }

}