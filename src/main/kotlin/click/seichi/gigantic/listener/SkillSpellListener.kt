package click.seichi.gigantic.listener

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.event.events.ScoopEvent
import click.seichi.gigantic.extension.find
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.offer
import click.seichi.gigantic.player.skill.Skills
import click.seichi.gigantic.player.spell.Spells
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

        return false
    }

    private fun tryHeal(player: Player, block: Block): Boolean {

        player.offer(Keys.HEAL_SKILL_BLOCK, block)
        if (Skills.HEAL.tryInvoke(player)) return true

        player.offer(Keys.STELLA_CLAIR_SKILL_BLOCK, block)
        if (Spells.STELLA_CLAIR.tryInvoke(player)) return true

        return false
    }

    private fun trySpell(player: Player, block: Block): Boolean {

        val toggle = player.getOrPut(Keys.SPELL_TOGGLE)
        if (!toggle) return false

        player.offer(Keys.TERRA_DRAIN_SKILL_BLOCK, block)
        if (Spells.TERRA_DRAIN.tryInvoke(player)) return true

        player.offer(Keys.GRAND_NATURA_SKILL_BLOCK, block)
        if (Spells.GRAND_NATURA.tryInvoke(player)) return true

        player.offer(Keys.AQUA_LINEA_SKILL_BLOCK, block)
        if (Spells.AQUA_LINEA.tryInvoke(player)) return true

        return false
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onBlockBreak(event: BlockBreakEvent) {
        if (event.isCancelled) return
        val player = event.player ?: return
        val block = event.block ?: return
        if (event.player.gameMode != GameMode.SURVIVAL) return
        if (trySkill(player, block)) return
        if (trySpell(player, block)) return
        if (tryHeal(player, block)) return
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onScoop(event: ScoopEvent) {
        if (event.isCancelled) return
        val player = event.player
        val block = event.block
        if (event.player.gameMode != GameMode.SURVIVAL) return
        val mineBurst = event.player.find(CatalogPlayerCache.MINE_BURST) ?: return
        if (mineBurst.duringFire()) return
        if (trySkill(player, block)) return
        if (trySpell(player, block)) return
        if (tryHeal(player, block)) return
    }

}