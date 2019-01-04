package click.seichi.gigantic.item.items.menu

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.menu.RefineItem
import click.seichi.gigantic.menu.menus.PlayerListRefineMenu
import click.seichi.gigantic.message.messages.menu.PlayerListMenuMessages
import click.seichi.gigantic.sound.sounds.PlayerSounds
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object PlayerListMenuButtons {


    val REFINE = object : Button {

        override fun findItemStack(player: Player): ItemStack? {
            return ItemStack(Material.HOPPER).apply {
                setDisplayName("${ChatColor.AQUA}" +
                        PlayerListMenuMessages.REFINE.asSafety(player.wrappedLocale))
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
            if (event.inventory.holder === PlayerListRefineMenu) return false
            PlayerListRefineMenu.open(player)
            return true
        }

    }

    val REFINE_ITEM: (RefineItem) -> Button = { item: RefineItem ->
        object : Button {
            override fun findItemStack(player: Player): ItemStack? {
                val isRefine = player.getOrPut(Keys.REFINE_ITEM_MAP[item]!!)
                val itemStack = if (isRefine) ItemStack(Material.ENDER_EYE)
                else ItemStack(Material.BLACK_STAINED_GLASS_PANE)
                return itemStack.apply {
                    val color = if (isRefine) ChatColor.GREEN else ChatColor.RED
                    setDisplayName("$color" +
                            PlayerListMenuMessages.ONLINE.asSafety(player.wrappedLocale))
                    setLore("${ChatColor.WHITE}${ChatColor.UNDERLINE}${ChatColor.BOLD}" +
                            PlayerListMenuMessages.CLICK_TO_TOGGLE.asSafety(player.wrappedLocale))
                }
            }

            override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
                player.transform(Keys.REFINE_ITEM_MAP[item]!!) { !it }
                PlayerSounds.TOGGLE.playOnly(player)
                PlayerListRefineMenu.reopen(player)
                return true
            }
        }
    }

}