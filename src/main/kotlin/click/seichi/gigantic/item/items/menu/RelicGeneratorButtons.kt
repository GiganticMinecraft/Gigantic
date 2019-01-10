package click.seichi.gigantic.item.items.menu

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.message.messages.menu.RelicGeneratorMenuMessages
import click.seichi.gigantic.player.Defaults
import click.seichi.gigantic.sound.sounds.PlayerSounds
import click.seichi.gigantic.will.Will
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object RelicGeneratorButtons {

    val SELECT_ETHEL: (Will) -> Button = { will ->
        object : Button {
            override fun findItemStack(player: Player): ItemStack? {
                if (!player.hasAptitude(will)) return null
                return ItemStack(will.material).apply {
                    setDisplayName("${ChatColor.RESET}" +
                            will.chatColor + "${ChatColor.BOLD}" +
                            will.getName(player.wrappedLocale) +
                            RelicGeneratorMenuMessages.SELECT_ETHEL.asSafety(player.wrappedLocale))
                    clearLore()
                    addLore("" + will.chatColor +
                            RelicGeneratorMenuMessages.ETHEL_AMOUNT.asSafety(player.wrappedLocale) +
                            player.ethel(will)
                    )
                    if (player.getOrPut(Keys.SELECTED_WILL) == will) {
                        setEnchanted(true)
                    }
                }
            }

            override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
                if (!player.hasAptitude(will)) return false
                if (player.ethel(will) < Defaults.RELIC_GENERATOR_REQUIRE_ETHEL) {
                    PlayerSounds.FAIL.playOnly(player)
                    return true
                }
                player.offer(Keys.SELECTED_WILL, will)
                // TODO playsounds
                return true
            }
        }
    }

    val GENERATE = {

    }

}