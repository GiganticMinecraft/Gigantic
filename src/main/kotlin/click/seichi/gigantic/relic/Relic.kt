package click.seichi.gigantic.relic

import click.seichi.gigantic.GiganticEvent
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.head.Head
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.messages.RelicMessages
import click.seichi.gigantic.player.Defaults
import click.seichi.gigantic.util.BiomeGroup
import click.seichi.gigantic.util.Junishi
import click.seichi.gigantic.util.MoonPhase
import click.seichi.gigantic.will.Will
import org.bukkit.Material
import org.bukkit.block.Biome
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*

/**
 * @author tar0ss
 */
enum class Relic(
        val id: Int,
        private val localizedName: LocalizedText,
        private val localizedLore: List<LocalizedText> = listOf(),
        val localizedBonusLore: List<LocalizedText>,
        private val multiplier: Double,
        private val icon: ItemStack,
        val maxAmount: Long = Long.MAX_VALUE
) {
    /*CHICKEN_KING_CROWN(
            2,
            RelicMessages.CHICKEN_KING_CROWN,
            RelicMessages.CHICKEN_KING_CROWN_LORE,
            icon = Head.KING_CROWN.toItemStack()
    ),
    TURTLE_KING_CROWN(
            6,
            RelicMessages.TURTLE_KING_CROWN,
            RelicMessages.TURTLE_KING_CROWN_LORE,
            icon = Head.KING_CROWN.toItemStack()
    ),
    SPIDER_KING_CROWN(
            7,
            RelicMessages.SPIDER_KING_CROWN,
            RelicMessages.SPIDER_KING_CROWN_LORE,
            icon = Head.KING_CROWN.toItemStack()
    ),
    ZOMBIE_KING_CROWN(
            8,
            RelicMessages.ZOMBIE_KING_CROWN,
            RelicMessages.ZOMBIE_KING_CROWN_LORE,
            icon = Head.KING_CROWN.toItemStack()
    ),
    SKELETON_KING_CROWN(
            9,
            RelicMessages.SKELETON_KING_CROWN,
            RelicMessages.SKELETON_KING_CROWN_LORE,
            icon = Head.KING_CROWN.toItemStack()
    ),
    ORC_KING_CROWN(
            10,
            RelicMessages.ORC_KING_CROWN,
            RelicMessages.ORC_KING_CROWN_LORE,
            icon = Head.KING_CROWN.toItemStack()
    ),
    GHOST_KING_CROWN(
            11,
            RelicMessages.GHOST_KING_CROWN,
            RelicMessages.GHOST_KING_CROWN_LORE,
            icon = Head.KING_CROWN.toItemStack()
    ),
    CHIP_OF_WOOD(
            12,
            RelicMessages.CHIP_OF_WOOD,
            RelicMessages.CHIP_OF_WOOD_LORE,
            icon = ItemStack(Material.OAK_WOOD)
    ),
    MOISTENED_SLIME_BOLL(
            13,
            RelicMessages.MOISTENED_SLIME_BOLL,
            RelicMessages.MOISTENED_SLIME_BOLL_LORE,
            icon = ItemStack(Material.SLIME_BALL)
    ),
    FADING_ENDER_PEARL(
            14,
            RelicMessages.FADING_ENDER_PEARL,
            RelicMessages.FADING_ENDER_PEARL_LORE,
            icon = ItemStack(Material.ENDER_PEARL)
    ),
    SPELL_BOOK_EXPLOSION(100, RelicMessages.SPELL_BOOK_EXPLOSION, maxAmount = 1),
    GOLDEN_APPLE(150, RelicMessages.GOLDEN_APPLE),
    WILL_CRYSTAL_SAPPHIRE(200, RelicMessages.WILL_CRYSTAL_SAPPHIRE),
    WILL_CRYSTAL_RUBY(300, RelicMessages.WILL_CRYSTAL_RUBY),
    WILL_CRYSTAL_FLUORITE(400, RelicMessages.WILL_CRYSTAL_FLUORITE),
    WILL_CRYSTAL_ANDALUSITE(500, RelicMessages.WILL_CRYSTAL_ANDALUSITE),
    WILL_CRYSTAL_JADE(600, RelicMessages.WILL_CRYSTAL_JADE),*/

    // relic of will spirit
    BEAST_HORN(
            1000,
            RelicMessages.BEAST_HORN,
            RelicMessages.BEAST_HORN_LORE,
            RelicMessages.BEAST_HORN_BONUS_LORE,
            0.1,
            itemStackOf(Material.ALLIUM)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome.isCold
        }
    },
    ELIXIR(
            1001,
            RelicMessages.ELIXIR,
            RelicMessages.ELIXIR_LORE,
            RelicMessages.ELIXIR_BONUS_LORE,
            0.1,
            itemStackOf(Material.SUNFLOWER)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.temperature in 0.15..1.0
        }
    },
    SHELL(
            1002,
            RelicMessages.SHELL,
            RelicMessages.SHELL_LORE,
            RelicMessages.SHELL_BONUS_LORE,
            0.2,
            itemStackOf(Material.TUBE_CORAL_FAN)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.BEACH
        }
    },
    CACAO_WATERMELON(
            1003,
            RelicMessages.CACAO_WATERMELON,
            RelicMessages.CACAO_WATERMELON_LORE,
            RelicMessages.CACAO_WATERMELON_BONUS_LORE,
            0.3,
            itemStackOf(Material.FERN)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.JUNGLE
        }
    },
    RUSTED_COMPASS(
            1004,
            RelicMessages.RUSTED_COMPASS,
            RelicMessages.RUSTED_COMPASS_LORE,
            RelicMessages.RUSTED_COMPASS_BONUS_LORE,
            0.1,
            itemStackOf(Material.LILAC)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome.isOcean
        }
    },
    USED_COIN(
            1005,
            RelicMessages.USED_COIN,
            RelicMessages.USED_COIN_LORE,
            RelicMessages.USED_COIN_BONUS_LORE,
            0.6,
            itemStackOf(Material.RED_TULIP)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.type == Material.GOLD_ORE
        }
    },
    SUMMER_DAY(
            1006,
            RelicMessages.SUMMER_DAY,
            RelicMessages.SUMMER_DAY_LORE,
            RelicMessages.SUMMER_DAY_BONUS_LORE,
            0.1,
            itemStackOf(Material.FERN)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.temperature in 1.0..1.5
        }
    },
    A_PIECE_OF_CHALK(
            1007,
            RelicMessages.A_PIECE_OF_CHALK,
            RelicMessages.A_PIECE_OF_CHALK_LORE,
            RelicMessages.A_PIECE_OF_CHALK_BONUS_LORE,
            0.1,
            itemStackOf(Material.QUARTZ)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.y in 30..62
        }
    },
    OLD_MESSAGE_BOTTLE(
            1008,
            RelicMessages.OLD_MESSAGE_BOTTLE,
            RelicMessages.OLD_MESSAGE_BOTTLE_LORE,
            RelicMessages.OLD_MESSAGE_BOTTLE_BONUS_LORE,
            0.22,
            itemStackOf(Material.SUNFLOWER)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.STONE_SHORE
        }
    },
    BROKEN_BOW(
            1009,
            RelicMessages.BROKEN_BOW,
            RelicMessages.BROKEN_BOW_LORE,
            RelicMessages.BROKEN_BOW_BONUS_LORE,
            0.2,
            itemStackOf(Material.AZURE_BLUET)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.y in 63..84
        }
    },
    BIRCH_MUSHROOM(
            1010,
            RelicMessages.BIRCH_MUSHROOM,
            RelicMessages.BIRCH_MUSHROOM_LORE,
            RelicMessages.BIRCH_MUSHROOM_BONUS_LORE,
            0.25,
            itemStackOf(Material.FERN)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.BIRCH_FOREST
        }
    },
    ADVENTURER_SOLE(
            1011,
            RelicMessages.ADVENTURER_SOLE,
            RelicMessages.ADVENTURER_SOLE_LORE,
            RelicMessages.ADVENTURER_SOLE_BONUS_LORE,
            0.3,
            itemStackOf(Material.RED_TULIP)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.type == Material.GRASS_BLOCK
        }
    },
    BEAUTIFUL_WING(
            1012,
            RelicMessages.BEAUTIFUL_WING,
            RelicMessages.BEAUTIFUL_WING_LORE,
            RelicMessages.BEAUTIFUL_WING_BONUS_LORE,
            0.3,
            itemStackOf(Material.LILAC)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.y > 85
        }
    },
    WHITE_APPLE(
            1013,
            RelicMessages.WHITE_APPLE,
            RelicMessages.WHITE_APPLE_LORE,
            RelicMessages.WHITE_APPLE_BONUS_LORE,
            0.25,
            itemStackOf(Material.SUNFLOWER)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.BIRCH_FOREST_HILLS
        }
    },
    TIME_CAPSEL(
            1014,
            RelicMessages.TIME_CAPSEL,
            RelicMessages.TIME_CAPSEL_LORE,
            RelicMessages.TIME_CAPSEL_BONUS_LORE,
            0.25,
            itemStackOf(Material.AZURE_BLUET)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.FLOWER_FOREST
        }
    },
    BEAST_BONE(
            1015,
            RelicMessages.BEAST_BONE,
            RelicMessages.BEAST_BONE_LORE,
            RelicMessages.BEAST_BONE_BONUS_LORE,
            0.2,
            itemStackOf(Material.POPPY)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.SHATTERED_SAVANNA
        }
    },
    THIN_TOOTH(
            1016,
            RelicMessages.THIN_TOOTH,
            RelicMessages.THIN_TOOTH_LORE,
            RelicMessages.THIN_TOOTH_BONUS_LORE,
            0.2,
            itemStackOf(Material.POPPY)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.SHATTERED_SAVANNA_PLATEAU
        }
    },
    BROKEN_IVORY(
            1017,
            RelicMessages.BROKEN_IVORY,
            RelicMessages.BROKEN_IVORY_LORE,
            RelicMessages.BROKEN_IVORY_BONUS_LORE,
            0.2,
            itemStackOf(Material.POPPY)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.BADLANDS_PLATEAU
        }
    },
    BOTTLED_LIQUID(
            1018,
            RelicMessages.BOTTLED_LIQUID,
            RelicMessages.BOTTLED_LIQUID_LORE,
            RelicMessages.BOTTLED_LIQUID_BONUS_LORE,
            0.2,
            itemStackOf(Material.ALLIUM)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.SWAMP
        }
    },
    WILL_O_WISP(
            1019,
            RelicMessages.WILL_O_WISP,
            RelicMessages.WILL_O_WISP_LORE,
            RelicMessages.WILL_O_WISP_BONUS_LORE,
            0.9,
            itemStackOf(Material.POPPY)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.type == Material.PUMPKIN
        }
    },
    SLIME_LEES(
            1020,
            RelicMessages.SLIME_LEES,
            RelicMessages.SLIME_LEES_LORE,
            RelicMessages.SLIME_LEES_BONUS_LORE,
            0.2,
            itemStackOf(Material.ALLIUM)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.SWAMP_HILLS
        }
    },
    TUMBLEWEED(
            1021,
            RelicMessages.TUMBLEWEED,
            RelicMessages.TUMBLEWEED_LORE,
            RelicMessages.TUMBLEWEED_BONUS_LORE,
            0.1,
            itemStackOf(Material.LILAC)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome.isDried
        }
    },
    SUPER_MUSHROOM(
            1022,
            RelicMessages.SUPER_MUSHROOM,
            RelicMessages.SUPER_MUSHROOM_LORE,
            RelicMessages.SUPER_MUSHROOM_BONUS_LORE,
            0.2,
            itemStackOf(Material.RED_TULIP)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.PLAINS
        }
    },
    PURPLE_CHEESE(
            1023,
            RelicMessages.PURPLE_CHEESE,
            RelicMessages.PURPLE_CHEESE_LORE,
            RelicMessages.PURPLE_CHEESE_BONUS_LORE,
            0.14,
            itemStackOf(Material.ALLIUM)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.type == Material.MYCELIUM
        }
    },
    HORN(
            1024,
            RelicMessages.HORN,
            RelicMessages.HORN_LORE,
            RelicMessages.HORN_BONUS_LORE,
            0.1,
            itemStackOf(Material.LILAC)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome.isWormed
        }
    },
    BROKEN_FORCE_FLAG(
            1025,
            RelicMessages.BROKEN_FORCE_FLAG,
            RelicMessages.BROKEN_FORCE_FLAG_LORE,
            RelicMessages.BROKEN_FORCE_FLAG_BONUS_LORE,
            0.8,
            itemStackOf(Material.AZURE_BLUET)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.isMossy
        }
    },
    SYLPH_LEAFE(
            1026,
            RelicMessages.SYLPH_LEAFE,
            RelicMessages.SYLPH_LEAFE_LORE,
            RelicMessages.SYLPH_LEAFE_BONUS_LORE,
            0.25,
            itemStackOf(Material.LILAC)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.FOREST
        }
    },
    BROKEN_TRAP(
            1027,
            RelicMessages.BROKEN_TRAP,
            RelicMessages.BROKEN_TRAP_LORE,
            RelicMessages.BROKEN_TRAP_BONUS_LORE,
            0.9,
            itemStackOf(Material.QUARTZ)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.isInfested
        }
    },
    RAINBOW_CLAY(
            1028,
            RelicMessages.RAINBOW_CLAY,
            RelicMessages.RAINBOW_CLAY_LORE,
            RelicMessages.RAIBOW_CLAY_BONUS_LORE,
            0.4,
            itemStackOf(Material.RED_TULIP)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.type == Material.CLAY
        }
    },
    SHADE_ARMOR(
            1029,
            RelicMessages.SHADE_ARMOR,
            RelicMessages.SHADE_ARMOR_LORE,
            RelicMessages.SHADE_ARMOR_BONUS_LORE,
            0.24,
            itemStackOf(Material.ALLIUM)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.DARK_FOREST_HILLS
        }
    },
    CLAY_IMAGE(
            1030,
            RelicMessages.CLAY_IMAGE,
            RelicMessages.CLAY_IMAGE_LORE,
            RelicMessages.CLAY_IMAGE_BONUS_LORE,
            0.2,
            itemStackOf(Material.RED_TULIP)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.BADLANDS
        }
    },
    BIG_FUNG(
            1031,
            RelicMessages.BIG_FUNG,
            RelicMessages.BIG_FUNG_LORE,
            RelicMessages.BIG_FUNG_BONUS_LORE,
            0.2,
            itemStackOf(Material.POPPY)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.DESERT
        }
    },
    FLUX_FOSSIL(
            1032,
            RelicMessages.FLUX_FOSSIL,
            RelicMessages.FLUX_FOSSIL_LORE,
            RelicMessages.FLUX_FOSSIL_BONUS_LORE,
            0.2,
            itemStackOf(Material.QUARTZ)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.FROZEN_OCEAN
        }
    },
    BITTEN_LEATHER_BOOT(
            1033,
            RelicMessages.BITTEN_LEATHER_BOOT,
            RelicMessages.BITTEN_LEATHER_BOOT_LORE,
            RelicMessages.BITTEN_LEATHER_BOOT_BONUS_LORE,
            0.2,
            itemStackOf(Material.AZURE_BLUET)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.SAVANNA_PLATEAU
        }
    },
    BUDDHIST_STATUE(
            1034,
            RelicMessages.BUDDHIST_STATUE,
            RelicMessages.BUDDHIST_STATUE_LORE,
            RelicMessages.BUDDHIST_STATUE_BONUS_LORE,
            0.23,
            itemStackOf(Material.SUNFLOWER)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.MOUNTAINS
        }
    },
    BROKEN_LEAD(
            1035,
            RelicMessages.BROKEN_LEAD,
            RelicMessages.BROKEN_LEAD_LORE,
            RelicMessages.BROKEN_LEAD_BONUS_LORE,
            0.25,
            itemStackOf(Material.AZURE_BLUET)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.WOODED_HILLS
        }
    },
    EGGPLANT(
            1036,
            RelicMessages.EGGPLANT,
            RelicMessages.EGGPLANT_LORE,
            RelicMessages.EGGPLANT_BONUS_LORE,
            0.25,
            itemStackOf(Material.FERN)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.WOODED_MOUNTAINS
        }
    },
    OLD_AXE(
            1037,
            RelicMessages.OLD_AXE,
            RelicMessages.OLD_AXE_LORE,
            RelicMessages.OLD_AXE_BONUS_LORE,
            0.25,
            itemStackOf(Material.AZURE_BLUET)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.TAIGA
        }
    },
    CRYSTAL_OF_SNOW(
            1038,
            RelicMessages.CRYSTAL_OF_SNOW,
            RelicMessages.CRYSTAL_OF_SNOW_LORE,
            RelicMessages.CRYSTAL_OF_SNOW_BONUS_LORE,
            0.1,
            itemStackOf(Material.BLUE_ORCHID)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.temperature in -0.5..0.15
        }
    },
    FROSTED_FISH(
            1039,
            RelicMessages.FROSTED_FISH,
            RelicMessages.FROSTED_FISH_LORE,
            RelicMessages.FROSTED_FISH_BONUS_LORE,
            0.2,
            itemStackOf(Material.BLUE_ORCHID)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.FROZEN_RIVER
        }
    },
    VODKA_BOTTLE(
            1040,
            RelicMessages.VODKA_BOTTLE,
            RelicMessages.VODKA_BOTTLE_LORE,
            RelicMessages.VODKA_BOTTLE_BONUS_LORE,
            0.2,
            itemStackOf(Material.AZURE_BLUET)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.COLD_OCEAN
        }
    },
    SAIL(
            1041,
            RelicMessages.SAIL,
            RelicMessages.SAIL_LORE,
            RelicMessages.SAIL_BONUS_LORE,
            0.2,
            itemStackOf(Material.TUBE_CORAL_FAN)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.DEEP_LUKEWARM_OCEAN
        }
    },
    TREASURE_CASKET(
            1042,
            RelicMessages.TREASURE_CASKET,
            RelicMessages.TREASURE_CASKET_LORE,
            RelicMessages.TREASURE_CASKET_BONUS_LORE,
            0.2,
            itemStackOf(Material.SUNFLOWER)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.DEEP_OCEAN
        }
    },
    DEEP_SEA_FISH_DIODE(
            1043,
            RelicMessages.DEEP_SEA_FISH_DIODE,
            RelicMessages.DEEP_SEA_FISH_DIODE_LORE,
            RelicMessages.DEEP_SEA_FISH_DIODE_BONUS_LORE,
            0.2,
            itemStackOf(Material.TUBE_CORAL_FAN)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.DEEP_COLD_OCEAN
        }
    },
    SEICHI_MACKEREL(
            1044,
            RelicMessages.SEICHI_MACKEREL,
            RelicMessages.SEICHI_MACKEREL_LORE,
            RelicMessages.SEICHI_MACKEREL_BONUS_LORE,
            0.2,
            itemStackOf(Material.TUBE_CORAL_FAN)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.OCEAN
        }
    },
    NOT_MELTTED_ICE(
            1045,
            RelicMessages.NOT_MELTTED_ICE,
            RelicMessages.NOT_MELTTED_ICE_LORE,
            RelicMessages.NOT_MELTTED_ICE_BONUS_LORE,
            0.1,
            itemStackOf(Material.BLUE_ORCHID)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.temperature < -0.5
        }
    },
    WHITE_FLOWER(
            1046,
            RelicMessages.WHITE_FLOWER,
            RelicMessages.WHITE_FLOWER_LORE,
            RelicMessages.WHITE_FLOWER_BONUS_LORE,
            0.25,
            itemStackOf(Material.FERN)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.SNOWY_TUNDRA
        }
    },
    FROSTED_ORE(
            1047,
            RelicMessages.FROSTED_ORE,
            RelicMessages.FROSTED_ORE_LORE,
            RelicMessages.FROSTED_ORE_BONUS_LORE,
            0.4,
            itemStackOf(Material.QUARTZ)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.type == Material.BLUE_ICE
        }
    },
    MAMMMOTH_RAW_MEET(
            1048,
            RelicMessages.MAMMMOTH_RAW_MEET,
            RelicMessages.MAMMMOTH_RAW_MEET_LORE,
            RelicMessages.MAMMMOTH_RAW_MEET_BONUS_LORE,
            0.25,
            itemStackOf(Material.RED_TULIP)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.SNOWY_TAIGA
        }
    },
    TENT_CLOTH(
            1049,
            RelicMessages.TENT_CLOTH,
            RelicMessages.TENT_CLOTH_LORE,
            RelicMessages.TENT_CLOTH_BONUS_LORE,
            0.25,
            itemStackOf(Material.LILAC)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.SNOWY_MOUNTAINS
        }
    },
    CAMP_FIRE_TRACE(
            1050,
            RelicMessages.CAMP_FIRE_TRACE,
            RelicMessages.CAMP_FIRE_TRACE_LORE,
            RelicMessages.CAMP_FIRE_TRACE_BONUS_LORE,
            0.25,
            itemStackOf(Material.POPPY)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.TAIGA_HILLS
        }
    },
    FROSTED_PINECONE(
            1051,
            RelicMessages.FROSTED_PINECONE,
            RelicMessages.FROSTED_PINECONE_LORE,
            RelicMessages.FROSTED_PINECONE_BONUS_LORE,
            0.28,
            itemStackOf(Material.BLUE_ORCHID)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.GIANT_TREE_TAIGA
        }
    },
    FROSTED_CRAFTBOX(
            1052,
            RelicMessages.FROSTED_CRAFTBOX,
            RelicMessages.FROSTED_CRAFTBOX_LORE,
            RelicMessages.FROSTED_CRAFTBOX_BONUS_LORE,
            0.2,
            itemStackOf(Material.BLUE_ORCHID)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.SNOWY_BEACH
        }
    },
    MUSH_FISH(
            1054,
            RelicMessages.MUSH_FISH,
            RelicMessages.MUSH_FISH_LORE,
            RelicMessages.MUSH_FISH_BONUS_LORE,
            0.25,
            itemStackOf(Material.TUBE_CORAL_FAN)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.MUSHROOM_FIELDS
        }
    },
    MYCELIUM_PICKAXE(
            1055,
            RelicMessages.MYCELIUM_PICKAXE,
            RelicMessages.MYCELIUM_PICKAXE_LORE,
            RelicMessages.MYCELIUM_PICKAXE_BONUS_LORE,
            0.4,
            itemStackOf(Material.QUARTZ)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.type == Material.BLUE_ICE
        }
    },
    ACID_GEAR(
            1056,
            RelicMessages.ACID_GEAR,
            RelicMessages.ACID_GEAR_LORE,
            RelicMessages.ACID_GEAR_BONUS_LORE,
            0.3,
            itemStackOf(Material.AZURE_BLUET)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.JUNGLE_HILLS
        }
    },
    DESERT_CRYSTAL(
            1057,
            RelicMessages.DESERT_CRYSTAL,
            RelicMessages.DESERT_CRYSTAL_LORE,
            RelicMessages.DESERT_CRYSTAL_BONUS_LORE,
            0.2,
            itemStackOf(Material.POPPY)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.DESERT_HILLS
        }
    },
    CAT_SAND(
            1058,
            RelicMessages.CAT_SAND,
            RelicMessages.CAT_SAND_LORE,
            RelicMessages.CAT_SAND_BONUS_LORE,
            0.3,
            itemStackOf(Material.RED_TULIP)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.type == Material.COARSE_DIRT
        }
    },
    ORICHALCUM(
            1059,
            RelicMessages.ORICHALCUM,
            RelicMessages.ORICHALCUM_LORE,
            RelicMessages.ORICHALCUM_BONUS_LORE,
            0.4,
            itemStackOf(Material.ALLIUM)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.y < 29
        }
    },
    BEAUTIFUL_ORE(
            1060,
            RelicMessages.BEAUTIFUL_ORE,
            RelicMessages.BEAUTIFUL_ORE_LORE,
            RelicMessages.BEAUTIFUL_ORE_BONUS_LORE,
            3.5,
            itemStackOf(Material.QUARTZ)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.type == Material.EMERALD_ORE
        }
    },
    BANANA_SKIN(
            1061,
            RelicMessages.BANANA_SKIN,
            RelicMessages.BANANA_SKIN_LORE,
            RelicMessages.BANANA_SKIN_BONUS_LORE,
            0.3,
            itemStackOf(Material.FERN)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.MODIFIED_JUNGLE
        }
    },
    INSECT_HORN(
            1062,
            RelicMessages.INSECT_HORN,
            RelicMessages.INSECT_HORN_LORE,
            RelicMessages.INSECT_HORN_BONUS_LORE,
            0.28,
            itemStackOf(Material.FERN)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.TALL_BIRCH_FOREST
        }
    },
    ICICLE(
            1063,
            RelicMessages.ICICLE,
            RelicMessages.ICICLE_LORE,
            RelicMessages.ICICLE_BONUS_LORE,
            0.1,
            itemStackOf(Material.BLUE_ORCHID)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome.isSnowy
        }
    },
    FROSTED_WHEEL(
            1064,
            RelicMessages.FROSTED_WHEEL,
            RelicMessages.FROSTED_WHEEL_LORE,
            RelicMessages.FROSTED_WHEEL_BONUS_LORE,
            0.28,
            itemStackOf(Material.BLUE_ORCHID)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.GIANT_TREE_TAIGA_HILLS
        }
    },
    DARK_MATTER(
            1065,
            RelicMessages.DARK_MATTER,
            RelicMessages.DARK_MATTER_LORE,
            RelicMessages.DARK_MATTER_BONUS_LORE,
            0.28,
            itemStackOf(Material.ALLIUM)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.GIANT_SPRUCE_TAIGA
        }
    },
    STEERING_WHEEL(
            1066,
            RelicMessages.STEERING_WHEEL,
            RelicMessages.STEERING_WHEEL_LORE,
            RelicMessages.STEERING_WHEEL_BONUS_LORE,
            0.2,
            itemStackOf(Material.TUBE_CORAL_FAN)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.WARM_OCEAN
        }
    },
    SOFT_RIME(
            1067,
            RelicMessages.SOFT_RIME,
            RelicMessages.SOFT_RIME_LORE,
            RelicMessages.SOFT_RIME_BONUS_LORE,
            0.3,
            itemStackOf(Material.BLUE_ORCHID)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.ICE_SPIKES
        }
    },
    JIZO(
            1068,
            RelicMessages.JIZO,
            RelicMessages.JIZO_LORE,
            RelicMessages.JIZO_BONUS_LORE,
            0.22,
            itemStackOf(Material.SUNFLOWER)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.GRAVELLY_MOUNTAINS
        }
    },
    CRAMPONS(
            1069,
            RelicMessages.CRAMPONS,
            RelicMessages.CRAMPONS_LORE,
            RelicMessages.CRAMPONS_BONUS_LORE,
            0.25,
            itemStackOf(Material.BLUE_ORCHID)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.SNOWY_TAIGA_HILLS
        }
    },
    SLICED_ROPE(
            1070,
            RelicMessages.SLICED_ROPE,
            RelicMessages.SLICED_ROPE_LORE,
            RelicMessages.SLICED_ROPE_BONUS_LORE,
            0.25,
            itemStackOf(Material.AZURE_BLUET)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.TAIGA_MOUNTAINS
        }
    },
    BLACK_CLOTH(
            1071,
            RelicMessages.BLACK_CLOTH,
            RelicMessages.BLACK_CLOTH_LORE,
            RelicMessages.BLACK_CLOTH_BONUS_LORE,
            0.2,
            itemStackOf(Material.ALLIUM)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.DEEP_FROZEN_OCEAN
        }
    },
    LIGHTNING_MOSS(
            1072,
            RelicMessages.LIGHTNING_MOSS,
            RelicMessages.LIGHTNING_MOSS_LORE,
            RelicMessages.LIGHTNING_MOSS_BONUS_LORE,
            0.28,
            itemStackOf(Material.SUNFLOWER)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.GIANT_SPRUCE_TAIGA_HILLS
        }
    },
    BROWN_SAP(
            1073,
            RelicMessages.BROWN_SAP,
            RelicMessages.BROWN_SAP_LORE,
            RelicMessages.BROWN_SAP_BONUS_LORE,
            0.28,
            itemStackOf(Material.FERN)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.TALL_BIRCH_HILLS
        }
    },
    BLOODSTAINED_SWORD(
            1074,
            RelicMessages.BLOODSTAINED_SWORD,
            RelicMessages.BLOODSTAINED_SWORD_LORE,
            RelicMessages.BLOODSTAINED_SWORD_BONUS_LORE,
            0.3,
            itemStackOf(Material.ALLIUM)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.ERODED_BADLANDS
        }
    },
    WEB_SCRAP(
            1075,
            RelicMessages.WEB_SCRAP,
            RelicMessages.WEB_SCRAP_LORE,
            RelicMessages.WEB_SCRAP_BONUS_LORE,
            0.2,
            itemStackOf(Material.AZURE_BLUET)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.LUKEWARM_OCEAN
        }
    },
    DOWN_TREE(
            1076,
            RelicMessages.DOWN_TREE,
            RelicMessages.DOWN_TREE_LORE,
            RelicMessages.DOWN_TREE_BONUS_LORE,
            0.25,
            itemStackOf(Material.FERN)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.SNOWY_TAIGA_MOUNTAINS
        }
    },
    CARNIVORE_BONE(
            1077,
            RelicMessages.CARNIVORE_BONE,
            RelicMessages.CARNIVORE_BONE_LORE,
            RelicMessages.CARNIVORE_BONE_BONUS_LORE,
            0.2,
            itemStackOf(Material.POPPY)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.SAVANNA
        }
    },
    IRON_ARMOR(
            1078,
            RelicMessages.IRON_ARMOR,
            RelicMessages.IRON_ARMOR_LORE,
            RelicMessages.IRON_ARMOR_BONUS_LORE,
            0.4,
            itemStackOf(Material.QUARTZ)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.type == Material.IRON_ORE
        }
    },
    INDIGO(
            1079,
            RelicMessages.INDIGO,
            RelicMessages.INDIGO_LORE,
            RelicMessages.INDIGO_BONUS_LORE,
            0.3,
            itemStackOf(Material.QUARTZ)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.type == Material.COAL_ORE
        }
    },
    DIAMOND_STONE(
            1080,
            RelicMessages.DIAMOND_STONE,
            RelicMessages.DIAMOND_STONE_LORE,
            RelicMessages.DIAMOND_STONE_BONUS_LORE,
            2.4,
            itemStackOf(Material.QUARTZ)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.type == Material.DIAMOND_ORE
        }
    },
    ULURU_SCRAP(
            1081,
            RelicMessages.ULURU_SCRAP,
            RelicMessages.ULURU_SCRAP_LORE,
            RelicMessages.ULURU_SCRAP_BONUS_LORE,
            0.2,
            itemStackOf(Material.RED_TULIP)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.MODIFIED_BADLANDS_PLATEAU
        }
    },
    RED_DUST(
            1082,
            RelicMessages.RED_DUST,
            RelicMessages.RED_DUST_LORE,
            RelicMessages.RED_DUST_BONUS_LORE,
            1.4,
            itemStackOf(Material.SUNFLOWER)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.type == Material.REDSTONE_ORE
        }
    },
    BLUE_DUST(
            1083,
            RelicMessages.BLUE_DUST,
            RelicMessages.BLUE_DUST_LORE,
            RelicMessages.BLUE_DUST_BONUS_LORE,
            1.4,
            itemStackOf(Material.SUNFLOWER)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.type == Material.LAPIS_ORE
        }
    },
    FRIED_POTATO(
            1084,
            RelicMessages.FRIED_POTATO,
            RelicMessages.FRIED_POTATO_LORE,
            RelicMessages.FRIED_POTATO_BONUS_LORE,
            0.1,
            itemStackOf(Material.POPPY)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.temperature > 1.5
        }
    },
    SPHINX(
            1085,
            RelicMessages.SPHINX,
            RelicMessages.SPHINX_LORE,
            RelicMessages.SPHINX_BONUS_LORE,
            0.3,
            itemStackOf(Material.RED_TULIP)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.DESERT_LAKES
        }
    },
    PRICKLE(
            1086,
            RelicMessages.PRICKLE,
            RelicMessages.PRICKLE_LORE,
            RelicMessages.PRICKLE_BONUS_LORE,
            0.23,
            itemStackOf(Material.LILAC)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.MODIFIED_WOODED_BADLANDS_PLATEAU
        }
    },
    WOOD_SLAB(
            1087,
            RelicMessages.WOOD_SLAB,
            RelicMessages.WOOD_SLAB_LORE,
            RelicMessages.WOOD_SLAB_BONUS_LORE,
            0.2,
            itemStackOf(Material.TUBE_CORAL_FAN)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.RIVER
        }
    },
    BROKEN_WATERMELON(
            1088,
            RelicMessages.BROKEN_WATERMELON,
            RelicMessages.BROKEN_WATERMELON_LORE,
            RelicMessages.BROKEN_WATERMELON_BONUS_LORE,
            0.9,
            itemStackOf(Material.TUBE_CORAL_FAN)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.type == Material.MELON
        }
    },
    RLYEH_TEXT(
            1089,
            RelicMessages.RLYEH_TEXT,
            RelicMessages.RLYEH_TEXT_LORE,
            RelicMessages.RLYEH_TEXT_BONUS_LORE,
            0.24,
            itemStackOf(Material.ALLIUM)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.DARK_FOREST
        }
    },
    CUTE_WATERING_POT(
            1090,
            RelicMessages.CUTE_WATERING_POT,
            RelicMessages.CUTE_WATERING_POT_LORE,
            RelicMessages.CUTE_WATERING_POT_BONUS_LORE,
            0.25,
            itemStackOf(Material.TUBE_CORAL_FAN)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.SUNFLOWER_PLAINS
        }
    },
    WING(
            1091,
            RelicMessages.WING,
            RelicMessages.WING_LORE,
            RelicMessages.WING_BONUS_LORE,
            0.22,
            itemStackOf(Material.LILAC)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.MODIFIED_GRAVELLY_MOUNTAINS
        }
    },
    NIDUS_AVIS(
            1092,
            RelicMessages.NIDUS_AVIS,
            RelicMessages.NIDUS_AVIS_LORE,
            RelicMessages.NIDUS_AVIS_BONUS_LORE,
            0.23,
            itemStackOf(Material.LILAC)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.WOODED_BADLANDS_PLATEAU
        }
    },
    ALSTROMERIA_SEED(
            1093,
            RelicMessages.ALSTROMERIA_SEED,
            RelicMessages.ALSTROMERIA_SEED_LORE,
            RelicMessages.ALSTROMERIA_SEED_BONUS_LORE,
            0.1,
            itemStackOf(Material.BRAIN_CORAL)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.type == Material.DIRT
        }
    },
    NIGHTINGALE_FEATHER(
            1094,
            RelicMessages.NIGHTINGALE_FEATHER,
            RelicMessages.NIGHTINGALE_FEATHER_LORE,
            RelicMessages.NIGHTINGALE_FEATHER_BONUS_LORE,
            0.44,
            itemStackOf(Material.BRAIN_CORAL)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.y in 63..84 && block.biome.isWormed
        }
    },
    OBOROZUKI_SWORD(
            1095,
            RelicMessages.OBOROZUKI_SWORD,
            RelicMessages.OBOROZUKI_SWORD_LORE,
            RelicMessages.OBOROZUKI_SWORD_BONUS_LORE,
            0.1,
            itemStackOf(Material.BRAIN_CORAL)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.world.moonPhase == MoonPhase.MIKAZUKI && block.world.isMoonTime
        }
    },
    SAKURA_RACE_CAKE(
            1096,
            RelicMessages.SAKURA_RACE_CAKE,
            RelicMessages.SAKURA_RACE_CAKE_LORE,
            RelicMessages.SAKURA_RACE_CAKE_BONUS_LORE,
            0.17,
            itemStackOf(Material.BRAIN_CORAL)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.FOREST || block.biome == Biome.BIRCH_FOREST
        }
    },
    CERTIFICATE(
            1097,
            RelicMessages.CERTIFICATE,
            RelicMessages.CERTIFICATE_LORE,
            RelicMessages.CERTIFICATE_BONUS_LORE,
            0.27,
            itemStackOf(Material.BRAIN_CORAL)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.BEACH && block.type == Material.SAND
        }
    },
    SCHOOL_BAG(
            1098,
            RelicMessages.SCHOOL_BAG,
            RelicMessages.SCHOOL_BAG_LORE,
            RelicMessages.SCHOOL_BAG_BONUS_LORE,
            0.08,
            itemStackOf(Material.BRAIN_CORAL)

    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.world.isDay
        }
    },
    KATSUO_SASHIMI(
            1099,
            RelicMessages.KATSUO_SASHIMI,
            RelicMessages.KATSUO_SASHIMI_LORE,
            RelicMessages.KATSUO_SASHIMI_BONUS_LORE,
            0.16,
            itemStackOf(Material.BRAIN_CORAL)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.WARM_OCEAN || block.biome == Biome.LUKEWARM_OCEAN
        }
    },
    BOTAMOCHI(
            1100,
            RelicMessages.BOTAMOCHI,
            RelicMessages.BOTAMOCHI_LORE,
            RelicMessages.BOTAMOCHI_BONUS_LORE,
            0.1,
            itemStackOf(Material.BRAIN_CORAL)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.world.junishiOfTime == Junishi.HITSUJI
        }
    },
    PEACH_CORE(
            1101,
            RelicMessages.PEACH_CORE,
            RelicMessages.PEACH_CORE_LORE,
            RelicMessages.PEACH_CORE_BONUS_LORE,
            0.1,
            itemStackOf(Material.BRAIN_CORAL)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.type == Material.GRANITE
        }
    },
    CUP_OF_KING(
            1102,
            RelicMessages.CUP_OF_KING,
            RelicMessages.CUP_OF_KING_LORE,
            RelicMessages.CUP_OF_KING_BONUS_LORE,
            0.1,
            Head.KING_CROWN.toItemStack()
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return GiganticEvent.JMS_KING.isActive()
        }
    },
    BOILED_CANOLA_FLOWER(
            1103,
            RelicMessages.BOILED_CANOLA_FLOWER,
            RelicMessages.BOILED_CANOLA_FLOWER_LORE,
            RelicMessages.BOILED_CANOLA_FLOWER_BONUS_LORE,
            0.42,
            itemStackOf(Material.BRAIN_CORAL)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.SUNFLOWER_PLAINS && block.type == Material.GRASS_BLOCK
        }
    },
    MORNING_GLORY_LEAVES(
            1104,
            RelicMessages.MORNING_GLORY_LEAVES,
            RelicMessages.MORNING_GLORY_LEAVES_LORE,
            RelicMessages.MORNING_GLORY_LEAVES_BONUS_LORE,
            0.12,
            itemStackOf(Material.PRISMARINE_SHARD)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.world.isBreakDown
        }
    },
    FUJI_ROUND_FUN(
            1105,
            RelicMessages.FUJI_ROUND_FUN,
            RelicMessages.FUJI_ROUND_FUN_LORE,
            RelicMessages.FUJI_ROUND_FUN_BONUS_LORE,
            0.7,
            itemStackOf(Material.PRISMARINE_SHARD)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.y > 85 && block.biome == Biome.MOUNTAINS
        }
    },
    HITSUMABUSHI(
            1106,
            RelicMessages.HITSUMABUSHI,
            RelicMessages.HITSUMABUSHI_LORE,
            RelicMessages.HITSUMABUSHI_BONUS_LORE,
            0.1,
            itemStackOf(Material.PRISMARINE_SHARD)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.world.junishiOfTime == Junishi.USHI
        }
    },
    MOSQUITO_COIL(
            1107,
            RelicMessages.MOSQUITO_COIL,
            RelicMessages.MOSQUITO_COIL_LORE,
            RelicMessages.MOSQUITO_COIL_BONUS_LORE,
            0.1,
            itemStackOf(Material.PRISMARINE_SHARD)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.world.moonPhase == MoonPhase.IMACHIZUKI && block.world.isMoonTime
        }
    },
    GOLD_FISH(
            1108,
            RelicMessages.GOLD_FISH,
            RelicMessages.GOLD_FISH_LORE,
            RelicMessages.GOLD_FISH_BONUS_LORE,
            1.0,
            itemStackOf(Material.PRISMARINE_SHARD)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biomeGroup == BiomeGroup.OCEAN && block.type == Material.GOLD_ORE
        }
    },
    WIND_BELL(
            1109,
            RelicMessages.WIND_BELL,
            RelicMessages.WIND_BELL_LORE,
            RelicMessages.WIND_BELL_BONUS_LORE,
            0.1,
            itemStackOf(Material.PRISMARINE_SHARD)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.world.junishiOfTime == Junishi.SARU
        }
    },
    FIREFLY(
            1110,
            RelicMessages.FIREFLY,
            RelicMessages.FIREFLY_LORE,
            RelicMessages.FIREFLY_BONUS_LORE,
            0.08,
            itemStackOf(Material.PRISMARINE_SHARD)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.world.isNight
        }
    },
    STRAW_HAT(
            1111,
            RelicMessages.STRAW_HAT,
            RelicMessages.STRAW_HAT_LORE,
            RelicMessages.STRAW_HAT_BONUS_LORE,
            0.14,
            itemStackOf(Material.PRISMARINE_SHARD)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.BADLANDS_PLATEAU || block.biome == Biome.BADLANDS
        }
    },
    TERU_TERU(
            1112,
            RelicMessages.TERU_TERU,
            RelicMessages.TERU_TERU_LORE,
            RelicMessages.TERU_TERU_BONUS_LORE,
            0.1,
            itemStackOf(Material.PRISMARINE_SHARD)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.world.moonPhase == MoonPhase.ZYUUSANYA && block.world.isMoonTime
        }
    },
    YUKATA(
            1113,
            RelicMessages.YUKATA,
            RelicMessages.YUKATA_LORE,
            RelicMessages.YUKATA_BONUS_LORE,
            0.12,
            itemStackOf(Material.PRISMARINE_SHARD)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.world.isTwilight
        }
    },
    MARBLING_MEAT(
            1114,
            RelicMessages.MARBLING_MEAT,
            RelicMessages.MARBLING_MEAT_LORE,
            RelicMessages.MARBLING_MEAT_BONUS_LORE,
            0.4,
            itemStackOf(Material.LANTERN)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.temperature in -0.5..0.15 && block.world.isMoonTime && block.world.moonPhase == MoonPhase.ZYOGEN
        }
    },
    SHIRANUI(
            1115,
            RelicMessages.SHIRANUI,
            RelicMessages.SHIRANUI_LORE,
            RelicMessages.SHIRANUI_BONUS_LORE,
            0.1,
            itemStackOf(Material.LANTERN)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.world.junishiOfTime == Junishi.NE
        }
    },
    MOMIJI(
            1116,
            RelicMessages.MOMIJI,
            RelicMessages.MOMIJI_LORE,
            RelicMessages.MOMIJI_BONUS_LORE,
            0.08,
            itemStackOf(Material.LANTERN)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.WOODED_MOUNTAINS || (block.world.isMoonTime && block.world.moonPhase == MoonPhase.NIZYUROKUYA)
        }
    },
    TOUROU(
            1117,
            RelicMessages.TOUROU,
            RelicMessages.TOUROU_LORE,
            RelicMessages.TOUROU_BONUS_LORE,
            0.14,
            itemStackOf(Material.LANTERN)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.type == Material.DIORITE
        }
    },
    SCARECROW(
            1118,
            RelicMessages.SCARECROW,
            RelicMessages.SCARECROW_LORE,
            RelicMessages.SCARECROW_BONUS_LORE,
            0.14,
            itemStackOf(Material.LANTERN)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.type == Material.ANDESITE
        }
    },
    TSUKIMI_UDON(
            1119,
            RelicMessages.TSUKIMI_UDON,
            RelicMessages.TSUKIMI_UDON_LORE,
            RelicMessages.TSUKIMI_UDON_BONUS_LORE,
            0.1,
            itemStackOf(Material.LANTERN)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.world.isMoonTime && block.world.moonPhase == MoonPhase.MANGETSU
        }
    },
    CHRYSANTHEMUM_DOLL(
            1120,
            RelicMessages.CHRYSANTHEMUM_DOLL,
            RelicMessages.CHRYSANTHEMUM_DOLL_LORE,
            RelicMessages.CHRYSANTHEMUM_DOLL_BONUS_LORE,
            0.1,
            itemStackOf(Material.LANTERN)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.world.isMoonTime && block.world.moonPhase == MoonPhase.KAGEN
        }
    },
    SHRIKE_FEATHER(
            1121,
            RelicMessages.SHRIKE_FEATHER,
            RelicMessages.SHRIKE_FEATHER_LORE,
            RelicMessages.SHRIKE_FEATHER_BONUS_LORE,
            0.1,
            itemStackOf(Material.LANTERN)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.world.junishiOfTime == Junishi.TORI
        }
    },
    PERSIMMON_SEED(
            1122,
            RelicMessages.PERSIMMON_SEED,
            RelicMessages.PERSIMMON_SEED_LORE,
            RelicMessages.PERSIMMON_SEED_BONUS_LORE,
            0.11,
            itemStackOf(Material.LANTERN)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.type == Material.GRAVEL
        }
    },
    SANMA(
            1123,
            RelicMessages.SANMA,
            RelicMessages.SANMA_LORE,
            RelicMessages.SANMA_BONUS_LORE,
            0.08,
            itemStackOf(Material.LANTERN)
    ) {
        override fun isBonusTarget(block: Block): Boolean {
            return block.biome == Biome.OCEAN || block.biome == Biome.COLD_OCEAN
        }
    },
    // TODO implements REI will relic
//    (
//    11,
//    RelicMessages.,
//    RelicMessages._LORE,
//    RelicMessages._BONUS_LORE,
//    0.,
//    itemStackOf(Material.)
//    ){
//        override fun isBonusTarget(block: Block): Boolean {
//            return
//        }
//    },


    ;

    companion object {
        // 重複確認
        val hasDuplicateId = values().size != values().map { it.id }.toSet().size

        val SPECIAL_RELICS by lazy { values().filter { Will.findByRelic(it) == null }.toSet() }

        fun calcBonusTargetRelics(player: Player, block: Block): Set<Relic> = values().filter {
            player.hasRelic(it)
        }.filter {
            it.isBonusTarget(block)
        }.toSet()
    }

    fun getName(locale: Locale) = localizedName.asSafety(locale)

    fun getLore(locale: Locale) = localizedLore.map { it.asSafety(locale) }

    fun getIcon() = icon.clone()

    fun getDroppedNum(player: Player) = Keys.RELIC_MAP[this]?.let { player.getOrPut(it) } ?: 0L

    fun dropTo(player: Player) {
        player.transform(Keys.RELIC_MAP[this] ?: return) { if (it < maxAmount) it + 1 else maxAmount }
    }

    abstract fun isBonusTarget(block: Block): Boolean

    fun getBonusLore(locale: Locale) = localizedBonusLore.map { it.asSafety(locale) }

    fun calcMultiplier(player: Player): Double {
        return kotlin.math.log(
                getDroppedNum(player)
                        .plus(Defaults.RELIC_MUL_DIFFX), Defaults.RELIC_MUL_BASE
        ).times(multiplier)
    }
}