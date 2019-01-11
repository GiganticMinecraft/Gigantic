package click.seichi.gigantic.item.items.menu

import click.seichi.gigantic.extension.addLore
import click.seichi.gigantic.extension.setDisplayName
import click.seichi.gigantic.extension.setLore
import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.MenuMessages
import click.seichi.gigantic.message.messages.menu.RelicMenuMessages
import click.seichi.gigantic.relic.WillRelic
import click.seichi.gigantic.will.Will
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object RelicButtons {

    val WILL: (Will, Menu) -> Button = { will, menu ->
        object : Button {
            override fun findItemStack(player: Player): ItemStack? {
                return ItemStack(will.material).apply {
                    setDisplayName("" + will.chatColor +
                            "${ChatColor.BOLD}" +
                            will.getName(player.wrappedLocale) +
                            RelicMenuMessages.WILL.asSafety(player.wrappedLocale) +
                            " " +
                            "${ChatColor.RESET}${ChatColor.BLACK}" +
                            RelicMenuMessages.RELICS.asSafety(player.wrappedLocale)
                    )
                }
            }

            override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
                menu.open(player)
                return true
            }
        }
    }

    val WILL_RELIC: (WillRelic) -> Button = { willRelic ->
        val relic = willRelic.relic
        val will = willRelic.will
        object : Button {
            override fun findItemStack(player: Player): ItemStack? {
                val amount = relic.getDroppedNum(player)
                // TODO remove
//                if(amount == 0L)return null
                return ItemStack(willRelic.material).apply {
                    setDisplayName("${ChatColor.RESET}" +
                            will.chatColor + "${ChatColor.BOLD}" +
                            relic.getName(player.wrappedLocale) +
                            "($amount)")
                    setLore(*relic.getLore(player.wrappedLocale).map { "${ChatColor.GRAY}" + it }.toTypedArray())
                    addLore("${ChatColor.WHITE}" + MenuMessages.LINE)
                    val bonusLore = willRelic.getLore(player.wrappedLocale)
                    bonusLore.forEachIndexed { index, s ->
                        if (index == 0) {
                            addLore("${ChatColor.YELLOW}" +
                                    RelicMenuMessages.CONDITIONS.asSafety(player.wrappedLocale) +
                                    s)
                        } else {
                            addLore(s)
                        }
                    }
                }
            }

            override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
                return true
            }
        }

    }

}