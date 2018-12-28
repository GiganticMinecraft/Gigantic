package click.seichi.gigantic.effect.effectors

import click.seichi.gigantic.effect.EffectParameters
import click.seichi.gigantic.effect.effector.ApostolEffector
import click.seichi.gigantic.extension.centralLocation
import click.seichi.gigantic.extension.update
import click.seichi.gigantic.util.Random
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
object ApostolEffectors {

    val DEFAULT = object : ApostolEffector {
        override fun apostolBreak(player: Player, breakBlockSet: Set<Block>) {
            breakBlockSet.run {
                forEach { target ->
                    if (target.isLiquid)
                        target.type = Material.AIR
                    else
                        target.breakNaturally(player.inventory.itemInMainHand)
                }
                // 凍結，火成等の処理を最後にまとめる
                forEach { target ->
                    target.update()
                }
            }
        }
    }

    val EXPLOSION = object : ApostolEffector {
        override fun apostolBreak(player: Player, breakBlockSet: Set<Block>) {
            breakBlockSet.run {
                forEach { target ->
                    if (target.isLiquid)
                        target.type = Material.AIR
                    else
                        target.breakNaturally(player.inventory.itemInMainHand)
                }
                forEach { target ->
                    if (Random.nextDouble() > EffectParameters.EXPLOSION_PROBABILITY.div(100.0)) return@forEach
                    target.world.createExplosion(target.centralLocation, 0F, false)
                }
                // 凍結，火成等の処理を最後にまとめる
                forEach { target ->
                    target.update()
                }
            }
        }
    }
}