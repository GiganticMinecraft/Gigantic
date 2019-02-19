package click.seichi.gigantic.relic

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.messages.RelicMessages
import click.seichi.gigantic.player.Defaults
import click.seichi.gigantic.util.Junishi
import click.seichi.gigantic.util.MoonPhase
import click.seichi.gigantic.will.Will
import org.bukkit.Material
import org.bukkit.block.Biome
import org.bukkit.block.Block
import org.bukkit.entity.Player
import java.util.*

/**
 * @author tar0ss
 */
enum class WillRelic(
        val will: Will,
        val relic: Relic,
        private val multiplier: Double,
        val material: Material,
        val localizedLore: List<LocalizedText>
) {
    USED_COIN(Will.TERRA, Relic.USED_COIN, 0.6, Material.RED_TULIP, RelicMessages.USED_COIN_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.type == Material.GOLD_ORE
        }
    },
    ADVENTURER_SOLE(Will.TERRA, Relic.ADVENTURER_SOLE, 0.3, Material.RED_TULIP, RelicMessages.ADVENTURER_SOLE_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.type == Material.GRASS_BLOCK
        }
    },
    SUPER_MUSHROOM(Will.TERRA, Relic.SUPER_MUSHROOM, 0.2, Material.RED_TULIP, RelicMessages.SUPER_MUSHROOM_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.PLAINS
        }
    },
    RAINBOW_CLAY(Will.TERRA, Relic.RAINBOW_CLAY, 0.4, Material.RED_TULIP, RelicMessages.RAIBOW_CLAY_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.type == Material.CLAY
        }
    },
    CLAY_IMAGE(Will.TERRA, Relic.CLAY_IMAGE, 0.2, Material.RED_TULIP, RelicMessages.CLAY_IMAGE_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.BADLANDS
        }
    },
    MAMMMOTH_RAW_MEET(Will.TERRA, Relic.MAMMMOTH_RAW_MEET, 0.25, Material.RED_TULIP, RelicMessages.MAMMMOTH_RAW_MEET_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.SNOWY_TAIGA
        }
    },
    CAT_SAND(Will.TERRA, Relic.CAT_SAND, 0.3, Material.RED_TULIP, RelicMessages.CAT_SAND_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.type == Material.COARSE_DIRT
        }
    },
    ULURU_SCRAP(Will.TERRA, Relic.ULURU_SCRAP, 0.2, Material.RED_TULIP, RelicMessages.ULURU_SCRAP_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.MODIFIED_BADLANDS_PLATEAU
        }
    },
    SPHINX(Will.TERRA, Relic.SPHINX, 0.3, Material.RED_TULIP, RelicMessages.SPHINX_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.DESERT_LAKES
        }
    },
    SHELL(Will.AQUA, Relic.SHELL, 0.2, Material.TUBE_CORAL_FAN, RelicMessages.SHELL_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.BEACH
        }
    },
    SAIL(Will.AQUA, Relic.SAIL, 0.2, Material.TUBE_CORAL_FAN, RelicMessages.SAIL_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.DEEP_LUKEWARM_OCEAN
        }
    },
    DEEP_SEA_FISH_DIODE(Will.AQUA, Relic.DEEP_SEA_FISH_DIODE, 0.2, Material.TUBE_CORAL_FAN, RelicMessages.DEEP_SEA_FISH_DIODE_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.DEEP_COLD_OCEAN
        }
    },
    SEICHI_MACKEREL(Will.AQUA, Relic.SEICHI_MACKEREL, 0.2, Material.TUBE_CORAL_FAN, RelicMessages.SEICHI_MACKEREL_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.OCEAN
        }
    },
    MUSH_FISH(Will.AQUA, Relic.MUSH_FISH, 0.25, Material.TUBE_CORAL_FAN, RelicMessages.MUSH_FISH_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.MUSHROOM_FIELDS
        }
    },
    STEERING_WHEEL(Will.AQUA, Relic.STEERING_WHEEL, 0.2, Material.TUBE_CORAL_FAN, RelicMessages.STEERING_WHEEL_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.WARM_OCEAN
        }
    },
    WOOD_SLAB(Will.AQUA, Relic.WOOD_SLAB, 0.2, Material.TUBE_CORAL_FAN, RelicMessages.WOOD_SLAB_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.RIVER
        }
    },
    BROKEN_WATERMELON(Will.AQUA, Relic.BROKEN_WATERMELON, 0.9, Material.TUBE_CORAL_FAN, RelicMessages.BROKEN_WATERMELON_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.type == Material.MELON
        }
    },
    CUTE_WATERING_POT(Will.AQUA, Relic.CUTE_WATERING_POT, 0.25, Material.TUBE_CORAL_FAN, RelicMessages.CUTE_WATERING_POT_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.SUNFLOWER_PLAINS
        }
    },
    BEAST_BONE(Will.IGNIS, Relic.BEAST_BONE, 0.2, Material.POPPY, RelicMessages.BEAST_BONE_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.SHATTERED_SAVANNA
        }
    },
    THIN_TOOTH(Will.IGNIS, Relic.THIN_TOOTH, 0.2, Material.POPPY, RelicMessages.THIN_TOOTH_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.SHATTERED_SAVANNA_PLATEAU
        }
    },
    BROKEN_IVORY(Will.IGNIS, Relic.BROKEN_IVORY, 0.2, Material.POPPY, RelicMessages.BROKEN_IVORY_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.BADLANDS_PLATEAU
        }
    },
    WILL_O_WISP(Will.IGNIS, Relic.WILL_O_WISP, 0.9, Material.POPPY, RelicMessages.WILL_O_WISP_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.type == Material.PUMPKIN
        }
    },
    BIG_FUNG(Will.IGNIS, Relic.BIG_FUNG, 0.2, Material.POPPY, RelicMessages.BIG_FUNG_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.DESERT
        }
    },
    CAMP_FIRE_TRACE(Will.IGNIS, Relic.CAMP_FIRE_TRACE, 0.25, Material.POPPY, RelicMessages.CAMP_FIRE_TRACE_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.TAIGA_HILLS
        }
    },
    DESERT_CRYSTAL(Will.IGNIS, Relic.DESERT_CRYSTAL, 0.2, Material.POPPY, RelicMessages.DESERT_CRYSTAL_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.DESERT_HILLS
        }
    },
    CARNIVORE_BONE(Will.IGNIS, Relic.CARNIVORE_BONE, 0.2, Material.POPPY, RelicMessages.CARNIVORE_BONE_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.SAVANNA
        }
    },
    FRIED_POTATO(Will.IGNIS, Relic.FRIED_POTATO, 0.1, Material.POPPY, RelicMessages.FRIED_POTATO_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.temperature > 1.5
        }
    },
    BROKEN_BOW(Will.AER, Relic.BROKEN_BOW, 0.1, Material.AZURE_BLUET, RelicMessages.BROKEN_BOW_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.y in 63..84
        }
    },
    TIME_CAPSEL(Will.AER, Relic.TIME_CAPSEL, 0.25, Material.AZURE_BLUET, RelicMessages.TIME_CAPSEL_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.FLOWER_FOREST
        }
    },
    BROKEN_FORCE_FLAG(Will.AER, Relic.BROKEN_FORCE_FLAG, 0.8, Material.AZURE_BLUET, RelicMessages.BROKEN_FORCE_FLAG_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.isMossy
        }
    },
    BITTEN_LEATHER_BOOT(Will.AER, Relic.BITTEN_LEATHER_BOOT, 0.2, Material.AZURE_BLUET, RelicMessages.BITTEN_LEATHER_BOOT_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.SAVANNA_PLATEAU
        }
    },
    BROKEN_LEAD(Will.AER, Relic.BROKEN_LEAD, 0.25, Material.AZURE_BLUET, RelicMessages.BROKEN_LEAD_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.WOODED_HILLS
        }
    },
    OLD_AXE(Will.AER, Relic.OLD_AXE, 0.25, Material.AZURE_BLUET, RelicMessages.OLD_AXE_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.TAIGA
        }
    },
    VODKA_BOTTLE(Will.AER, Relic.VODKA_BOTTLE, 0.2, Material.AZURE_BLUET, RelicMessages.VODKA_BOTTLE_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.COLD_OCEAN
        }
    },
    ACID_GEAR(Will.AER, Relic.ACID_GEAR, 0.3, Material.AZURE_BLUET, RelicMessages.ACID_GEAR_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.JUNGLE_HILLS
        }
    },
    SLICED_ROPE(Will.AER, Relic.SLICED_ROPE, 0.25, Material.AZURE_BLUET, RelicMessages.SLICED_ROPE_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.TAIGA_MOUNTAINS
        }
    },
    WEB_SCRAP(Will.AER, Relic.WEB_SCRAP, 0.2, Material.AZURE_BLUET, RelicMessages.WEB_SCRAP_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.LUKEWARM_OCEAN
        }
    },
    CACAO_WATERMELON(Will.NATURA, Relic.CACAO_WATERMELON, 0.3, Material.FERN, RelicMessages.CACAO_WATERMELON_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.JUNGLE
        }
    },
    SUMMER_DAY(Will.NATURA, Relic.SUMMER_DAY, 0.1, Material.FERN, RelicMessages.SUMMER_DAY_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.temperature in 1.0..1.5
        }
    },
    BIRCH_MUSHROOM(Will.NATURA, Relic.BIRCH_MUSHROOM, 0.25, Material.FERN, RelicMessages.BIRCH_MUSHROOM_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.BIRCH_FOREST
        }
    },
    EGGPLANT(Will.NATURA, Relic.EGGPLANT, 0.25, Material.FERN, RelicMessages.EGGPLANT_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.WOODED_MOUNTAINS
        }
    },
    WHITE_FLOWER(Will.NATURA, Relic.WHITE_FLOWER, 0.25, Material.FERN, RelicMessages.WHITE_FLOWER_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.SNOWY_TUNDRA
        }
    },
    BANANA_SKIN(Will.NATURA, Relic.BANANA_SKIN, 0.3, Material.FERN, RelicMessages.BANANA_SKIN_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.MODIFIED_JUNGLE
        }
    },
    INSECT_HORN(Will.NATURA, Relic.INSECT_HORN, 0.28, Material.FERN, RelicMessages.INSECT_HORN_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.TALL_BIRCH_FOREST
        }
    },
    BROWN_SAP(Will.NATURA, Relic.BROWN_SAP, 0.28, Material.FERN, RelicMessages.BROWN_SAP_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.TALL_BIRCH_HILLS
        }
    },
    DOWN_TREE(Will.NATURA, Relic.DOWN_TREE, 0.25, Material.FERN, RelicMessages.DOWN_TREE_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.SNOWY_TAIGA_MOUNTAINS
        }
    },
    FROSTED_PINECONE(Will.GLACIES, Relic.FROSTED_PINECONE, 0.28, Material.BLUE_ORCHID, RelicMessages.FROSTED_PINECONE_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.GIANT_TREE_TAIGA
        }
    },
    CRYSTAL_OF_SNOW(Will.GLACIES, Relic.CRYSTAL_OF_SNOW, 0.1, Material.BLUE_ORCHID, RelicMessages.CRYSTAL_OF_SNOW_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.temperature in -0.5..0.15
        }
    },
    FROSTED_FISH(Will.GLACIES, Relic.FROSTED_FISH, 0.2, Material.BLUE_ORCHID, RelicMessages.FROSTED_FISH_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.FROZEN_RIVER
        }
    },
    NOT_MELTTED_ICE(Will.GLACIES, Relic.NOT_MELTTED_ICE, 0.1, Material.BLUE_ORCHID, RelicMessages.NOT_MELTTED_ICE_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.temperature < -0.5
        }
    },
    FROSTED_CRAFTBOX(Will.GLACIES, Relic.FROSTED_CRAFTBOX, 0.2, Material.BLUE_ORCHID, RelicMessages.FROSTED_CRAFTBOX_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.SNOWY_BEACH
        }
    },
    ICICLE(Will.GLACIES, Relic.ICICLE, 0.1, Material.BLUE_ORCHID, RelicMessages.ICICLE_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome.isSnowy
        }
    },
    FROSTED_WHEEL(Will.GLACIES, Relic.FROSTED_WHEEL, 0.28, Material.BLUE_ORCHID, RelicMessages.FROSTED_WHEEL_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.GIANT_TREE_TAIGA_HILLS
        }
    },
    SOFT_RIME(Will.GLACIES, Relic.SOFT_RIME, 0.3, Material.BLUE_ORCHID, RelicMessages.SOFT_RIME_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.ICE_SPIKES
        }
    },
    CRAMPONS(Will.GLACIES, Relic.CRAMPONS, 0.25, Material.BLUE_ORCHID, RelicMessages.CRAMPONS_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.SNOWY_TAIGA_HILLS
        }
    },
    A_PIECE_OF_CHALK(Will.SOLUM, Relic.A_PIECE_OF_CHALK, 0.1, Material.QUARTZ, RelicMessages.A_PIECE_OF_CHALK_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.y in 30..62
        }
    },
    BROKEN_TRAP(Will.SOLUM, Relic.BROKEN_TRAP, 0.9, Material.QUARTZ, RelicMessages.BROKEN_TRAP_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.isInfested
        }
    },
    FLUX_FOSSIL(Will.SOLUM, Relic.FLUX_FOSSIL, 0.2, Material.QUARTZ, RelicMessages.FLUX_FOSSIL_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.FROZEN_OCEAN
        }
    },
    FROSTED_ORE(Will.SOLUM, Relic.FROSTED_ORE, 0.4, Material.QUARTZ, RelicMessages.FROSTED_ORE_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.type == Material.BLUE_ICE
        }
    },
    MYCELIUM_PICKAXE(Will.SOLUM, Relic.MYCELIUM_PICKAXE, 0.28, Material.QUARTZ, RelicMessages.MYCELIUM_PICKAXE_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.MUSHROOM_FIELD_SHORE
        }
    },
    BEAUTIFUL_ORE(Will.SOLUM, Relic.BEAUTIFUL_ORE, 3.5, Material.QUARTZ, RelicMessages.BEAUTIFUL_ORE_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.type == Material.EMERALD_ORE
        }
    },
    IRON_ARMOR(Will.SOLUM, Relic.IRON_ARMOR, 0.4, Material.QUARTZ, RelicMessages.IRON_ARMOR_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.type == Material.IRON_ORE
        }
    },
    INDIGO(Will.SOLUM, Relic.INDIGO, 0.3, Material.QUARTZ, RelicMessages.INDIGO_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.type == Material.COAL_ORE
        }
    },
    DIAMOND_STONE(Will.SOLUM, Relic.DIAMOND_STONE, 2.4, Material.QUARTZ, RelicMessages.DIAMOND_STONE_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.type == Material.DIAMOND_ORE
        }
    },
    RUSTED_COMPASS(Will.VENTUS, Relic.RUSTED_COMPASS, 0.1, Material.LILAC, RelicMessages.RUSTED_COMPASS_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome.isOcean
        }
    },
    BEAUTIFUL_WING(Will.VENTUS, Relic.BEAUTIFUL_WING, 0.1, Material.LILAC, RelicMessages.BEAUTIFUL_WING_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.y > 85
        }
    },
    TUMBLEWEED(Will.VENTUS, Relic.TUMBLEWEED, 0.1, Material.LILAC, RelicMessages.TUMBLEWEED_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome.isDried
        }
    },
    HORN(Will.VENTUS, Relic.HORN, 0.1, Material.LILAC, RelicMessages.HORN_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome.isWormed
        }
    },
    SYLPH_LEAFE(Will.VENTUS, Relic.SYLPH_LEAFE, 0.25, Material.LILAC, RelicMessages.SYLPH_LEAFE_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.FOREST
        }
    },
    TENT_CLOTH(Will.VENTUS, Relic.TENT_CLOTH, 0.25, Material.LILAC, RelicMessages.TENT_CLOTH_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.SNOWY_MOUNTAINS
        }
    },
    PRICKLE(Will.VENTUS, Relic.PRICKLE, 0.23, Material.LILAC, RelicMessages.PRICKLE_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.MODIFIED_WOODED_BADLANDS_PLATEAU
        }
    },
    WING(Will.VENTUS, Relic.WING, 0.22, Material.LILAC, RelicMessages.WING_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.MODIFIED_GRAVELLY_MOUNTAINS
        }
    },
    NIDUS_AVIS(Will.VENTUS, Relic.NIDUS_AVIS, 0.23, Material.LILAC, RelicMessages.NIDUS_AVIS_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.WOODED_BADLANDS_PLATEAU
        }
    },
    ELIXIR(Will.LUX, Relic.ELIXIR, 0.1, Material.SUNFLOWER, RelicMessages.ELIXIR_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.temperature in 0.15..1.0
        }
    },
    OLD_MESSAGE_BOTTLE(Will.LUX, Relic.OLD_MESSAGE_BOTTLE, 0.22, Material.SUNFLOWER, RelicMessages.OLD_MESSAGE_BOTTLE_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.STONE_SHORE
        }
    },
    WHITE_APPLE(Will.LUX, Relic.WHITE_APPLE, 0.25, Material.SUNFLOWER, RelicMessages.WHITE_APPLE_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.BIRCH_FOREST_HILLS
        }
    },
    BUDDHIST_STATUE(Will.LUX, Relic.BUDDHIST_STATUE, 0.23, Material.SUNFLOWER, RelicMessages.BUDDHIST_STATUE_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.MOUNTAINS
        }
    },
    TREASURE_CASKET(Will.LUX, Relic.TREASURE_CASKET, 0.2, Material.SUNFLOWER, RelicMessages.TREASURE_CASKET_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.DEEP_OCEAN
        }
    },
    JIZO(Will.LUX, Relic.JIZO, 0.22, Material.SUNFLOWER, RelicMessages.JIZO_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.GRAVELLY_MOUNTAINS
        }
    },
    LIGHTNING_MOSS(Will.LUX, Relic.LIGHTNING_MOSS, 0.28, Material.SUNFLOWER, RelicMessages.LIGHTNING_MOSS_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.GIANT_SPRUCE_TAIGA_HILLS
        }
    },
    RED_DUST(Will.LUX, Relic.RED_DUST, 1.4, Material.SUNFLOWER, RelicMessages.RED_DUST_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.type == Material.REDSTONE_ORE
        }
    },
    BLUE_DUST(Will.LUX, Relic.BLUE_DUST, 1.4, Material.SUNFLOWER, RelicMessages.BLUE_DUST_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.type == Material.LAPIS_ORE
        }
    },
    BEAST_HORN(Will.UMBRA, Relic.BEAST_HORN, 0.1, Material.ALLIUM, RelicMessages.BEAST_HORN_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome.isCold
        }
    },
    BOTTLED_LIQUID(Will.UMBRA, Relic.BOTTLED_LIQUID, 0.2, Material.ALLIUM, RelicMessages.BOTTLED_LIQUID_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.SWAMP
        }
    },
    SLIME_LEES(Will.UMBRA, Relic.SLIME_LEES, 0.2, Material.ALLIUM, RelicMessages.SLIME_LEES_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.SWAMP_HILLS
        }
    },
    PURPLE_CHEESE(Will.UMBRA, Relic.PURPLE_CHEESE, 0.14, Material.ALLIUM, RelicMessages.PURPLE_CHEESE_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.type == Material.MYCELIUM
        }
    },
    SHADE_ARMOR(Will.UMBRA, Relic.SHADE_ARMOR, 0.24, Material.ALLIUM, RelicMessages.SHADE_ARMOR_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.DARK_FOREST_HILLS
        }
    },
    ORICHALCUM(Will.UMBRA, Relic.ORICHALCUM, 0.1, Material.ALLIUM, RelicMessages.ORICHALCUM_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.y < 29
        }
    },
    DARK_MATTER(Will.UMBRA, Relic.DARK_MATTER, 0.28, Material.ALLIUM, RelicMessages.DARK_MATTER_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.GIANT_SPRUCE_TAIGA
        }
    },
    BLACK_CLOTH(Will.UMBRA, Relic.BLACK_CLOTH, 0.2, Material.ALLIUM, RelicMessages.BLACK_CLOTH_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.DEEP_FROZEN_OCEAN
        }
    },
    BLOODSTAINED_SWORD(Will.UMBRA, Relic.BLOODSTAINED_SWORD, 0.3, Material.ALLIUM, RelicMessages.BLOODSTAINED_SWORD_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.ERODED_BADLANDS
        }
    },
    RLYEH_TEXT(Will.UMBRA, Relic.RLYEH_TEXT, 0.24, Material.ALLIUM, RelicMessages.RLYEH_TEXT_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.DARK_FOREST
        }
    },
    ALSTROMERIA_SEED(Will.SAKURA, Relic.ALSTROMERIA_SEED, 0.1, Material.BRAIN_CORAL, RelicMessages.ALSTROMERIA_SEED_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.type == Material.DIRT
        }
    },
    NIGHTINGALE_FEATHER(Will.SAKURA, Relic.NIGHTINGALE_FEATHER, 0.1, Material.BRAIN_CORAL, RelicMessages.NIGHTINGALE_FEATHER_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.y in 63..84
        }
    },
    OBOROZUKI_SWORD(Will.SAKURA, Relic.OBOROZUKI_SWORD, 0.1, Material.BRAIN_CORAL, RelicMessages.OBOROZUKI_SWORD_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.world.moonPhase == MoonPhase.MANGETSU && block.world.isMoonTime
        }
    },
    SAKURA_RACE_CAKE(Will.SAKURA, Relic.SAKURA_RACE_CAKE, 0.25, Material.BRAIN_CORAL, RelicMessages.SAKURA_RACE_CAKE_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.FOREST
        }
    },
    CERTIFICATE(Will.SAKURA, Relic.CERTIFICATE, 0.2, Material.BRAIN_CORAL, RelicMessages.CERTIFICATE_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.BEACH
        }
    },
    SCHOOL_BAG(Will.SAKURA, Relic.SCHOOL_BAG, 0.1, Material.BRAIN_CORAL, RelicMessages.SCHOOL_BAG_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.world.isDay
        }
    },
    KATSUO_SASHIMI(Will.SAKURA, Relic.KATSUO_SASHIMI, 0.2, Material.BRAIN_CORAL, RelicMessages.KATSUO_SASHIMI_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.WARM_OCEAN
        }
    },
    BOTAMOCHI(Will.SAKURA, Relic.BOTAMOCHI, 0.1, Material.BRAIN_CORAL, RelicMessages.BOTAMOCHI_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.world.junishiOfTime == Junishi.HITSUJI
        }
    },
    PEACH_CORE(Will.SAKURA, Relic.PEACH_CORE, 0.1, Material.BRAIN_CORAL, RelicMessages.PEACH_CORE_BONUS_LORE) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.type == Material.GRANITE
        }
    },


    ;

    companion object {
        fun calcMultiplier(player: Player, block: Block) = values().filter {
            player.hasRelic(it.relic)
        }.filter {
            it.isBonusTarget(block)
        }.map { it.calcMultiplier(player) }.sum()
    }

    abstract fun isBonusTarget(block: Block): Boolean

    fun getLore(locale: Locale) = localizedLore.map { it.asSafety(locale) }

    fun calcMultiplier(player: Player): Double {
        return kotlin.math.log(
                player.getOrPut(Keys.RELIC_MAP[relic]!!)
                        .plus(Defaults.RELIC_MUL_DIFFX), Defaults.RELIC_MUL_BASE
        ).times(multiplier)
    }

}