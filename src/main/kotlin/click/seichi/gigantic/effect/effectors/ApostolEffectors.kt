package click.seichi.gigantic.effect.effectors

import click.seichi.gigantic.Gigantic
import click.seichi.gigantic.animation.animations.effect.ApostolAnimations
import click.seichi.gigantic.config.Config
import click.seichi.gigantic.effect.effector.ApostolEffector
import click.seichi.gigantic.extension.centralLocation
import click.seichi.gigantic.extension.update
import click.seichi.gigantic.sound.sounds.EffectSounds
import click.seichi.gigantic.util.Random
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import kotlin.math.roundToLong

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
                    ApostolAnimations.EXPLOSION.start(target.centralLocation)
                }
                EffectSounds.EXPLOSION.play(base.centralLocation)
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
                        ApostolAnimations.BLIZZARD.start(target.centralLocation)
                    }
                    EffectSounds.BLIZZARD.play(base.centralLocation)
                    breakBlockSet.forEach { target ->
                        target.update()
                    }
                }
            }.runTaskLater(Gigantic.PLUGIN, Config.SPELL_APOSTOL_DELAY.times(20.0).roundToLong())
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
            }.runTaskLater(Gigantic.PLUGIN, Config.SPELL_APOSTOL_DELAY.times(20.0).roundToLong())
        }
    }

    val FLAME = object : ApostolEffector {
        override fun apostolBreak(player: Player, base: Block, breakBlockSet: Set<Block>) {
            breakBlockSet.run {
                forEach { target ->
                    target.type = Material.AIR
                }
                forEach { target ->
                    ApostolAnimations.FLAME.start(target.centralLocation)
                }
                EffectSounds.FLAME.play(base.centralLocation)
                // 凍結，火成等の処理を最後にまとめる
                forEach { target ->
                    target.update()
                }
            }
        }
    }

    val WITCH_SCENT = object : ApostolEffector {
        override fun apostolBreak(player: Player, base: Block, breakBlockSet: Set<Block>) {
            breakBlockSet.run {
                forEach { target ->
                    target.type = Material.AIR
                }
                forEach { target ->
                    ApostolAnimations.WITCH_SCENT.start(target.centralLocation)
                }
                EffectSounds.WITCH_SCENT.play(base.centralLocation)
                // 凍結，火成等の処理を最後にまとめる
                forEach { target ->
                    target.update()
                }
            }
        }
    }

}
