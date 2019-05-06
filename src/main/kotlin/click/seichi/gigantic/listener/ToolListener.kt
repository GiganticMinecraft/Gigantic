package click.seichi.gigantic.listener

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.manipulate
import click.seichi.gigantic.sound.sounds.PlayerSounds
import click.seichi.gigantic.tool.Tool
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent
import org.bukkit.inventory.EquipmentSlot

/**
 * @author tar0ss
 */
class ToolListener : Listener {


    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerSwapHandItems(event: PlayerSwapHandItemsEvent) {
        val player = event.player
        event.isCancelled = true
        var current: Tool? = null
        player.manipulate(CatalogPlayerCache.TOOL_SWITCHER) {
            current = it.current
            it.switch()
        }
        val nextTool = player.getOrPut(Keys.TOOL)
        if (current == nextTool) return
        nextTool.update(player)
        PlayerSounds.SWITCH.playOnly(player)
    }

    // 自動切り替え
    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        val player = event.player
        if (event.action != Action.LEFT_CLICK_BLOCK) return
        if (event.hand != EquipmentSlot.HAND) return
        if (!player.getOrPut(Keys.AUTO_SWITCH)) return
        val clickedBlock = event.clickedBlock ?: return
        val slot = player.inventory.heldItemSlot
        if (slot != player.getOrPut(Keys.BELT).toolSlot) return

        val suitableTool = Tool.findSuitableTool(clickedBlock) ?: return

        var current: Tool? = null
        player.manipulate(CatalogPlayerCache.TOOL_SWITCHER) {
            current = it.current
            it.setTool(suitableTool)
        }
        val nextTool = player.getOrPut(Keys.TOOL)
        if (current == nextTool) return
        nextTool.update(player)
        PlayerSounds.SWITCH.playOnly(player)
    }

}