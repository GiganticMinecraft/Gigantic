package click.seichi.gigantic.will

import click.seichi.gigantic.GiganticEvent
import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.*
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.messages.WillMessages
import click.seichi.gigantic.relic.Relic
import org.bukkit.ChatColor
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import java.util.*

/**
 * @author unicroak
 * @author tar0ss
 */
enum class Will(
        // 変更不可
        val id: Int,
        val color: Color,
        val chatColor: ChatColor,
        val material: Material,
        val grade: WillGrade,
        private val localizedName: LocalizedText,
        // 表示の順番
        val displayPriority: Int,
        vararg relics: Relic
) {

    /**
     * スポーン条件は以下のいずれかによって分けること
     * * バイオーム
     * * 気温
     * * 高度
     *
     */
    AQUA(
            1,
            Color.fromRGB(0, 0, 128),
            ChatColor.BLUE,
            Material.BLUE_GLAZED_TERRACOTTA,
            WillGrade.BASIC,
            WillMessages.AQUA,
            2,
            Relic.SHELL, Relic.SAIL, Relic.DEEP_SEA_FISH_DIODE, Relic.SEICHI_MACKEREL, Relic.MUSH_FISH,
            Relic.STEERING_WHEEL, Relic.WOOD_SLAB, Relic.BROKEN_WATERMELON, Relic.CUTE_WATERING_POT
    ) {
        // 高度が30以上62以下であり，かつ海，川等のバイオームであること
        override fun canSpawn(player: Player, block: Block): Boolean {
            val biome = block.biome
            if (!biome.isOcean && !biome.isRiver) return false
            return block.y in 30..62
        }
    },
    IGNIS(
            2,
            Color.fromRGB(139, 0, 0),
            ChatColor.RED,
            Material.RED_GLAZED_TERRACOTTA,
            WillGrade.BASIC,
            WillMessages.IGNIS,
            3,
            Relic.BEAST_BONE, Relic.THIN_TOOTH, Relic.BROKEN_IVORY, Relic.WILL_O_WISP, Relic.BIG_FUNG,
            Relic.CAMP_FIRE_TRACE, Relic.DESERT_CRYSTAL, Relic.CARNIVORE_BONE, Relic.FRIED_POTATO
    ) {
        // 高度が29以下であること
        override fun canSpawn(player: Player, block: Block): Boolean {
            return block.y <= 29
        }
    },
    AER(
            3,
            Color.fromRGB(255, 255, 204),
            ChatColor.WHITE,
            Material.WHITE_GLAZED_TERRACOTTA,
            WillGrade.BASIC,
            WillMessages.AER,
            5,
            Relic.BROKEN_BOW, Relic.TIME_CAPSEL, Relic.BROKEN_FORCE_FLAG, Relic.BITTEN_LEATHER_BOOT, Relic.BROKEN_LEAD,
            Relic.OLD_AXE, Relic.VODKA_BOTTLE, Relic.ACID_GEAR, Relic.SLICED_ROPE, Relic.WEB_SCRAP
    ) {
        // 高度が85以上であること
        override fun canSpawn(player: Player, block: Block): Boolean {
            return block.y >= 85
        }
    },
    TERRA(
            4,
            Color.fromRGB(124, 83, 53),
            ChatColor.GOLD,
            Material.BROWN_GLAZED_TERRACOTTA,
            WillGrade.BASIC,
            WillMessages.TERRA,
            1,
            Relic.USED_COIN, Relic.ADVENTURER_SOLE, Relic.SUPER_MUSHROOM, Relic.RAINBOW_CLAY, Relic.CLAY_IMAGE,
            Relic.MAMMMOTH_RAW_MEET, Relic.CAT_SAND, Relic.ULURU_SCRAP, Relic.SPHINX
    ) {
        // 高度が30以上62以下であり，かつ海，川等のバイオームではないこと
        override fun canSpawn(player: Player, block: Block): Boolean {
            val biome = block.biome
            if (biome.isOcean || biome.isRiver) return false
            return block.y in 30..62
        }
    },
    NATURA(
            5,
            Color.fromRGB(0, 255, 0),
            ChatColor.DARK_GREEN,
            Material.LIME_GLAZED_TERRACOTTA,
            WillGrade.BASIC,
            WillMessages.NATURA,
            4,
            Relic.CACAO_WATERMELON, Relic.SUMMER_DAY, Relic.BIRCH_MUSHROOM, Relic.EGGPLANT, Relic.WHITE_FLOWER,
            Relic.BANANA_SKIN, Relic.INSECT_HORN, Relic.BROWN_SAP, Relic.DOWN_TREE
    ) {
        // 高度が63以上84以下であること
        override fun canSpawn(player: Player, block: Block): Boolean {
            return block.y in 63..84
        }
    },
    GLACIES(
            6,
            Color.fromRGB(127, 255, 255),
            ChatColor.AQUA,
            Material.LIGHT_BLUE_GLAZED_TERRACOTTA,
            WillGrade.ADVANCED,
            WillMessages.GLACIES,
            6,
            Relic.FROSTED_PINECONE, Relic.CRYSTAL_OF_SNOW, Relic.FROSTED_FISH, Relic.NOT_MELTTED_ICE, Relic.FROSTED_CRAFTBOX,
            Relic.ICICLE, Relic.FROSTED_WHEEL, Relic.SOFT_RIME, Relic.CRAMPONS
    ) {
        // 温度が0以下であること
        override fun canSpawn(player: Player, block: Block): Boolean {
            return block.temperature <= 0
        }
    },
    LUX(
            7,
            Color.fromRGB(255, 234, 0),
            ChatColor.YELLOW,
            Material.YELLOW_GLAZED_TERRACOTTA,
            WillGrade.ADVANCED,
            WillMessages.LUX,
            9,
            Relic.ELIXIR, Relic.OLD_MESSAGE_BOTTLE, Relic.WHITE_APPLE, Relic.BUDDHIST_STATUE, Relic.TREASURE_CASKET,
            Relic.JIZO, Relic.LIGHTNING_MOSS, Relic.RED_DUST, Relic.BLUE_DUST
    ) {
        // 温度が1.2以上であること
        override fun canSpawn(player: Player, block: Block): Boolean {
            return block.temperature >= 1.2
        }
    },
    SOLUM(
            8,
            Color.fromRGB(80, 73, 70),
            ChatColor.DARK_GRAY,
            Material.GRAY_GLAZED_TERRACOTTA,
            WillGrade.ADVANCED,
            WillMessages.SOLUM,
            7,
            Relic.A_PIECE_OF_CHALK, Relic.BROKEN_TRAP, Relic.FLUX_FOSSIL, Relic.FROSTED_ORE, Relic.MYCELIUM_PICKAXE,
            Relic.BEAUTIFUL_ORE, Relic.IRON_ARMOR, Relic.INDIGO, Relic.DIAMOND_STONE
    ) {
        // 山岳バイオームであること
        override fun canSpawn(player: Player, block: Block): Boolean {
            val biome = block.biome
            return biome.isMountain
        }
    },
    UMBRA(
            9,
            Color.fromRGB(148, 0, 211),
            ChatColor.DARK_PURPLE,
            Material.PURPLE_GLAZED_TERRACOTTA,
            WillGrade.ADVANCED,
            WillMessages.UMBRA,
            10,
            Relic.BEAST_HORN, Relic.BOTTLED_LIQUID, Relic.SLIME_LEES, Relic.PURPLE_CHEESE, Relic.SHADE_ARMOR,
            Relic.ORICHALCUM, Relic.DARK_MATTER, Relic.BLACK_CLOTH, Relic.BLOODSTAINED_SWORD, Relic.RLYEH_TEXT
    ) {
        // 森林バイオームであること
        override fun canSpawn(player: Player, block: Block): Boolean {
            val biome = block.biome
            return biome.isForest
        }
    },
    VENTUS(
            10,
            Color.fromRGB(220, 221, 221),
            ChatColor.GRAY,
            Material.LIGHT_GRAY_GLAZED_TERRACOTTA,
            WillGrade.ADVANCED,
            WillMessages.VENTUS,
            8,
            Relic.RUSTED_COMPASS, Relic.BEAUTIFUL_WING, Relic.TUMBLEWEED, Relic.HORN, Relic.SYLPH_LEAFE,
            Relic.TENT_CLOTH, Relic.PRICKLE, Relic.WING, Relic.NIDUS_AVIS
    ) {
        // 丘陵
        override fun canSpawn(player: Player, block: Block): Boolean {
            val biome = block.biome
            return biome.isHill
        }
    },
    SAKURA(
            11,
            Color.fromRGB(254, 244, 244),
            ChatColor.LIGHT_PURPLE,
            Material.PINK_GLAZED_TERRACOTTA,
            WillGrade.SPECIAL,
            WillMessages.SAKURA,
            11,
            Relic.ALSTROMERIA_SEED, Relic.NIGHTINGALE_FEATHER, Relic.OBOROZUKI_SWORD, Relic.SAKURA_RACE_CAKE, Relic.CERTIFICATE,
            Relic.SCHOOL_BAG, Relic.KATSUO_SASHIMI, Relic.BOTAMOCHI, Relic.PEACH_CORE, Relic.BOILED_CANOLA_FLOWER
    ) {
        // 期間限定
        override fun canSpawn(player: Player, block: Block): Boolean {
            return GiganticEvent.SAKURA.isActive()
        }
    },
    MIO(
            12,
            Color.fromRGB(70, 191, 255),
            ChatColor.DARK_AQUA,
            Material.CYAN_GLAZED_TERRACOTTA,
            WillGrade.SPECIAL,
            WillMessages.MIO,
            12,
            Relic.MORNING_GLORY_LEAVES, Relic.FUJI_ROUND_FUN, Relic.HITSUMABUSHI, Relic.MOSQUITO_COIL, Relic.GOLD_FISH,
            Relic.WIND_BELL, Relic.FIREFLY, Relic.STRAW_HAT, Relic.TERU_TERU, Relic.YUKATA
    ) {
        // 期間限定
        override fun canSpawn(player: Player, block: Block): Boolean {
            return GiganticEvent.MIO.isActive()
        }
    },
    KAEDE(
            13,
            Color.fromRGB(200, 153, 50),
            ChatColor.GOLD,
            Material.ORANGE_GLAZED_TERRACOTTA,
            WillGrade.SPECIAL,
            WillMessages.KAEDE,
            13,
            Relic.MARBLING_MEAT, Relic.SHIRANUI, Relic.MOMIJI, Relic.TOUROU, Relic.SCARECROW,
            Relic.TSUKIMI_UDON, Relic.CHRYSANTHEMUM_DOLL, Relic.SHRIKE_FEATHER, Relic.PERSIMMON_SEED, Relic.SANMA
    ) {
        // 期間限定
        override fun canSpawn(player: Player, block: Block): Boolean {
            return GiganticEvent.KAEDE.isActive()
        }
    },
    ;

    val relicSet = relics.toSet()

    fun has(relic: Relic) = relicSet.contains(relic)

    abstract fun canSpawn(player: Player, block: Block): Boolean

    fun addEthel(player: Player, amount: Long) = player.transform(Keys.ETHEL_MAP.getValue(this)) { it + amount }

    override fun toString(): String = name.toLowerCase()

    fun getName(locale: Locale) = localizedName.asSafety(locale)

    companion object {
        // 重複確認
        val hasDuplicateId = values().size != values().map { it.id }.toSet().size

        private val idMap = values().map { it.id to it }.toMap()

        private val relicMap = values().flatMap { will ->
            will.relicSet.map { relic ->
                relic to will
            }
        }.toMap()

        fun findById(id: Int) = idMap[id]

        fun findByRelic(relic: Relic) = relicMap[relic]
    }
}