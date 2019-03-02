package click.seichi.gigantic.item.items.menu

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.item.Button
import click.seichi.gigantic.message.messages.WillMessages
import click.seichi.gigantic.will.Will
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object WillButtons {

    val WILL: (Will) -> Button = { will: Will ->
        object : Button {

            override fun toShownItemStack(player: Player): ItemStack? {
                if (!player.isProcessed(will)) return null
                return ItemStack(will.material).apply {
                    setDisplayName(player, WillMessages.WILL_MENU(will))
                    clearLore()
                    if (player.hasAptitude(will)) {
                        // あなたとの関係の表示
                        val relation = player.relationship(will)
                        addLore(player, WillMessages.RELATION(relation))
                    } else {
                        // 友好度の表示
                        val interval = will.grade.unlockAmount.div(100).toBigDecimal()
                        val friendRatio = player.getOrPut(Keys.WILL_SECRET_MAP.getValue(will)).toBigDecimal().div(interval)
                        addLore(player, WillMessages.FRIEND_RATIO(friendRatio))
                    }
                }
            }

            override fun tryClick(player: Player, event: InventoryClickEvent): Boolean {
                return false
            }
        }
    }
}