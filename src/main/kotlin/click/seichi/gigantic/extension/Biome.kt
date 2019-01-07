package click.seichi.gigantic.extension

import org.bukkit.block.Biome

/**
 * @author tar0ss
 */

val OCEANS = setOf(
        Biome.OCEAN,
        Biome.COLD_OCEAN,
        Biome.DEEP_COLD_OCEAN,
        Biome.DEEP_FROZEN_OCEAN,
        Biome.DEEP_LUKEWARM_OCEAN,
        Biome.DEEP_OCEAN,
        Biome.DEEP_WARM_OCEAN,
        Biome.FROZEN_OCEAN,
        Biome.LUKEWARM_OCEAN,
        Biome.WARM_OCEAN
)

val RIVERS = setOf(
        Biome.RIVER,
        Biome.FROZEN_RIVER
)


val Biome.isOcean: Boolean
    get() = OCEANS.contains(this)

val Biome.isRiver: Boolean
    get() = RIVERS.contains(this)