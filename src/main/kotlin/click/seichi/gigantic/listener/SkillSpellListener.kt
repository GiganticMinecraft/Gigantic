package click.seichi.gigantic.listener

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.offer
import click.seichi.gigantic.player.skill.Skills
import click.seichi.gigantic.player.spell.Spells
import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

/**
 * @author tar0ss
 */
class SkillSpellListener : Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    fun onBlockBreak(event: BlockBreakEvent) {
        if (event.isCancelled) return
        val player = event.player ?: return
        val block = event.block ?: return
        if (event.player.gameMode != GameMode.SURVIVAL) return

        player.offer(Keys.TERRA_DRAIN_SKILL_BLOCK, block)
        if (Spells.TERRA_DRAIN.tryInvoke(player)) return

        player.offer(Keys.IGNIS_VOLCANO_SKILL_BLOCK, block)
        if (Spells.IGNIS_VOLCANO.tryInvoke(player)) return

        player.offer(Keys.HEAL_SKILL_BLOCK, block)
        if (Skills.HEAL.tryInvoke(player)) return

        player.offer(Keys.STELLA_CLAIR_SKILL_BLOCK, block)
        if (Spells.STELLA_CLAIR.tryInvoke(player)) return
    }

}