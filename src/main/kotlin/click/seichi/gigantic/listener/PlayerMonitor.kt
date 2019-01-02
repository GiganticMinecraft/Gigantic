package click.seichi.gigantic.listener

import click.seichi.gigantic.breaker.Cutter
import click.seichi.gigantic.breaker.Miner
import click.seichi.gigantic.enchantment.ToolEnchantment
import click.seichi.gigantic.extension.effect
import click.seichi.gigantic.extension.isLog
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

/**
 * @author tar0ss
 */
class PlayerMonitor : Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    fun onBlockBreak(event: BlockBreakEvent) {
        if (event.isCancelled) return
        if (event.player.gameMode != GameMode.SURVIVAL) return

        val player = event.player ?: return
        val block = event.block ?: return

        if (block.isLog && ToolEnchantment.CUTTER.has(player)) {
            Cutter().breakRelationalBlock(block, true)
        }

        Miner().onBreakBlock(player, block)

        player.effect.generalBreak(player, block)
    }

}