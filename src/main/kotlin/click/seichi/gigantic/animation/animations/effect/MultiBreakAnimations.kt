package click.seichi.gigantic.animation.animations.effect

import click.seichi.gigantic.animation.Animation
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
object MultiBreakAnimations {

    val EXPLOSION = Animation(0) { location, _ ->
        location.world?.spawnParticle(Particle.EXPLOSION_NORMAL, location, 1)
    }

    val BLIZZARD = Animation(0) { location, _ ->
        location.world?.spawnParticle(Particle.BLOCK_CRACK, location, 1, Material.PACKED_ICE.createBlockData())
    }

    val MAGIC = Animation(0) { location, _ ->
        location.world?.spawnParticle(Particle.NOTE, location, 1)
    }

    val FLAME = Animation(0) { location, _ ->
        location.world?.spawnParticle(Particle.FLAME, location, 2, 0.0, 0.0, 0.0, 0.2)
    }

    val WITCH_SCENT = Animation(0) { location, _ ->
        location.world?.spawnParticle(Particle.SPELL_WITCH, location, 3)
    }

    val SLIME = Animation(0) { location, _ ->
        location.world?.spawnParticle(Particle.SLIME, location, 1)
    }

    val BUBBLE = Animation(0) { location, _ ->
        location.world?.spawnParticle(Particle.BUBBLE_POP, location, 1)
    }

    private val alchemiaMap = mapOf(
            Material.GOLD_ORE to ItemStack(Material.GOLD_INGOT),
            Material.DIAMOND_ORE to ItemStack(Material.DIAMOND),
            Material.LAPIS_ORE to ItemStack(Material.LAPIS_LAZULI),
            Material.REDSTONE_ORE to ItemStack(Material.REDSTONE)
    )

    val ALCHEMIA = Animation(0) { location, _ ->
        val type = location.block.type
        if (!alchemiaMap.containsKey(type)) return@Animation
        location.world?.spawnParticle(Particle.ITEM_CRACK, location, 3, 0.0, 0.0, 0.0, 0.05, alchemiaMap.getValue(type))
    }


}