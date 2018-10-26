package click.seichi.gigantic.listener

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.animation.SkillAnimations
import click.seichi.gigantic.belt.Belt
import click.seichi.gigantic.button.buttons.FixedButtons
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.event.events.LevelUpEvent
import click.seichi.gigantic.event.events.RelationalBlockBreakEvent
import click.seichi.gigantic.event.events.ScoopEvent
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.player.ExpProducer
import click.seichi.gigantic.player.skill.Skills
import click.seichi.gigantic.popup.PopUpParameters
import click.seichi.gigantic.popup.SkillPops
import click.seichi.gigantic.raid.RaidManager
import click.seichi.gigantic.sound.sounds.PlayerSounds
import click.seichi.gigantic.sound.sounds.SkillSounds
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.block.Block
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


    private fun breakBlock(player: Player, block: Block) {

        // Gravity process
        block.fallUpperCrustBlock()

        // carry player cache
        player.manipulate(CatalogPlayerCache.MINE_BLOCK) {
            it.add(1L)
        }
        player.manipulate(CatalogPlayerCache.MINE_COMBO) {
            it.combo(1L)
            SkillPops.MINE_COMBO(it).pop(block.centralLocation.add(0.0, PopUpParameters.MINE_COMBO_DIFF, 0.0))
        }

        player.offer(Keys.HEAL_SKILL_BLOCK, block)
        Skills.HEAL.tryInvoke(player)

        // raid battle process
        RaidManager.playBattle(player, block.centralLocation.clone())

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
    fun onBlockBreak(event: BlockBreakEvent) {
        if (event.isCancelled) return
        if (event.player.gameMode != GameMode.SURVIVAL) return
        if (event is RelationalBlockBreakEvent) return

        val player = event.player ?: return
        val block = event.block ?: return

        breakBlock(player, block)
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
                        breakBlock(player, block)
                }
                else -> {
                }
            }
            event.itemStack = FixedButtons.BUCKET.getItemStack(player)
        }
    }

}