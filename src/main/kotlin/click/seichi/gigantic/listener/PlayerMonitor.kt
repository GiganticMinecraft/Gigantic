package click.seichi.gigantic.listener

import click.seichi.gigantic.breaker.Cutter
import click.seichi.gigantic.breaker.Miner
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.enchantment.ToolEnchantment
import click.seichi.gigantic.extension.isLog
import click.seichi.gigantic.extension.manipulate
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityRegainHealthEvent
import kotlin.math.roundToLong

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
            Cutter().breakRelationalBlock(player, block, true)
        }
        Miner().run {
            onBreakBlock(player, block)
            breakBlock(player, block)
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onDamage(event: EntityDamageEvent) {
        if (event.isCancelled) return
        val player = event.entity as? Player ?: return
        player.manipulate(CatalogPlayerCache.HEALTH) { health ->
            // 割合ダメージ
            val wrappedDamage = event.finalDamage.times(health.max / 20.0).roundToLong()
            health.decrease(wrappedDamage)
        }

    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onRegainHealth(event: EntityRegainHealthEvent) {
        if (event.isCancelled) return
        val player = event.entity as? Player ?: return
        player.manipulate(CatalogPlayerCache.HEALTH) { health ->
            // 割合回復
            val wrappedRegain = event.amount.times(health.max / 20.0).roundToLong()
            health.increase(wrappedRegain)
        }
    }

}