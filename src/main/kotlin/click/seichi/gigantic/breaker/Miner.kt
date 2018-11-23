package click.seichi.gigantic.breaker

import click.seichi.gigantic.extension.centralLocation
import click.seichi.gigantic.extension.changeRelativeBedrock
import click.seichi.gigantic.extension.fallUpperCrustBlock
import click.seichi.gigantic.extension.removeUpperLiquidBlock
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
    }

}