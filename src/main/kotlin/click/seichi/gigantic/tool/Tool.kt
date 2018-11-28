package click.seichi.gigantic.tool

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.item.HandItem
import click.seichi.gigantic.item.items.HandItems
import click.seichi.gigantic.player.Defaults
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
enum class Tool(
        val id: Int,
        private val tool: HandItem
) {
    PICKEL(1, HandItems.PICKEL),
    SHOVEL(2, HandItems.SHOVEL),
    AXE(3, HandItems.AXE),
    ;

    companion object {
        private val idMap = values().map { it.id to it }.toMap()
        fun findById(id: Int) = idMap[id]
    }

    fun findItemStack(player: Player) = tool.findItemStack(player)

    fun update(player: Player) {
        val slot = player.getOrPut(Keys.BELT).toolSlot
        player.inventory.setItem(slot, findItemStack(player) ?: Defaults.ITEM)
    }
}