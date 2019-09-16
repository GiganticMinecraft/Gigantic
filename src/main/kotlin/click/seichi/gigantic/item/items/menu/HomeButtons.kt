package click.seichi.gigantic.item.items.menu

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.menu.menus.teleport.TeleportToHomeMenu
import click.seichi.gigantic.message.messages.MenuMessages
import click.seichi.gigantic.message.messages.menu.TeleportMessages
import click.seichi.gigantic.player.Home
import click.seichi.gigantic.sound.sounds.PlayerSounds
import org.bukkit.ChatColor
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import java.util.*

/**
 * @author tar0ss
 */
object HomeButtons {

    // プレイヤーが削除したいホームの仮保管場所
    private val deleteMap = mutableMapOf<UUID, Int>()

    val HOME: (Int) -> Button = { homeId: Int ->
        object : Button {
            override fun toShownItemStack(player: Player): ItemStack? {
                val home = player.getOrPut(Keys.HOME_MAP)[homeId]
                return when (home) {
                    null -> itemStackOf(Material.BEDROCK) {
                        // クリックでホームを設定
                        setDisplayName("${ChatColor.GREEN}${ChatColor.BOLD}${ChatColor.UNDERLINE}" +
                                TeleportMessages.REGISTER_HOME.asSafety(player.wrappedLocale)
                        )
                    }
                    else -> itemStackOf(Material.RED_BED) {
                        setDisplayName(
                                "${ChatColor.AQUA}${ChatColor.BOLD}${ChatColor.UNDERLINE}" +
                                        home.name
                        )
                        clearLore()
                        if (player.gameMode != GameMode.SURVIVAL) {
                            addLore(
                                    "${ChatColor.RED}${ChatColor.BOLD}${ChatColor.UNDERLINE}" +
                                            TeleportMessages.HOME_NOT_SURVIVAL.asSafety(player.wrappedLocale)
                            )
                        } else {
                            addLore(
                                    "${ChatColor.GREEN}${ChatColor.BOLD}${ChatColor.UNDERLINE}" +
                                            TeleportMessages.CLICK_TO_TELEPORT_HOME.asSafety(player.wrappedLocale)
                            )
                        }

                        if (deleteMap.containsKey(player.uniqueId) && deleteMap[player.uniqueId] == homeId) {
                            addLore(
                                    "${ChatColor.RED}${ChatColor.BOLD}${ChatColor.UNDERLINE}" +
                                            TeleportMessages.HOME_DELETE.asSafety(player.wrappedLocale)
                            )
                        }
                        addLore(MenuMessages.LINE)
                        addLore(
                                "${ChatColor.GRAY}" +
                                        "/home ${home.id + 1} 名前"
                        )
                        addLore(
                                "${ChatColor.GRAY}" +
                                        TeleportMessages.CHANGE_NAME_LORE.asSafety(player.wrappedLocale)
                        )
                        addLore(
                                "${ChatColor.DARK_RED}" +
                                        TeleportMessages.HOME_DELETE_LORE.asSafety(player.wrappedLocale)
                        )

                    }
                }
            }

            override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
                if (player.gameMode != GameMode.SURVIVAL) return true
                val home = player.getOrPut(Keys.HOME_MAP)[homeId]
                val uniqueId = player.uniqueId
                // 削除
                if (event.isRightClick) {
                    if (home == null) return true
                    val deleteHomeId = deleteMap.remove(uniqueId)
                    if (deleteHomeId == null || deleteHomeId != homeId) {
                        deleteMap[uniqueId] = homeId
                        runTaskLater(20L) {
                            deleteMap.remove(uniqueId)
                            if (!player.isValid) return@runTaskLater
                            TeleportToHomeMenu.reopen(player)
                        }
                    } else {
                        player.transform(Keys.HOME_MAP) {
                            it.toMutableMap().apply {
                                remove(homeId)
                            }
                        }
                        PlayerSounds.REMOVE.playOnly(player)
                    }
                    TeleportToHomeMenu.reopen(player)
                    return true
                }

                // 設定もしくはテレポート

                if (home == null) {
                    // ホームを設定
                    player.transform(Keys.HOME_MAP) {
                        it.toMutableMap().apply {
                            put(homeId, Home(homeId, player.location))
                        }
                    }
                    PlayerSounds.TOGGLE.playOnly(player)
                } else {
                    // クリックでテレポート
                    player.teleportSafely(home.location)
                    PlayerSounds.TELEPORT.play(home.location)
                }
                TeleportToHomeMenu.reopen(player)
                return true
            }
        }
    }
}