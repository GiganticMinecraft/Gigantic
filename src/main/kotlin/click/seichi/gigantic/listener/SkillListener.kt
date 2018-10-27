package click.seichi.gigantic.listener

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.offer
import click.seichi.gigantic.player.skill.Skills
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

/**
 * @author tar0ss
 */
class SkillListener : Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    fun onBlockBreak(event: BlockBreakEvent) {
        if (event.isCancelled) return
        val player = event.player ?: return
        val block = event.block ?: return
        if (event.player.gameMode != GameMode.SURVIVAL) return

        player.offer(Keys.TERRA_DRAIN_SKILL_BLOCK, block)
        if (Skills.TERRA_DRAIN.tryInvoke(player)) return

        player.offer(Keys.HEAL_SKILL_BLOCK, block)
        if (Skills.HEAL.tryInvoke(player)) return
    }

}