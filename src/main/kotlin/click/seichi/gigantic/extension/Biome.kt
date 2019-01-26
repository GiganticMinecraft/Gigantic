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

val MUSHROOMS = setOf(
        Biome.MUSHROOM_FIELDS,
        Biome.MUSHROOM_FIELD_SHORE
)

val MOUNTAINS = setOf(
        Biome.MOUNTAINS,
        Biome.GRAVELLY_MOUNTAINS,
        Biome.WOODED_MOUNTAINS,
        Biome.MODIFIED_GRAVELLY_MOUNTAINS,
        Biome.STONE_SHORE,
        Biome.SNOWY_MOUNTAINS,
        Biome.SNOWY_TAIGA_MOUNTAINS,
        Biome.TAIGA_MOUNTAINS
)

val FORESTS = setOf(
        Biome.FOREST,
        Biome.DARK_FOREST_HILLS,
        Biome.DARK_FOREST,
        Biome.BIRCH_FOREST,
        Biome.BIRCH_FOREST_HILLS,
        Biome.FLOWER_FOREST,
        Biome.TALL_BIRCH_FOREST,
        Biome.JUNGLE,
        Biome.JUNGLE_EDGE,
        Biome.JUNGLE_HILLS,
        Biome.MODIFIED_JUNGLE_EDGE,
        Biome.MODIFIED_JUNGLE
)

val HILLS = setOf(
        Biome.JUNGLE_HILLS,
        Biome.BIRCH_FOREST_HILLS,
        Biome.DARK_FOREST_HILLS,
        Biome.DESERT_HILLS,
        Biome.GIANT_SPRUCE_TAIGA_HILLS,
        Biome.GIANT_TREE_TAIGA_HILLS,
        Biome.SNOWY_TAIGA_HILLS,
        Biome.SWAMP_HILLS,
        Biome.TAIGA_HILLS,
        Biome.TALL_BIRCH_HILLS,
        Biome.WOODED_HILLS
)

val SNOWIES = setOf(
        Biome.SNOWY_TUNDRA,
        Biome.ICE_SPIKES,
        Biome.SNOWY_TAIGA,
        Biome.SNOWY_TAIGA_MOUNTAINS,
        Biome.FROZEN_RIVER,
        Biome.SNOWY_BEACH,
        Biome.SNOWY_TAIGA_HILLS,
        Biome.SNOWY_MOUNTAINS
)

val COLDS = setOf(
        Biome.MOUNTAINS,
        Biome.GRAVELLY_MOUNTAINS,
        Biome.WOODED_MOUNTAINS,
        Biome.MODIFIED_GRAVELLY_MOUNTAINS,
        Biome.TAIGA,
        Biome.TAIGA_MOUNTAINS,
        Biome.GIANT_TREE_TAIGA,
        Biome.TAIGA_HILLS,
        Biome.GIANT_TREE_TAIGA_HILLS,
        Biome.GIANT_SPRUCE_TAIGA,
        Biome.GIANT_SPRUCE_TAIGA_HILLS,
        Biome.STONE_SHORE
)

val WORMS = setOf(
        Biome.PLAINS,
        Biome.SUNFLOWER_PLAINS,
        Biome.FOREST,
        Biome.FLOWER_FOREST,
        Biome.BIRCH_FOREST,
        Biome.BIRCH_FOREST_HILLS,
        Biome.TALL_BIRCH_HILLS,
        Biome.TALL_BIRCH_FOREST,
        Biome.DARK_FOREST,
        Biome.DARK_FOREST_HILLS,
        Biome.SWAMP,
        Biome.SWAMP_HILLS,
        Biome.JUNGLE,
        Biome.JUNGLE_HILLS,
        Biome.JUNGLE_EDGE,
        Biome.MODIFIED_JUNGLE,
        Biome.MODIFIED_JUNGLE_EDGE,
        Biome.RIVER,
        Biome.BEACH,
        Biome.MUSHROOM_FIELDS,
        Biome.MUSHROOM_FIELD_SHORE
)

val DRIES = setOf(
        Biome.DESERT,
        Biome.DESERT_HILLS,
        Biome.DESERT_LAKES,
        Biome.SAVANNA,
        Biome.SAVANNA_PLATEAU,
        Biome.SHATTERED_SAVANNA_PLATEAU,
        Biome.SHATTERED_SAVANNA,
        Biome.BADLANDS,
        Biome.BADLANDS_PLATEAU,
        Biome.MODIFIED_BADLANDS_PLATEAU,
        Biome.ERODED_BADLANDS,
        Biome.MODIFIED_WOODED_BADLANDS_PLATEAU,
        Biome.WOODED_BADLANDS_PLATEAU
)


val Biome.isOcean: Boolean
    get() = OCEANS.contains(this)

val Biome.isRiver: Boolean
    get() = RIVERS.contains(this)

val Biome.isMushRoom: Boolean
    get() = MUSHROOMS.contains(this)

val Biome.isMountain: Boolean
    get() = MOUNTAINS.contains(this)

val Biome.isForest: Boolean
    get() = FORESTS.contains(this)

val Biome.isHill: Boolean
    get() = HILLS.contains(this)

val Biome.isSnowy: Boolean
    get() = SNOWIES.contains(this)

val Biome.isCold: Boolean
    get() = COLDS.contains(this)

val Biome.isWormed: Boolean
    get() = WORMS.contains(this)

val Biome.isDried: Boolean
    get() = DRIES.contains(this)
