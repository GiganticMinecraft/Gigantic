package click.seichi.gigantic.button.buttons.menu

import click.seichi.gigantic.belt.Belt
import click.seichi.gigantic.button.Button
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.menu.menus.BeltSwitchSettingMenu
import click.seichi.gigantic.message.messages.menu.BeltSwitchMessages
import click.seichi.gigantic.sound.sounds.SkillSounds
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object BeltSwitchButtons {
    val BELT_SWITCHER_SETTING: (Belt) -> Button = { belt ->
        object : Button {
            override fun getItemStack(player: Player): ItemStack? {
                val switcher = player.find(CatalogPlayerCache.BELT_SWITCHER) ?: return null
                return belt.findFixedButton()?.getItemStack(player)?.apply {
                    setDisplayName(belt.localizedName.asSafety(player.wrappedLocale))
                    setLore(
                            *BeltSwitchMessages.BELT_SWITCHER_SETTING_BUTTON_LORE(switcher.canSwitch(belt))
                                    .map { it.asSafety(player.wrappedLocale) }
                                    .toTypedArray()
                    )
                }
            }

            override fun onClick(player: Player, event: InventoryClickEvent) {
                player.manipulate(CatalogPlayerCache.BELT_SWITCHER) {
                    it.setCanSwitch(belt, !it.canSwitch(belt))
                    if (!it.canSwitch(belt)) {
                        it.switch()
                        SkillSounds.SWITCH.playOnly(player)
                    }
                }
                player.updateBelt(true, true)
                BeltSwitchSettingMenu.reopen(player)
            }

        }
    }
}