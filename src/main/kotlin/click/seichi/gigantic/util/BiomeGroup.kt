package click.seichi.gigantic.util

import click.seichi.gigantic.message.LocalizedText
import org.bukkit.ChatColor
import org.bukkit.block.Biome
import org.bukkit.block.Block
import java.util.*

/**
 * @author tar0ss
 */
enum class BiomeGroup(
        private val localizedName: LocalizedText,
        val chatColor: ChatColor,
        vararg biomes: Biome
) {
    SNOWY(
            LocalizedText(
                    Locale.JAPANESE to "氷雪地帯"
            ),
            ChatColor.AQUA,
            Biome.SNOWY_TUNDRA,
            Biome.ICE_SPIKES,
            Biome.SNOWY_TAIGA,
            Biome.SNOWY_TAIGA_MOUNTAINS,
            Biome.FROZEN_RIVER,
            Biome.SNOWY_BEACH,
            Biome.SNOWY_TAIGA_HILLS,
            Biome.SNOWY_MOUNTAINS
    ),
    COLD(
            LocalizedText(
                    Locale.JAPANESE to "冷帯"
            ),
            ChatColor.DARK_AQUA,
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
    ),
    WORM(
            LocalizedText(
                    Locale.JAPANESE to "温帯"
            ),
            ChatColor.GREEN,
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
    ),
    DRY(
            LocalizedText(
                    Locale.JAPANESE to "乾燥帯"
            ),
            ChatColor.YELLOW,
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
    ),
    OCEAN(
            LocalizedText(
                    Locale.JAPANESE to "海洋"
            ),
            ChatColor.BLUE,
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
    ),
    ;

    val biomeSet = biomes.toSet()

    fun getName(locale: Locale) = localizedName.asSafety(locale)

    fun isBelongTo(biome: Biome): Boolean {
        return biomeSet.contains(biome)
    }

    companion object {
        fun findByBlock(block: Block): BiomeGroup? {
            return values().firstOrNull { it.isBelongTo(block.biome) }
        }
    }
}