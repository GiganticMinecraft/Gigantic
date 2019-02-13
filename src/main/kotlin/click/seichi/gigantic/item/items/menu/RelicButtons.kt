package click.seichi.gigantic.item.items.menu

import click.seichi.gigantic.extension.*
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
import java.math.RoundingMode

/**
 * @author tar0ss
 */
object RelicButtons {

    val WILL: (Will, Menu) -> Button = { will, menu ->
        object : Button {

            override fun toShownItemStack(player: Player): ItemStack? {
                val hasNoRelic = WillRelic.values()
                        .filter { it.will == will }
                        // 一つもレリックを持っていなければTRUE
                        .none { player.hasRelic(it.relic) }

                if (hasNoRelic) return null
                return ItemStack(will.material).apply {
                    setDisplayName("" + will.chatColor +
                            "${ChatColor.BOLD}" +
                            will.getName(player.wrappedLocale) +
                            RelicMenuMessages.WILL.asSafety(player.wrappedLocale) +
                            " " +
                            "${ChatColor.RESET}${ChatColor.WHITE}" +
                            RelicMenuMessages.RELICS.asSafety(player.wrappedLocale)
                    )
                }
            }

            override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
                val hasNoRelic = WillRelic.values()
                        .filter { it.will == will }
                        // 一つもレリックを持っていなければTRUE
                        .none { player.hasRelic(it.relic) }

                if (hasNoRelic) return false
                menu.open(player)
                return true
            }
        }
    }

    val WILL_RELIC: (WillRelic) -> Button = { willRelic ->
        val relic = willRelic.relic
        val will = willRelic.will
        object : Button {
            override fun toShownItemStack(player: Player): ItemStack? {
                val amount = relic.getDroppedNum(player)
                if (amount == 0L) return null
                return ItemStack(willRelic.material).apply {
                    setDisplayName("${ChatColor.RESET}" +
                            will.chatColor + "${ChatColor.BOLD}" +
                            relic.getName(player.wrappedLocale) +
                            "($amount)")
                    setLore(*relic.getLore(player.wrappedLocale).map { "${ChatColor.GRAY}" + it }.toTypedArray())
                    addLore("${ChatColor.WHITE}" + MenuMessages.LINE)
                    val multiplier = willRelic.calcMultiplier(player)
                    addLore("${ChatColor.YELLOW}${ChatColor.BOLD}" +
                            RelicMenuMessages.BONUS_EXP.asSafety(player.wrappedLocale) +
                            "${ChatColor.RESET}${ChatColor.WHITE}" +
                            RelicMenuMessages.BREAK_MUL.asSafety(player.wrappedLocale) +
                            multiplier.toBigDecimal().setScale(2, RoundingMode.HALF_UP))
                    val bonusLore = willRelic.getLore(player.wrappedLocale)
                    bonusLore.forEachIndexed { index, s ->
                        if (index == 0) {
                            addLore("${ChatColor.YELLOW}${ChatColor.BOLD}" +
                                    RelicMenuMessages.CONDITIONS.asSafety(player.wrappedLocale) +
                                    "${ChatColor.RESET}${ChatColor.WHITE}" + s)
                        } else {
                            addLore("${ChatColor.RESET}${ChatColor.WHITE}" + s)
                        }
                    }
                }
            }

            override fun tryClick(player: Player, event: InventoryClickEvent) = true // TODO: why it returns true but does nothing?
        }

    }

}