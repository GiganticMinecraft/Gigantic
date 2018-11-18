package click.seichi.gigantic.animation.animations

import click.seichi.gigantic.animation.Animation
import click.seichi.gigantic.extension.spawnColoredParticle
import click.seichi.gigantic.extension.spawnColoredParticleSpherically
import click.seichi.gigantic.util.NoiseData
import click.seichi.gigantic.util.Random
import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.block.data.BlockData

/**
 * @author tar0ss
 */
object BattleMonsterAnimations {

    val AMBIENT = { color: Color ->
        Animation(1) { location, _ ->
            location.world.spawnColoredParticleSpherically(
                    location,
                    color,
                    count = 2,
                    radius = 1.5
            )
        }
    }

    val ATTACK_READY = { color: Color ->
        Animation(20) { location, _ ->
            location.world.spawnColoredParticle(
                    location,
                    color,
                    noiseData = NoiseData(0.01)
            )
        }
    }

    val ATTACK_READY_BLOCK = { data: BlockData ->
        Animation(1) { location, _ ->
            location.world.spawnParticle(
                    Particle.BLOCK_CRACK,
                    location.clone().add(
                            Random.nextGaussian(variance = 0.5),
                            Random.nextGaussian(variance = 0.5),
                            Random.nextGaussian(variance = 0.5)
                    ),
                    1,
                    data
            )
        }
    }

    val DEFENCE = { color: Color ->
        Animation(20) { location, _ ->
            location.world.spawnColoredParticle(
                    location,
                    color,
                    noiseData = NoiseData(0.01)
            )
        }
    }

    val DAMAGE_FROM_PLAYER = Animation(1) { location, l ->
        location.world.spawnParticle(
                Particle.DAMAGE_INDICATOR,
                location,
                1
        )
    }

}