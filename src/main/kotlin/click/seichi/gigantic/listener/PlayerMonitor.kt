package click.seichi.gigantic.listener

import click.seichi.gigantic.animation.SkillAnimations
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.event.events.LevelUpEvent
import click.seichi.gigantic.event.events.RelationalBlockBreakEvent
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.player.ExpProducer
import click.seichi.gigantic.popup.SkillPops
import click.seichi.gigantic.raid.RaidManager
import click.seichi.gigantic.skill.Skills
import click.seichi.gigantic.sound.sounds.PlayerSounds
import click.seichi.gigantic.sound.sounds.SkillSounds
import org.bukkit.Bukkit
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
        if (event is RelationalBlockBreakEvent) return

        val player = event.player ?: return
        val block = event.block ?: return

        // Gravity process
        block.fallUpper()

        // carry player cache
        player.manipulate(CatalogPlayerCache.MINE_BLOCK) {
            it.add(1L)
        }
        player.manipulate(CatalogPlayerCache.MINE_COMBO) {
            it.combo(1L)
            SkillPops.MINE_COMBO(it).pop(block.centralLocation)
        }

        player.offer(Keys.HEAL_SKILL_BLOCK, block)
        Skills.HEAL.tryInvoke(player)

        // raid battle process
        RaidManager.playBattle(player)

        player.manipulate(CatalogPlayerCache.LEVEL) {
            it.calculate(ExpProducer.calcExp(player)) { current ->
                Bukkit.getPluginManager().callEvent(LevelUpEvent(current, player))
            }
            PlayerMessages.EXP_BAR_DISPLAY(it).sendTo(player)
        }


        val mineBurst = player.find(CatalogPlayerCache.MINE_BURST)
        if (mineBurst?.duringFire() == true)
            SkillAnimations.MINE_BURST_ON_BREAK.start(block.centralLocation)

        val currentCombo = player.find(CatalogPlayerCache.MINE_COMBO)?.currentCombo ?: 0

        // Sounds
        when {
            mineBurst?.duringFire() == true -> SkillSounds.MINE_BURST_ON_BREAK(currentCombo).play(block.centralLocation)
            else -> PlayerSounds.OBTAIN_EXP(currentCombo).playOnly(player)
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