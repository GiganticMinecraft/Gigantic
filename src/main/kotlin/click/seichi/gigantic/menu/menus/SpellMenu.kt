package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.extension.setDisplayName
import click.seichi.gigantic.extension.setEnchanted
import click.seichi.gigantic.extension.setLore
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.menu.SpellMenuMessages
import click.seichi.gigantic.player.spell.Spell
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object SpellMenu : Menu() {

    override val size: Int
        get() = 9

    override fun getTitle(player: Player): String {
        return SpellMenuMessages.TITLE.asSafety(player.wrappedLocale)
    }

    init {
        Spell.values().forEach { spell ->
            registerButton(spell.slot, object : Button {
                override fun findItemStack(player: Player): ItemStack? {
                    if (!spell.isGranted(player)) return null
                    return spell.getIcon().apply {
                        setDisplayName(spell.getName(player.wrappedLocale))
                        spell.getLore(player.wrappedLocale)?.let {
                            setLore(*it.toTypedArray())
                        }
                        setEnchanted(true)
                    }
                }

                override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
                    if (spell == Spell.MULTI_BREAK) {
                        MultiBreakSettingMenu.open(player)
                        return true
                    }
                    return false
                }

            })
        }
    }

}