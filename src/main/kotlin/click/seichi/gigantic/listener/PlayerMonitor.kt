package click.seichi.gigantic.listener

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.belt.Belt
import click.seichi.gigantic.button.buttons.FixedButtons
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.event.events.ScoopEvent
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.manipulate
import click.seichi.gigantic.player.breaker.Miner
import click.seichi.gigantic.player.breaker.Scooper
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityRegainHealthEvent
import org.bukkit.event.player.PlayerBucketFillEvent
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
        Miner().breakBlock(player, block)
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

    @EventHandler(priority = EventPriority.MONITOR)
    fun onBucketFill(event: PlayerBucketFillEvent) {
        if (event.isCancelled) return
        if (event.player.gameMode != GameMode.SURVIVAL) return
        val bucket = event.bucket ?: return
        val itemStack = event.itemStack ?: return
        val player = event.player ?: return
        val belt = player.getOrPut(Keys.BELT)
        val block = event.blockClicked ?: return

        if (belt == Belt.SCOOP && bucket == Material.BUCKET && itemStack.type != Material.BUCKET) {
            when (itemStack.type) {
                Material.LAVA_BUCKET,
                Material.WATER_BUCKET -> {
                    val scoopEvent = ScoopEvent(player, block)
                    Gigantic.PLUGIN.server.pluginManager.callEvent(scoopEvent)
                    if (!scoopEvent.isCancelled)
                        Scooper().breakBlock(player, block)
                }
                else -> {
                }
            }
            event.itemStack = FixedButtons.BUCKET.getItemStack(player)
        }
    }

}