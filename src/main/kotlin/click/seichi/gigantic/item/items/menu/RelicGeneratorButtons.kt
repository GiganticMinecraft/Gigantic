package click.seichi.gigantic.item.items.menu

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.menu.menus.RelicGeneratorMenu
import click.seichi.gigantic.message.messages.menu.RelicGeneratorMenuMessages
import click.seichi.gigantic.player.Defaults
import click.seichi.gigantic.sound.sounds.MenuSounds
import click.seichi.gigantic.will.Will
import org.bukkit.ChatColor
import org.bukkit.Material
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
                val selected = player.getOrPut(Keys.SELECTED_WILL)
                return ItemStack(will.material).apply {

                    setDisplayName("${ChatColor.RESET}${ChatColor.WHITE}" +
                            (if (selected == will) RelicGeneratorMenuMessages.SELECTED.asSafety(player.wrappedLocale)
                            else "") +
                            will.chatColor + "${ChatColor.BOLD}" +
                            will.getName(player.wrappedLocale) +
                            RelicGeneratorMenuMessages.SELECT_ETHEL.asSafety(player.wrappedLocale) +
                            "(${player.ethel(will)})"
                    )
                    clearLore()
                    if (player.getOrPut(Keys.SELECTED_WILL) == will) {
                        setEnchanted(true)
                    }
                }
            }

            override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
                if (!player.hasAptitude(will)) return false
                val selected = player.getOrPut(Keys.SELECTED_WILL)
                if (selected != null && selected == will) return true
                player.offer(Keys.SELECTED_WILL, will)
                MenuSounds.WILL_SELECT.playOnly(player)
                RelicGeneratorMenu.reopen(player)
                return true
            }
        }
    }

    val GENERATE = object : Button {
        override fun findItemStack(player: Player): ItemStack? {
            val selected = player.getOrPut(Keys.SELECTED_WILL)
            return ItemStack(Material.END_PORTAL_FRAME).apply {
                when {
                    // 何も選択していない場合
                    selected == null ->
                        setDisplayName(RelicGeneratorMenuMessages.NULL_OF_ETHEL.asSafety(player.wrappedLocale))
                    // 選択したエーテルが不足している場合
                    player.ethel(selected) < Defaults.RELIC_GENERATOR_REQUIRE_ETHEL ->
                        setDisplayName(RelicGeneratorMenuMessages.LOST_OF_ETHEL.asSafety(player.wrappedLocale))
                    else -> {
                        setDisplayName(RelicGeneratorMenuMessages.GENERATE.asSafety(player.wrappedLocale))
                        setLore("${ChatColor.RESET}" +
                                selected.chatColor +
                                "${ChatColor.BOLD}" +
                                selected.getName(player.wrappedLocale) +
                                RelicGeneratorMenuMessages.USE_ETHEL.asSafety(player.wrappedLocale)
                        )
                    }
                }
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
            val selected = player.getOrPut(Keys.SELECTED_WILL) ?: return true
            if (player.ethel(selected) < Defaults.RELIC_GENERATOR_REQUIRE_ETHEL) return true
            // TODO implements
            return true
        }
    }

}