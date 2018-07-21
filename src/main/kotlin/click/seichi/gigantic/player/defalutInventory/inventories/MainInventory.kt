package click.seichi.gigantic.player.defalutInventory.inventories

import click.seichi.gigantic.extension.*
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.player.defalutInventory.DefaultInventory
import org.bukkit.ChatColor
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import java.util.*

/**
 * @author tar0ss
 */
object MainInventory : DefaultInventory() {

    val lastLocationMap = mutableMapOf<UUID, Location>()

    override val slotItemMap: Map<Int, SlotItem> = mapOf(
            9 to object : SlotItem {
                override fun getItemStack(player: Player): ItemStack? {
                    return player.getHead().apply {
                        setTitle(
                                LocalizedText(
                                        Locale.JAPANESE to "プロフィール"
                                ).asSafety(player.wrappedLocale)
                        )
                    }
                }

                override fun onClick(player: Player, event: InventoryClickEvent) {
                }
            },
            25 to object : SlotItem {
                override fun getItemStack(player: Player): ItemStack? {
                    return ItemStack(Material.RED_ROSE, 1, 8).apply {
                        when (player.gameMode) {
                            GameMode.SURVIVAL -> setDisplayName(
                                    LocalizedText(
                                            Locale.JAPANESE to "${ChatColor.GREEN}休憩"
                                    ).asSafety(player.wrappedLocale)
                            )
                            GameMode.SPECTATOR -> setDisplayName(
                                    LocalizedText(
                                            Locale.JAPANESE to "${ChatColor.GREEN}戻る"
                                    ).asSafety(player.wrappedLocale)
                            )
                        }
                    }
                }

                override fun onClick(player: Player, event: InventoryClickEvent) {
                    val gPlayer = player.gPlayer ?: return
                    when (player.gameMode) {
                        GameMode.SURVIVAL -> {
                            player.gameMode = GameMode.SPECTATOR
                            lastLocationMap[player.uniqueId] = player.location
                            gPlayer.defaultInventory.update(player)
                        }
                        GameMode.SPECTATOR -> {
                            player.gameMode = GameMode.SURVIVAL
                            lastLocationMap.remove(player.uniqueId)?.run {
                                player.teleport(this)
                            }
                            gPlayer.defaultInventory.update(player)
                        }
                    }
                }

            }
    )

}