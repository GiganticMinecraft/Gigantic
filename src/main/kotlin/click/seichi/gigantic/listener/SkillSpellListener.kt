package click.seichi.gigantic.listener

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.belt.Belt
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.event.events.ScoopEvent
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.message.messages.HookedItemMessages
import click.seichi.gigantic.player.LockedFunction
import click.seichi.gigantic.player.skill.Skills
import click.seichi.gigantic.player.spell.Spells
import click.seichi.gigantic.sound.sounds.SpellSounds
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

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

    var toggleCoolTime = false

    @EventHandler(priority = EventPriority.MONITOR)
    fun onInteract(event: PlayerInteractEvent) {
        val player = event.player ?: return
        val action = event.action ?: return
        val belt = player.getOrPut(Keys.BELT)
        if (belt == Belt.SCOOP) return
        if (event.player.gameMode != GameMode.SURVIVAL) return
        if (!LockedFunction.MANA.isUnlocked(player)) return
        if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) return
        if (toggleCoolTime) return

        toggleCoolTime = true
        Bukkit.getScheduler().runTaskLater(Gigantic.PLUGIN, {
            toggleCoolTime = false
        }, 5L)
        player.transform(Keys.SPELL_TOGGLE) { toggle ->
            val next = !toggle
            player.inventory.itemInOffHand =
                    if (next) ItemStack(Material.NETHER_STAR).apply {
                        setDisplayName(HookedItemMessages.MANA_STONE.asSafety(player.wrappedLocale))
                        setLore(*HookedItemMessages.MANA_STONE_LORE
                                .map { it.asSafety(player.wrappedLocale) }
                                .toTypedArray())
                    }
                    else ItemStack(Material.AIR)
            if (next) SpellSounds.TOGGLE_ON.playOnly(player)
            else SpellSounds.TOGGLE_OFF.playOnly(player)
            next
        }

    }

}