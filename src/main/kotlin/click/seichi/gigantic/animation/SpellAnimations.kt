package click.seichi.gigantic.animation

import click.seichi.gigantic.extension.spawnColoredParticle
import click.seichi.gigantic.util.Random
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object SpellAnimations {


    val TERRA_DRAIN_ON_BREAK = Animation(1) { location, _ ->
        location.world.spawnParticle(Particle.VILLAGER_HAPPY, location, 1,
                Random.nextGaussian(0.0, 0.3),
                Random.nextGaussian(0.0, 0.3),
                Random.nextGaussian(0.0, 0.3)
        )
    }

    val TERRA_DRAIN_ON_FIRE = Animation(1) { location, _ ->
        location.world.spawnParticle(Particle.HEART, location, 10,
                Random.nextGaussian(0.0, 0.3),
                Random.nextGaussian(0.0, 0.3),
                Random.nextGaussian(0.0, 0.3)
        )
    }

    val STELLA_CLAIR = Animation(15) { location, _ ->
        location.world.spawnParticle(Particle.DOLPHIN, location, 10,
                Random.nextGaussian(0.0, 0.15),
                Random.nextGaussian(0.0, 0.15),
                Random.nextGaussian(0.0, 0.15)
        )
        location.world.spawnColoredParticle(location, Color.fromRGB(51, 103, 217), 1)
    }

    val GRAND_NATURA_ON_BREAK = Animation(3) { location, _ ->
        location.world.spawnParticle(Particle.ITEM_CRACK, location, 3,
                Random.nextGaussian(0.0, 0.3),
                Random.nextGaussian(0.0, 0.3),
                Random.nextGaussian(0.0, 0.3),
                ItemStack(Material.GREEN_WOOL)
        )
    }

    val GRAND_NATURA_ON_FIRE = Animation(1) { location, _ ->
        location.world.spawnParticle(Particle.TOTEM, location, 4,
                Random.nextGaussian(0.0, 0.3),
                Random.nextGaussian(0.0, 0.3),
                Random.nextGaussian(0.0, 0.3)
        )
    }

    val AQUA_LINEA_ON_BREAK = Animation(1) { location, _ ->
        location.world.spawnParticle(Particle.WATER_WAKE, location, 5,
                Random.nextGaussian(0.0, 0.3),
                Random.nextGaussian(0.0, 0.3),
                Random.nextGaussian(0.0, 0.3)
        )
        location.world.spawnParticle(Particle.BUBBLE_POP, location, 2,
                Random.nextGaussian(0.0, 0.3),
                Random.nextGaussian(0.0, 0.3),
                Random.nextGaussian(0.0, 0.3)
        )
    }

    val AQUA_LINEA_ON_FIRE = Animation(1) { location, _ ->
        location.world.spawnParticle(Particle.WATER_SPLASH, location, 10,
                Random.nextGaussian(0.0, 0.3),
                Random.nextGaussian(0.0, 0.3),
                Random.nextGaussian(0.0, 0.3)
        )
    }



}