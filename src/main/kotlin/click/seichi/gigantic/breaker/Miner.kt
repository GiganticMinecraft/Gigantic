package click.seichi.gigantic.breaker

import click.seichi.gigantic.animation.animations.SkillAnimations
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.cache.manipulator.catalog.CatalogPlayerCache
import click.seichi.gigantic.event.events.LevelUpEvent
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.message.messages.PlayerMessages
import click.seichi.gigantic.player.ExpProducer
import click.seichi.gigantic.popup.PopUpParameters
import click.seichi.gigantic.popup.SkillPops
import click.seichi.gigantic.sound.sounds.PlayerSounds
import click.seichi.gigantic.sound.sounds.SkillSounds
import org.bukkit.Bukkit
import org.bukkit.Effect
import org.bukkit.Particle
import org.bukkit.block.Block
import org.bukkit.entity.Player

/**
 * 通常破壊時の処理
 *
 * @author tar0ss
 */
open class Miner : Breaker {

    override fun breakBlock(player: Player, block: Block, isBroken: Boolean, showBrokenEffect: Boolean) {
        onBreakBlock(player, block)
        if (showBrokenEffect) {
            block.world.spawnParticle(Particle.BLOCK_CRACK, block.centralLocation, 1, block.blockData)
            block.world.playEffect(block.centralLocation, Effect.STEP_SOUND, block.type)
        }
        if (isBroken) return
        block.breakNaturally(player.inventory.itemInMainHand)
    }


    private fun onBreakBlock(player: Player, block: Block) {

        // Gravity process
        block.fallUpperCrustBlock()
        // Remove Liquid process
        block.removeUpperLiquidBlock()
        // bedrock process
        block.changeRelativeBedrock()

        // carry player cache
        player.manipulate(CatalogPlayerCache.MINE_BLOCK) {
            it.add(1L)
        }
        player.manipulate(CatalogPlayerCache.MINE_COMBO) {
            it.combo(1L)
            SkillPops.MINE_COMBO(it).pop(block.centralLocation.add(0.0, PopUpParameters.MINE_COMBO_DIFF, 0.0))
        }
        player.offer(Keys.IS_UPDATE_PROFILE, true)
        player.getOrPut(Keys.BAG).carry(player)

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
            mineBurst?.duringFire() == true -> SkillSounds.MINE_BURST_ON_BREAK(currentCombo).playOnly(player)
            else -> PlayerSounds.OBTAIN_EXP(currentCombo).playOnly(player)
        }
    }

}