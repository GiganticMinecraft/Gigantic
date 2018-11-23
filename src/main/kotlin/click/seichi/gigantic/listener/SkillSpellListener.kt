package click.seichi.gigantic.listener

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.offer
import click.seichi.gigantic.extension.remove
import click.seichi.gigantic.player.skill.Skill
import click.seichi.gigantic.player.spell.Spell
import org.bukkit.GameMode
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

/**
 * @author tar0ss
 */
class SkillSpellListener : Listener {

    private fun trySkill(player: Player, block: Block): Boolean {
        if (Skill.KODAMA_DRAIN.tryCast(player)) return true
        return false
    }

    private fun tryHeal(player: Player, block: Block): Boolean {
        if (Skill.HEAL.tryCast(player)) return true
        if (Spell.STELLA_CLAIR.tryCast(player)) return true

        return false
    }

    private fun trySpell(player: Player, block: Block): Boolean {

        val toggle = player.getOrPut(Keys.SPELL_TOGGLE)
        if (!toggle) return false

        return false
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onBlockBreak(event: BlockBreakEvent) {
        if (event.isCancelled) return
        val player = event.player ?: return
        val block = event.block ?: return
        if (event.player.gameMode != GameMode.SURVIVAL) return
        player.offer(Keys.BREAK_BLOCK, block)
        if (trySkill(player, block)) return
        if (trySpell(player, block)) return
        if (tryHeal(player, block)) return
        player.remove(Keys.BREAK_BLOCK)
    }

}