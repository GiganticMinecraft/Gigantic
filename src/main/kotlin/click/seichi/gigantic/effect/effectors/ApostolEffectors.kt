package click.seichi.gigantic.effect.effectors

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.animation.animations.effect.ApostolAnimations
import click.seichi.gigantic.effect.EffectParameters
import click.seichi.gigantic.effect.effector.ApostolEffector
import click.seichi.gigantic.extension.centralLocation
import click.seichi.gigantic.extension.update
import click.seichi.gigantic.sound.sounds.EffectSounds
import click.seichi.gigantic.util.Random
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

/**
 * @author tar0ss
 */
object ApostolEffectors {

    val DEFAULT = object : ApostolEffector {
        override fun apostolBreak(player: Player, base: Block, breakBlockSet: Set<Block>) {
            breakBlockSet.run {
                forEach { target ->
                    target.type = Material.AIR
                }
                // 凍結，火成等の処理を最後にまとめる
                forEach { target ->
                    target.update()
                }
            }
        }
    }

    val EXPLOSION = object : ApostolEffector {
        override fun apostolBreak(player: Player, base: Block, breakBlockSet: Set<Block>) {
            breakBlockSet.run {
                forEach { target ->
                    target.type = Material.AIR
                }
                forEach { target ->
                    if (Random.nextDouble() > EffectParameters.EXPLOSION_PROBABILITY.div(100.0)) return@forEach
                    ApostolAnimations.EXPLOSION.start(target.centralLocation)
                    EffectSounds.EXPLOSION.play(target.centralLocation)
                }
                // 凍結，火成等の処理を最後にまとめる
                forEach { target ->
                    target.update()
                }
            }
        }
    }

    val BLIZZARD = object : ApostolEffector {
        override fun apostolBreak(player: Player, base: Block, breakBlockSet: Set<Block>) {
            Gigantic.BROKEN_BLOCK_SET.addAll(breakBlockSet)
            breakBlockSet.forEach { target ->
                target.type = Material.PACKED_ICE
            }
            object : BukkitRunnable() {
                override fun run() {
                    Gigantic.BROKEN_BLOCK_SET.removeAll(breakBlockSet)
                    breakBlockSet.forEach { target ->
                        target.type = Material.AIR
                    }
                    breakBlockSet.forEach { target ->
                        if (Random.nextDouble() > EffectParameters.BLIZZARD_PROBABILITY.div(100.0)) return@forEach
                        ApostolAnimations.BLIZZARD.start(target.centralLocation)
                        EffectSounds.BLIZZARD.play(target.centralLocation)
                    }
                    breakBlockSet.forEach { target ->
                        target.update()
                    }
                }
            }.runTaskLater(Gigantic.PLUGIN, EffectParameters.BLIZZARD_TIME * 20L)
        }
    }

    val MAGIC = object : ApostolEffector {
        override fun apostolBreak(player: Player, base: Block, breakBlockSet: Set<Block>) {
            Gigantic.BROKEN_BLOCK_SET.addAll(breakBlockSet)
            breakBlockSet.forEach { target ->
                target.type = Random.nextWool()
            }
            object : BukkitRunnable() {
                override fun run() {
                    Gigantic.BROKEN_BLOCK_SET.removeAll(breakBlockSet)
                    breakBlockSet.forEach { target ->
                        target.type = Material.AIR
                    }
                    breakBlockSet.forEach { target ->
                        ApostolAnimations.MAGIC.start(target.centralLocation)
                    }
                    EffectSounds.MAGIC.play(base.centralLocation)
                    breakBlockSet.forEach { target ->
                        target.update()
                    }
                }
            }.runTaskLater(Gigantic.PLUGIN, EffectParameters.BLIZZARD_TIME * 20L)
        }
    }

}
