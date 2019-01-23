package click.seichi.gigantic.menu.menus

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.item.items.menu.BackButton
import click.seichi.gigantic.menu.Menu
import click.seichi.gigantic.message.messages.MenuMessages
import click.seichi.gigantic.message.messages.menu.TeleportMessages
import click.seichi.gigantic.player.Defaults
import click.seichi.gigantic.player.Home
import click.seichi.gigantic.sound.sounds.PlayerSounds
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

/**
 * @author tar0ss
 */
object TeleportToHomeMenu : Menu() {

    override val size: Int
        get() = 18

    override fun getTitle(player: Player): String {
        return TeleportMessages.TELEPORT_TO_HOME.asSafety(player.wrappedLocale)
    }

    // プレイヤーが削除したいホームの仮保管場所
    private val deleteMap = mutableMapOf<UUID, Int>()

    init {
        registerButton(0, BackButton(this, TeleportMenu))
        val slotList = (9..17).step(2).toList()
        (0 until Defaults.MAX_HOME).forEach { homeId ->
            val slot = slotList.getOrNull(homeId) ?: return@forEach
            registerButton(slot, object : Button {
                override fun findItemStack(player: Player): ItemStack? {
                    val home = player.getOrPut(Keys.HOME_MAP)[homeId]
                    return when (home) {
                        null -> ItemStack(Material.BEDROCK).apply {
                            // クリックでホームを設定
                            setDisplayName("${ChatColor.GREEN}${ChatColor.BOLD}${ChatColor.UNDERLINE}" +
                                    TeleportMessages.REGISTER_HOME.asSafety(player.wrappedLocale)
                            )
                        }
                        else -> ItemStack(Material.RED_BED).apply {
                            // クリックでテレポート
                            setDisplayName(
                                    "${ChatColor.AQUA}${ChatColor.BOLD}${ChatColor.UNDERLINE}" +
                                            home.name
                            )
                            clearLore()
                            addLore(
                                    "${ChatColor.GREEN}${ChatColor.BOLD}${ChatColor.UNDERLINE}" +
                                            TeleportMessages.CLICK_TO_TELEPORT_HOME.asSafety(player.wrappedLocale)
                            )
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

                override fun onClick(player: Player, event: InventoryClickEvent): Boolean {
                    // 削除
                    if (event.isRightClick) {
                        val deleteHomeId = deleteMap.remove(player.uniqueId)
                        if (deleteHomeId == null || deleteHomeId != homeId) {
                            deleteMap[player.uniqueId] = homeId
                            object : BukkitRunnable() {
                                override fun run() {
                                    if (!player.isValid) return
                                    deleteMap.remove(player.uniqueId)
                                    reopen(player)
                                }
                            }.runTaskLater(Gigantic.PLUGIN, 20L)
                        } else {
                            player.transform(Keys.HOME_MAP) {
                                it.toMutableMap().apply {
                                    remove(homeId)
                                }
                            }
                            PlayerSounds.REMOVE.playOnly(player)
                        }
                        reopen(player)
                        return true
                    }

                    // 設定もしくはテレポート
                    val home = player.getOrPut(Keys.HOME_MAP)[homeId]

                    if (home == null) {
                        // ホームを設定
                        player.transform(Keys.HOME_MAP) {
                            it.toMutableMap().apply {
                                put(
                                        homeId,
                                        Home(
                                                homeId,
                                                player.location
                                        )
                                )
                            }
                        }
                        PlayerSounds.TOGGLE.playOnly(player)
                    } else {
                        // クリックでテレポート
                        player.teleport(home.location)
                        PlayerSounds.TELEPORT.play(home.location)
                    }
                    reopen(player)
                    return true
                }
            })

        }
    }
}