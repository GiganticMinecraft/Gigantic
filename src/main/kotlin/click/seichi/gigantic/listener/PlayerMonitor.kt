package click.seichi.gigantic.listener

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.animation.SkillAnimations
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.event.events.LevelUpEvent
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.player.ExpProducer
import click.seichi.gigantic.popup.SkillPops
import click.seichi.gigantic.raid.RaidManager
import click.seichi.gigantic.sound.sounds.PlayerSounds
import click.seichi.gigantic.sound.sounds.SkillSounds
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

/**
 * @author tar0ss
 */
class PlayerMonitor : Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    fun onBlockBreak(event: BlockBreakEvent) {
        if (event.isCancelled) return
        if (event.player.gameMode != GameMode.SURVIVAL) return

        // Gravity process
        fallUpperBlock(event.block)

        // Player process
        val player = event.player ?: return
        val location = event.block.location.central

        // carry player cache
        player.manipulate(CatalogPlayerCache.MINE_BLOCK) {
            it.add(1L)
        }
        player.manipulate(CatalogPlayerCache.MINE_COMBO) {
            it.combo(1L)
            SkillPops.MINE_COMBO(it).pop(event.block.centralLocation)
        }

        // raid battle process
        RaidManager.playBattle(player)


        var isLevelUp = false
        player.manipulate(CatalogPlayerCache.LEVEL) {
            isLevelUp = it.calculate(ExpProducer.calcExp(player)) { current ->
                Bukkit.getPluginManager().callEvent(LevelUpEvent(current, player))
            }
            PlayerMessages.EXP_BAR_DISPLAY(it).sendTo(player)
        }

        val mineBurst = player.find(CatalogPlayerCache.MINE_BURST)
        if (mineBurst?.duringFire() == true)
            SkillAnimations.MINE_BURST_ON_BREAK.start(location)

        // Sounds
        when {
            isLevelUp -> PlayerSounds.LEVEL_UP.play(location)
            mineBurst?.duringFire() == true -> SkillSounds.MINE_BURST_ON_BREAK.play(location)
            else -> PlayerSounds.OBTAIN_EXP.playOnly(player)
        }
    }

    @Suppress("DEPRECATION")
    private fun fallUpperBlock(block: Block?) {
        var count = 0

        val fallTask = object : Runnable {
            override fun run() {
                val target = block?.getRelative(0, count + 1, 0) ?: return
                if (target.isCrust) {
                    target.location.world.spawnFallingBlock(
                            target.location.central.subtract(0.0, 0.5, 0.0),
                            target.type,
                            target.data
                    )
                    target.type = Material.AIR
                    count++
                    Bukkit.getScheduler().runTaskLater(Gigantic.PLUGIN, this, 2L)
                }
            }
        }
        Bukkit.getScheduler().runTask(Gigantic.PLUGIN, fallTask)
    }

}