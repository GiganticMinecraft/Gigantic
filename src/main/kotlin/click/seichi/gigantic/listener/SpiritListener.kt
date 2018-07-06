package click.seichi.gigantic.listener

import click.seichi.gigantic.event.events.BlockBreakSkillEvent
import click.seichi.gigantic.event.events.ScheduleSummoningEvent
import click.seichi.gigantic.extension.summonSpirit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDeathEvent

/**
 * @unicroak
 * @author tar0ss
 */
class SpiritListener : Listener {

    @EventHandler
    fun onScheduleSummoning(event: ScheduleSummoningEvent) {
        event.summonSpirit()
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        event.summonSpirit()
    }

    @EventHandler
    fun onBlockBreakSkill(event: BlockBreakSkillEvent) {
        event.summonSpirit()
    }

    @EventHandler
    fun onEntityDeath(event: EntityDeathEvent) {
        event.summonSpirit()
    }

}