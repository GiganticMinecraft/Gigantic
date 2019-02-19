package click.seichi.gigantic.relic

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.transform
import click.seichi.gigantic.head.Head
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.messages.RelicMessages
import org.bukkit.Material
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
        val maxAmount: Long = Long.MAX_VALUE,
        private val icon: ItemStack = Head.RUBY_JEWELLERY.toItemStack()
) {
    CHICKEN_KING_CROWN(
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
    WILL_CRYSTAL_JADE(600, RelicMessages.WILL_CRYSTAL_JADE),

    // relic of will spirit
    BEAST_HORN(1000, RelicMessages.BEAST_HORN, RelicMessages.BEAST_HORN_LORE),
    ELIXIR(1001, RelicMessages.ELIXIR, RelicMessages.ELIXIR_LORE),
    SHELL(1002, RelicMessages.SHELL, RelicMessages.SHELL_LORE),
    CACAO_WATERMELON(1003, RelicMessages.CACAO_WATERMELON, RelicMessages.CACAO_WATERMELON_LORE),
    RUSTED_COMPASS(1004, RelicMessages.RUSTED_COMPASS, RelicMessages.RUSTED_COMPASS_LORE),
    USED_COIN(1005, RelicMessages.USED_COIN, RelicMessages.USED_COIN_LORE),
    SUMMER_DAY(1006, RelicMessages.SUMMER_DAY, RelicMessages.SUMMER_DAY_LORE),
    A_PIECE_OF_CHALK(1007, RelicMessages.A_PIECE_OF_CHALK, RelicMessages.A_PIECE_OF_CHALK_LORE),
    OLD_MESSAGE_BOTTLE(1008, RelicMessages.OLD_MESSAGE_BOTTLE, RelicMessages.OLD_MESSAGE_BOTTLE_LORE),
    BROKEN_BOW(1009, RelicMessages.BROKEN_BOW, RelicMessages.BROKEN_BOW_LORE),
    BIRCH_MUSHROOM(1010, RelicMessages.BIRCH_MUSHROOM, RelicMessages.BIRCH_MUSHROOM_LORE),
    ADVENTURER_SOLE(1011, RelicMessages.ADVENTURER_SOLE, RelicMessages.ADVENTURER_SOLE_LORE),
    BEAUTIFUL_WING(1012, RelicMessages.BEAUTIFUL_WING, RelicMessages.BEAUTIFUL_WING_LORE),
    WHITE_APPLE(1013, RelicMessages.WHITE_APPLE, RelicMessages.WHITE_APPLE_LORE),
    TIME_CAPSEL(1014, RelicMessages.TIME_CAPSEL, RelicMessages.TIME_CAPSEL_LORE),
    BEAST_BONE(1015, RelicMessages.BEAST_BONE, RelicMessages.BEAST_BONE_LORE),
    THIN_TOOTH(1016, RelicMessages.THIN_TOOTH, RelicMessages.THIN_TOOTH_LORE),
    BROKEN_IVORY(1017, RelicMessages.BROKEN_IVORY, RelicMessages.BROKEN_IVORY_LORE),
    BOTTLED_LIQUID(1018, RelicMessages.BOTTLED_LIQUID, RelicMessages.BOTTLED_LIQUID_LORE),
    WILL_O_WISP(1019, RelicMessages.WILL_O_WISP, RelicMessages.WILL_O_WISP_LORE),
    SLIME_LEES(1020, RelicMessages.SLIME_LEES, RelicMessages.SLIME_LEES_LORE),
    TUMBLEWEED(1021, RelicMessages.TUMBLEWEED, RelicMessages.TUMBLEWEED_LORE),
    SUPER_MUSHROOM(1022, RelicMessages.SUPER_MUSHROOM, RelicMessages.SUPER_MUSHROOM_LORE),
    PURPLE_CHEESE(1023, RelicMessages.PURPLE_CHEESE, RelicMessages.PURPLE_CHEESE_LORE),
    HORN(1024, RelicMessages.HORN, RelicMessages.HORN_LORE),
    BROKEN_FORCE_FLAG(1025, RelicMessages.BROKEN_FORCE_FLAG, RelicMessages.BROKEN_FORCE_FLAG_LORE),
    SYLPH_LEAFE(1026, RelicMessages.SYLPH_LEAFE, RelicMessages.SYLPH_LEAFE_LORE),
    BROKEN_TRAP(1027, RelicMessages.BROKEN_TRAP, RelicMessages.BROKEN_TRAP_LORE),
    RAINBOW_CLAY(1028, RelicMessages.RAINBOW_CLAY, RelicMessages.RAINBOW_CLAY_LORE),
    SHADE_ARMOR(1029, RelicMessages.SHADE_ARMOR, RelicMessages.SHADE_ARMOR_LORE),
    CLAY_IMAGE(1030, RelicMessages.CLAY_IMAGE, RelicMessages.CLAY_IMAGE_LORE),
    BIG_FUNG(1031, RelicMessages.BIG_FUNG, RelicMessages.BIG_FUNG_LORE),
    FLUX_FOSSIL(1032, RelicMessages.FLUX_FOSSIL, RelicMessages.FLUX_FOSSIL_LORE),
    BITTEN_LEATHER_BOOT(1033, RelicMessages.BITTEN_LEATHER_BOOT, RelicMessages.BITTEN_LEATHER_BOOT_LORE),
    BUDDHIST_STATUE(1034, RelicMessages.BUDDHIST_STATUE, RelicMessages.BUDDHIST_STATUE_LORE),
    BROKEN_LEAD(1035, RelicMessages.BROKEN_LEAD, RelicMessages.BROKEN_LEAD_LORE),
    EGGPLANT(1036, RelicMessages.EGGPLANT, RelicMessages.EGGPLANT_LORE),
    OLD_AXE(1037, RelicMessages.OLD_AXE, RelicMessages.OLD_AXE_LORE),
    CRYSTAL_OF_SNOW(1038, RelicMessages.CRYSTAL_OF_SNOW, RelicMessages.CRYSTAL_OF_SNOW_LORE),
    FROSTED_FISH(1039, RelicMessages.FROSTED_FISH, RelicMessages.FROSTED_FISH_LORE),
    VODKA_BOTTLE(1040, RelicMessages.VODKA_BOTTLE, RelicMessages.VODKA_BOTTLE_LORE),
    SAIL(1041, RelicMessages.SAIL, RelicMessages.SAIL_LORE),
    TREASURE_CASKET(1042, RelicMessages.TREASURE_CASKET, RelicMessages.TREASURE_CASKET_LORE),
    DEEP_SEA_FISH_DIODE(1043, RelicMessages.DEEP_SEA_FISH_DIODE, RelicMessages.DEEP_SEA_FISH_DIODE_LORE),
    SEICHI_MACKEREL(1044, RelicMessages.SEICHI_MACKEREL, RelicMessages.SEICHI_MACKEREL_LORE),
    NOT_MELTTED_ICE(1045, RelicMessages.NOT_MELTTED_ICE, RelicMessages.NOT_MELTTED_ICE_LORE),
    WHITE_FLOWER(1046, RelicMessages.WHITE_FLOWER, RelicMessages.WHITE_FLOWER_LORE),
    FROSTED_ORE(1047, RelicMessages.FROSTED_ORE, RelicMessages.FROSTED_ORE_LORE),
    MAMMMOTH_RAW_MEET(1048, RelicMessages.MAMMMOTH_RAW_MEET, RelicMessages.MAMMMOTH_RAW_MEET_LORE),
    TENT_CLOTH(1049, RelicMessages.TENT_CLOTH, RelicMessages.TENT_CLOTH_LORE),
    CAMP_FIRE_TRACE(1050, RelicMessages.CAMP_FIRE_TRACE, RelicMessages.CAMP_FIRE_TRACE_LORE),
    FROSTED_PINECONE(1051, RelicMessages.FROSTED_PINECONE, RelicMessages.FROSTED_PINECONE_LORE),
    FROSTED_CRAFTBOX(1052, RelicMessages.FROSTED_CRAFTBOX, RelicMessages.FROSTED_CRAFTBOX_LORE),
    MUSH_FISH(1054, RelicMessages.MUSH_FISH, RelicMessages.MUSH_FISH_LORE),
    MYCELIUM_PICKAXE(1055, RelicMessages.MYCELIUM_PICKAXE, RelicMessages.MYCELIUM_PICKAXE_LORE),
    ACID_GEAR(1056, RelicMessages.ACID_GEAR, RelicMessages.ACID_GEAR_LORE),
    DESERT_CRYSTAL(1057, RelicMessages.DESERT_CRYSTAL, RelicMessages.DESERT_CRYSTAL_LORE),
    CAT_SAND(1058, RelicMessages.CAT_SAND, RelicMessages.CAT_SAND_LORE),
    ORICHALCUM(1059, RelicMessages.ORICHALCUM, RelicMessages.ORICHALCUM_LORE),
    BEAUTIFUL_ORE(1060, RelicMessages.BEAUTIFUL_ORE, RelicMessages.BEAUTIFUL_ORE_LORE),
    BANANA_SKIN(1061, RelicMessages.BANANA_SKIN, RelicMessages.BANANA_SKIN_LORE),
    INSECT_HORN(1062, RelicMessages.INSECT_HORN, RelicMessages.INSECT_HORN_LORE),
    ICICLE(1063, RelicMessages.ICICLE, RelicMessages.ICICLE_LORE),
    FROSTED_WHEEL(1064, RelicMessages.FROSTED_WHEEL, RelicMessages.FROSTED_WHEEL_LORE),
    DARK_MATTER(1065, RelicMessages.DARK_MATTER, RelicMessages.DARK_MATTER_LORE),
    STEERING_WHEEL(1066, RelicMessages.STEERING_WHEEL, RelicMessages.STEERING_WHEEL_LORE),
    SOFT_RIME(1067, RelicMessages.SOFT_RIME, RelicMessages.SOFT_RIME_LORE),
    JIZO(1068, RelicMessages.JIZO, RelicMessages.JIZO_LORE),
    CRAMPONS(1069, RelicMessages.CRAMPONS, RelicMessages.CRAMPONS_LORE),
    SLICED_ROPE(1070, RelicMessages.SLICED_ROPE, RelicMessages.SLICED_ROPE_LORE),
    BLACK_CLOTH(1071, RelicMessages.BLACK_CLOTH, RelicMessages.BLACK_CLOTH_LORE),
    LIGHTNING_MOSS(1072, RelicMessages.LIGHTNING_MOSS, RelicMessages.LIGHTNING_MOSS_LORE),
    BROWN_SAP(1073, RelicMessages.BROWN_SAP, RelicMessages.BROWN_SAP_LORE),
    BLOODSTAINED_SWORD(1074, RelicMessages.BLOODSTAINED_SWORD, RelicMessages.BLOODSTAINED_SWORD_LORE),
    WEB_SCRAP(1075, RelicMessages.WEB_SCRAP, RelicMessages.WEB_SCRAP_LORE),
    DOWN_TREE(1076, RelicMessages.DOWN_TREE, RelicMessages.DOWN_TREE_LORE),
    CARNIVORE_BONE(1077, RelicMessages.CARNIVORE_BONE, RelicMessages.CARNIVORE_BONE_LORE),
    IRON_ARMOR(1078, RelicMessages.IRON_ARMOR, RelicMessages.IRON_ARMOR_LORE),
    INDIGO(1079, RelicMessages.INDIGO, RelicMessages.INDIGO_LORE),
    DIAMOND_STONE(1080, RelicMessages.DIAMOND_STONE, RelicMessages.DIAMOND_STONE_LORE),
    ULURU_SCRAP(1081, RelicMessages.ULURU_SCRAP, RelicMessages.ULURU_SCRAP_LORE),
    RED_DUST(1082, RelicMessages.RED_DUST, RelicMessages.RED_DUST_LORE),
    BLUE_DUST(1083, RelicMessages.BLUE_DUST, RelicMessages.BLUE_DUST_LORE),
    FRIED_POTATO(1084, RelicMessages.FRIED_POTATO, RelicMessages.FRIED_POTATO_LORE),
    SPHINX(1085, RelicMessages.SPHINX, RelicMessages.SPHINX_LORE),
    PRICKLE(1086, RelicMessages.PRICKLE, RelicMessages.PRICKLE_LORE),
    WOOD_SLAB(1087, RelicMessages.WOOD_SLAB, RelicMessages.WOOD_SLAB_LORE),
    BROKEN_WATERMELON(1088, RelicMessages.BROKEN_WATERMELON, RelicMessages.BROKEN_WATERMELON_LORE),
    RLYEH_TEXT(1089, RelicMessages.RLYEH_TEXT, RelicMessages.RLYEH_TEXT_LORE),
    CUTE_WATERING_POT(1090, RelicMessages.CUTE_WATERING_POT, RelicMessages.CUTE_WATERING_POT_LORE),
    WING(1091, RelicMessages.WING, RelicMessages.WING_LORE),
    NIDUS_AVIS(1092, RelicMessages.NIDUS_AVIS, RelicMessages.NIDUS_AVIS_LORE),
    ALSTROMERIA_SEED(1093, RelicMessages.ALSTROMERIA_SEED, RelicMessages.ALSTROMERIA_SEED_LORE),
    NIGHTINGALE_FEATHER(1094, RelicMessages.NIGHTINGALE_FEATHER, RelicMessages.NIGHTINGALE_FEATHER_LORE),
    OBOROZUKI_SWORD(1095, RelicMessages.OBOROZUKI_SWORD, RelicMessages.OBOROZUKI_SWORD_LORE),
    SAKURA_RACE_CAKE(1096, RelicMessages.SAKURA_RACE_CAKE, RelicMessages.SAKURA_RACE_CAKE_LORE),
    CERTIFICATE(1097, RelicMessages.CERTIFICATE, RelicMessages.CERTIFICATE_LORE),
    SCHOOL_BAG(1098, RelicMessages.SCHOOL_BAG, RelicMessages.SCHOOL_BAG_LORE),
    KATSUO_SASHIMI(1099, RelicMessages.KATSUO_SASHIMI, RelicMessages.KATSUO_SASHIMI_LORE),
    BOTAMOCHI(1100, RelicMessages.BOTAMOCHI, RelicMessages.BOTAMOCHI_LORE),
    PEACH_CORE(1101, RelicMessages.PEACH_CORE, RelicMessages.PEACH_CORE_LORE),
    ;

    fun getName(locale: Locale) = localizedName.asSafety(locale)

    fun getLore(locale: Locale) = localizedLore.map { it.asSafety(locale) }

    fun getIcon() = icon.clone()

    fun getDroppedNum(player: Player) = Keys.RELIC_MAP[this]?.let { player.getOrPut(it) } ?: 0L

    fun dropTo(player: Player) {
        player.transform(Keys.RELIC_MAP[this] ?: return) { if (it < maxAmount) it + 1 else maxAmount }
    }

}