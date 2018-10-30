package click.seichi.gigantic.listener

import click.seichi.gigantic.event.events.ScoopEvent
import click.seichi.gigantic.extension.summonSpirit
import click.seichi.gigantic.player.LockedFunction
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDeathEvent

/**
 * @unicroak
 * @author tar0ss
 */
class SpiritListener : Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    fun onBlockBreak(event: BlockBreakEvent) {
        val player = event.player ?: return
        if (player.gameMode != GameMode.SURVIVAL) return
        if (!LockedFunction.SKILL_WILL_O_THE_WISP.isUnlocked(player)) return
        event.summonSpirit()
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onEntityDeath(event: EntityDeathEvent) {
        val player = event.entity.killer ?: return
        if (player.gameMode != GameMode.SURVIVAL) return
        if (!LockedFunction.SKILL_WILL_O_THE_WISP.isUnlocked(player)) return
        event.summonSpirit()
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onScoop(event: ScoopEvent) {
        val player = event.player
        if (player.gameMode != GameMode.SURVIVAL) return
        if (!LockedFunction.SKILL_WILL_O_THE_WISP.isUnlocked(player)) return
        event.summonSpirit()
    }

}