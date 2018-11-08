package click.seichi.gigantic.button.buttons.menu

import click.seichi.gigantic.button.Button
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.menu.menus.TeleportMenu
import click.seichi.gigantic.menu.menus.TeleportToPlayerMenu
import click.seichi.gigantic.message.messages.menu.TeleportMessages
import click.seichi.gigantic.sound.sounds.PlayerSounds
import org.bukkit.ChatColor
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object TeleportButtons {

    val TELEPORT_TO_PLAYER = object : Button {

        override fun getItemStack(player: Player): ItemStack? {
            return ItemStack(Material.PLAYER_HEAD).apply {
                setDisplayName("${ChatColor.AQUA}" + TeleportMessages.TELEPORT_TO_PLAYER.asSafety(player.wrappedLocale))
            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
            if (event.inventory.holder === TeleportToPlayerMenu) return
            TeleportToPlayerMenu.open(player)
        }

    }

    val TELEPORT_TOGGLE = object : Button {

        override fun getItemStack(player: Player): ItemStack? {
            val toggle = player.getOrPut(Keys.TELEPORT_TOGGLE)
            return ItemStack(Material.DAYLIGHT_DETECTOR).apply {
                if (toggle)
                    setDisplayName(TeleportMessages.TELEPORT_TOGGLE_ON.asSafety(player.wrappedLocale))
                else
                    setDisplayName(TeleportMessages.TELEPORT_TOGGLE_OFF.asSafety(player.wrappedLocale))

                setLore(*TeleportMessages.TELEPORT_TOGGLE_LORE
                        .map { it.asSafety(player.wrappedLocale) }
                        .toTypedArray())

            }
        }

        override fun onClick(player: Player, event: InventoryClickEvent) {
            player.transform(Keys.TELEPORT_TOGGLE) { !it }
            PlayerSounds.TOGGLE.playOnly(player)
            TeleportMenu.reopen(player)
        }

    }

    val TELEPORT_PLAYER: (Player) -> Button = { to: Player ->
        object : Button {
            override fun getItemStack(player: Player): ItemStack? {
                return when {
                    !to.isValid -> ItemStack(Material.BLACK_STAINED_GLASS_PANE).apply {
                        setDisplayName("${ChatColor.RED}${to.name}")
                        setLore(*TeleportMessages.TELEPORT_PLAYER_INVALID_LORE
                                .map { it.asSafety(player.wrappedLocale) }
                                .toTypedArray())
                    }
                    !to.getOrPut(Keys.TELEPORT_TOGGLE) -> ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE).apply {
                        setDisplayName("${ChatColor.RED}${to.name}")
                        setLore(*TeleportMessages.TELEPORT_PLAYER_TOGGLE_OFF_LORE
                                .map { it.asSafety(player.wrappedLocale) }
                                .toTypedArray())
                    }
                    to.gameMode == GameMode.SPECTATOR -> ItemStack(Material.YELLOW_STAINED_GLASS_PANE).apply {
                        setDisplayName("${ChatColor.RED}${to.name}")
                        setLore(*TeleportMessages.TELEPORT_PLAYER_AFK_LORE
                                .map { it.asSafety(player.wrappedLocale) }
                                .toTypedArray())
                    }
                    to.gameMode != GameMode.SURVIVAL -> ItemStack(Material.BROWN_STAINED_GLASS_PANE).apply {
                        setDisplayName("${ChatColor.RED}${to.name}")
                        setLore(*TeleportMessages.TELEPORT_PLAYER_NOT_SURVIVAL_LORE
                                .map { it.asSafety(player.wrappedLocale) }
                                .toTypedArray())
                    }
                    to.world != player.world -> ItemStack(Material.CYAN_STAINED_GLASS_PANE).apply {
                        setDisplayName("${ChatColor.RED}${to.name}")
                        setLore(*TeleportMessages.TELEPORT_PLAYER_INVALID_WORLD_LORE(to.world)
                                .map { it.asSafety(player.wrappedLocale) }
                                .toTypedArray())
                    }
                    to.isFlying -> ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE).apply {
                        setDisplayName("${ChatColor.RED}${to.name}")
                        setLore(*TeleportMessages.TELEPORT_PLAYER_FLYING_LORE
                                .map { it.asSafety(player.wrappedLocale) }
                                .toTypedArray())
                    }
                    else -> to.getHead().apply {
                        setDisplayName("${ChatColor.GREEN}${to.name}")
                        setLore(*TeleportMessages.TELEPORT_PLAYER_LORE(player)
                                .map { it.asSafety(player.wrappedLocale) }
                                .toTypedArray())
                    }
                }
            }

            override fun onClick(player: Player, event: InventoryClickEvent) {
                if (!to.isValid) return
                if (!to.getOrPut(Keys.TELEPORT_TOGGLE)) return
                if (to.gameMode != GameMode.SURVIVAL) return
                if (to.world != player.world) return
                if (to.isFlying) return
                player.teleport(to)
                PlayerSounds.TELEPORT.play(to.location)
            }

        }
    }
}