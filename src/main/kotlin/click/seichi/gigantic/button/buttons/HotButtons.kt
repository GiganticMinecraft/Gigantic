package click.seichi.gigantic.button.buttons

import click.seichi.gigantic.button.HotButton
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.menu.menus.BeltSwitchSettingMenu
import click.seichi.gigantic.message.messages.HookedItemMessages
import click.seichi.gigantic.player.LockedFunction
import click.seichi.gigantic.skill.Skills
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object HotButtons {

    val MINE_BURST = object : HotButton {

        override fun getItemStack(player: Player): ItemStack? {
            if (!LockedFunction.MINE_BURST.isUnlocked(player)) return null
            val mineBurst = player.find(CatalogPlayerCache.MINE_BURST) ?: return null
            return when {
                mineBurst.duringCoolTime() -> ItemStack(Material.FLINT_AND_STEEL).apply {
                    mineBurst.run {
                        amount = remainTimeToFire.toInt()
                    }
                }
                mineBurst.duringFire() -> ItemStack(Material.BLAZE_POWDER).apply {
                    setEnchanted(true)
                    mineBurst.run {
                        amount = remainTimeToCool.toInt()
                    }
                }
                else -> ItemStack(Material.BLAZE_POWDER)
            }.apply {
                setDisplayName(HookedItemMessages.MINE_BURST.asSafety(player.wrappedLocale))
                setLore(*HookedItemMessages.MINE_BURST_LORE(mineBurst)
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray()
                )
            }
        }

        override fun onItemHeld(player: Player, event: PlayerItemHeldEvent) {
            Skills.MINE_BURST.tryInvoke(player)
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
        }

    }

    val FLASH = object : HotButton {

        override fun getItemStack(player: Player): ItemStack? {
            if (!LockedFunction.FLASH.isUnlocked(player)) return null
            val flash = player.find(CatalogPlayerCache.FLASH) ?: return null
            return when {
                flash.duringCoolTime() -> ItemStack(Material.FLINT_AND_STEEL).apply {
                    flash.run {
                        amount = remainTimeToFire.toInt()
                    }
                }
                else -> ItemStack(Material.FEATHER)
            }.apply {
                setDisplayName(HookedItemMessages.FLASH.asSafety(player.wrappedLocale))
                setLore(*HookedItemMessages.FLASH_LORE(flash)
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray()
                )
            }
        }

        override fun onItemHeld(player: Player, event: PlayerItemHeldEvent) {
            Skills.FLASH.tryInvoke(player)
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
        }

    }

    val BELT_SWITCHER_SETTING = object : HotButton {

        override fun getItemStack(player: Player): ItemStack? {
            if (!LockedFunction.SWITCH.isUnlocked(player)) return null
            val switcher = player.find(CatalogPlayerCache.BELT_SWITCHER) ?: return null
            val nextBelt = switcher.nextBelt()
            return nextBelt.getFixedButton().getItemStack(player)?.apply {
                setDisplayName(HookedItemMessages.SWITCH.asSafety(player.wrappedLocale))
                clearLore()
            }
        }

        override fun onItemHeld(player: Player, event: PlayerItemHeldEvent) {
            BeltSwitchSettingMenu.open(player)
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
            BeltSwitchSettingMenu.open(player)
        }

    }

}