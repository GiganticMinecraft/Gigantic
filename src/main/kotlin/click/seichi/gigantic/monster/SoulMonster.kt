package click.seichi.gigantic.monster

import click.seichi.gigantic.cache.key.Keys
import click.seichi.gigantic.extension.getOrPut
import click.seichi.gigantic.extension.transform
import click.seichi.gigantic.head.Head
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.messages.MonsterMessages
import click.seichi.gigantic.monster.ai.SoulMonsterAI
import click.seichi.gigantic.monster.parameter.SoulMonsterParameter
import click.seichi.gigantic.monster.parameter.SoulMonsterParameters
import click.seichi.gigantic.relic.Relic
import click.seichi.gigantic.will.Will
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

/**
 * @author tar0ss
 */
enum class SoulMonster(
        val id: Int,
        private val icon: Head?,
        private val localizedName: LocalizedText,
        private val localizedLore: List<LocalizedText>?,
        val color: Color,
        val parameter: SoulMonsterParameter,
        private val aiClass: KClass<SoulMonsterAI>,
        vararg dropRelic: DropRelic
) {
    PIG(
            0,
            Head.PIG,
            MonsterMessages.PIG,
            null,
            Color.fromRGB(255, 182, 193),
            SoulMonsterParameters.PIG,
            SoulMonsterAI::class
    ),
    PIG_WARRIOR(
            1,
            Head.PIG_WARRIOR,
            MonsterMessages.PIG_WARRIOR,
            null,
            Color.fromRGB(255, 182, 193),
            SoulMonsterParameters.PIG_WARRIOR,
            SoulMonsterAI::class
    ),
    MR_PIG(
            2,
            Head.MR_PIG,
            MonsterMessages.MR_PIG,
            null,
            Color.fromRGB(255, 182, 193),
            SoulMonsterParameters.MR_PIG,
            SoulMonsterAI::class,
            DropRelic(Relic.PIGS_FEATHER)
    ),


    BLAZE(
            10,
            Head.BLAZE,
            MonsterMessages.BLAZE,
            null,
            Color.fromRGB(251, 208, 67),
            SoulMonsterParameters.BLAZE,
            SoulMonsterAI::class
    ),
    BLAZE_WARRIOR(
            11,
            Head.BLAZE_WARRIOR,
            MonsterMessages.BLAZE_WARRIOR,
            null,
            Color.fromRGB(251, 208, 67),
            SoulMonsterParameters.BLAZE_WARRIOR,
            SoulMonsterAI::class
    ),
    BLUE_BLAZE(
            12,
            Head.BLUE_BLAZE,
            MonsterMessages.BLUE_BLAZE,
            null,
            Color.fromRGB(82, 154, 222),
            SoulMonsterParameters.BLUE_BLAZE,
            SoulMonsterAI::class,
            DropRelic(Relic.BLUE_BLAZE_POWDER)
    ),
    CHICKEN(
            13,
            Head.CHICKEN,
            MonsterMessages.CHICKEN,
            null,
            Color.fromRGB(230, 227, 222),
            SoulMonsterParameters.CHICKEN,
            SoulMonsterAI::class
    ),
    CHICKEN_KING(
            14,
            Head.CHICKEN_KING,
            MonsterMessages.CHICKEN_KING,
            null,
            Color.fromRGB(230, 227, 222),
            SoulMonsterParameters.CHICKEN_KING,
            SoulMonsterAI::class,
            DropRelic(Relic.CHICKEN_KING_CROWN)
    ),
    WITHER_SKELETON(
            15,
            Head.WITHER_SKELETON,
            MonsterMessages.WITHER_SKELETON,
            null,
            Color.fromRGB(37, 37, 39),
            SoulMonsterParameters.WITHER_SKELETON,
            SoulMonsterAI::class,
            DropRelic(Relic.WITHER_SKELETON_SKULL)
    ),
    WITHER(
            16,
            Head.WITHER,
            MonsterMessages.WITHER,
            null,
            Color.fromRGB(19, 19, 21),
            SoulMonsterParameters.WITHER,
            SoulMonsterAI::class,
            DropRelic(Relic.MANA_STONE)
    ),
    VILLAGER(
            17,
            Head.VILLAGER,
            MonsterMessages.VILLAGER,
            null,
            Color.fromRGB(189, 137, 113),
            SoulMonsterParameters.VILLAGER,
            SoulMonsterAI::class
    ),
    ZOMBIE_VILLAGER(
            18,
            Head.ZOMBIE_VILLAGER,
            MonsterMessages.ZOMBIE_VILLAGER,
            null,
            Color.fromRGB(71, 99, 43),
            SoulMonsterParameters.ZOMBIE_VILLAGER,
            SoulMonsterAI::class,
            DropRelic(Relic.ROTTEN_FLESH)
    ),
    TURTLE(
            19,
            Head.TURTLE,
            MonsterMessages.TURTLE,
            null,
            Color.fromRGB(87, 219, 89),
            SoulMonsterParameters.TURTLE,
            SoulMonsterAI::class
    ),
    TURTLE_SOLDIER(
            20,
            Head.TURTLE_SOLDIER,
            MonsterMessages.TURTLE_SOLDIER,
            null,
            Color.fromRGB(87, 219, 89),
            SoulMonsterParameters.TURTLE_SOLDIER,
            SoulMonsterAI::class
    ),
    TURTLE_KING(
            21,
            Head.TURTLE_KING,
            MonsterMessages.TURTLE_KING,
            null,
            Color.fromRGB(87, 219, 89),
            SoulMonsterParameters.TURTLE_KING,
            SoulMonsterAI::class,
            DropRelic(Relic.TURTLE_KING_CROWN)
    ),
    SPIDER(
            22,
            Head.SPIDER,
            MonsterMessages.SPIDER,
            null,
            Color.fromRGB(81, 72, 62),
            SoulMonsterParameters.SPIDER,
            SoulMonsterAI::class
    ),
    CAVE_SPIDER(
            23,
            Head.CAVE_SPIDER,
            MonsterMessages.CAVE_SPIDER,
            null,
            Color.fromRGB(34, 65, 67),
            SoulMonsterParameters.CAVE_SPIDER,
            SoulMonsterAI::class
    ),
    SPIDER_KING(
            24,
            Head.SPIDER_KING,
            MonsterMessages.SPIDER_KING,
            null,
            Color.fromRGB(81, 72, 62),
            SoulMonsterParameters.SPIDER_KING,
            SoulMonsterAI::class,
            DropRelic(Relic.SPIDER_KING_CROWN)
    ),
    ZOMBIE(
            25,
            Head.ZOMBIE,
            MonsterMessages.ZOMBIE,
            null,
            Color.fromRGB(50, 88, 32),
            SoulMonsterParameters.ZOMBIE,
            SoulMonsterAI::class
    ),
    ZOMBIE_SOLDIER(
            26,
            Head.ZOMBIE_SOLDIER,
            MonsterMessages.ZOMBIE_SOLDIER,
            null,
            Color.fromRGB(50, 88, 32),
            SoulMonsterParameters.ZOMBIE_SOLDIER,
            SoulMonsterAI::class
    ),
    ZOMBIE_KING(
            27,
            Head.ZOMBIE_KING,
            MonsterMessages.ZOMBIE_KING,
            null,
            Color.fromRGB(50, 88, 32),
            SoulMonsterParameters.ZOMBIE_KING,
            SoulMonsterAI::class,
            DropRelic(Relic.ZOMBIE_KING_CROWN)
    ),
    SKELETON(
            28,
            Head.SKELETON,
            MonsterMessages.SKELETON,
            null,
            Color.fromRGB(151, 151, 151),
            SoulMonsterParameters.SKELETON,
            SoulMonsterAI::class
    ),
    SKELETON_SOLDIER(
            29,
            Head.SKELETON_SOLDIER,
            MonsterMessages.SKELETON_SOLDIER,
            null,
            Color.fromRGB(151, 151, 151),
            SoulMonsterParameters.SKELETON_SOLDIER,
            SoulMonsterAI::class
    ),
    SKELETON_KING(
            30,
            Head.SKELETON_KING,
            MonsterMessages.SKELETON_KING,
            null,
            Color.fromRGB(151, 151, 151),
            SoulMonsterParameters.SKELETON_KING,
            SoulMonsterAI::class,
            DropRelic(Relic.SKELETON_KING_CROWN)
    ),
    ORC(
            31,
            Head.ORC,
            MonsterMessages.ORC,
            null,
            Color.fromRGB(37, 51, 16),
            SoulMonsterParameters.ORC,
            SoulMonsterAI::class
    ),
    ORC_SOLDIER(
            32,
            Head.ORC_SOLDIER,
            MonsterMessages.ORC_SOLDIER,
            null,
            Color.fromRGB(37, 51, 16),
            SoulMonsterParameters.ORC_SOLDIER,
            SoulMonsterAI::class
    ),
    ORC_KING(
            33,
            Head.ORC_KING,
            MonsterMessages.ORC_KING,
            null,
            Color.fromRGB(37, 51, 16),
            SoulMonsterParameters.ORC_KING,
            SoulMonsterAI::class,
            DropRelic(Relic.ORC_KING_CROWN)
    ),
    GHOST(
            34,
            Head.GHOST,
            MonsterMessages.GHOST,
            null,
            Color.fromRGB(13, 10, 17),
            SoulMonsterParameters.GHOST,
            SoulMonsterAI::class
    ),
    WHITE_GHOST(
            35,
            Head.WHITE_GHOST,
            MonsterMessages.WHITE_GHOST,
            null,
            Color.fromRGB(252, 247, 239),
            SoulMonsterParameters.WHITE_GHOST,
            SoulMonsterAI::class
    ),
    GHOST_KING(
            36,
            Head.GHOST_KING,
            MonsterMessages.GHOST_KING,
            null,
            Color.fromRGB(13, 10, 17),
            SoulMonsterParameters.GHOST_KING,
            SoulMonsterAI::class,
            DropRelic(Relic.GHOST_KING_CROWN)
    ),
    GRAY_PARROT(
            37,
            Head.GRAY_PARROT,
            MonsterMessages.GRAY_PARROT,
            null,
            Color.fromRGB(147, 149, 148),
            SoulMonsterParameters.GRAY_PARROT,
            SoulMonsterAI::class
    ),
    RED_PARROT(
            38,
            Head.RED_PARROT,
            MonsterMessages.RED_PARROT,
            null,
            Color.fromRGB(233, 2, 2),
            SoulMonsterParameters.RED_PARROT,
            SoulMonsterAI::class
    ),
    ELDER_PARROT(
            39,
            Head.ELDER_PARROT,
            MonsterMessages.ELDER_PARROT,
            null,
            Color.fromRGB(165, 162, 145),
            SoulMonsterParameters.ELDER_PARROT,
            SoulMonsterAI::class,
            DropRelic(Relic.CHIP_OF_WOOD)
    ),
    SLIME(
            40,
            Head.SLIME,
            MonsterMessages.SLIME,
            null,
            Color.fromRGB(64, 119, 64),
            SoulMonsterParameters.SLIME,
            SoulMonsterAI::class
    ),
    RAINBOW_SLIME(
            41,
            Head.RAINBOW_SLIME,
            MonsterMessages.RAINBOW_SLIME,
            null,
            Color.fromRGB(242, 12, 0),
            SoulMonsterParameters.RAINBOW_SLIME,
            SoulMonsterAI::class
    ),
    MOISTENED_SLIME(
            42,
            Head.MOISTENED_SLIME,
            MonsterMessages.MOISTENED_SLIME,
            null,
            Color.fromRGB(165, 196, 117),
            SoulMonsterParameters.MOISTENED_SLIME,
            SoulMonsterAI::class,
            DropRelic(Relic.MOISTENED_SLIME_BOLL)
    ),

    LADON(
            100,
            Head.LADON,
            MonsterMessages.LADON,
            null,
            Color.fromRGB(255, 215, 0),
            SoulMonsterParameters.LADON,
            SoulMonsterAI::class,
            DropRelic(Relic.SPELL_BOOK_EXPLOSION),
            DropRelic(Relic.GOLDEN_APPLE)
    ),
    UNDINE(
            200,
            Head.UNDINE,
            MonsterMessages.UNDINE,
            null,
            Will.IGNIS.color,
            SoulMonsterParameters.UNDINE,
            SoulMonsterAI::class,
            DropRelic(Relic.WILL_CRYSTAL_SAPPHIRE)
    ),

    SALAMANDRA(
            300,
            Head.SALAMANDRA,
            MonsterMessages.SALAMANDRA,
            null,
            Will.AQUA.color,
            SoulMonsterParameters.SALAMANDRA,
            SoulMonsterAI::class,
            DropRelic(Relic.WILL_CRYSTAL_RUBY)
    ),

    SYLPHID(
            400,
            Head.SYLPHID,
            MonsterMessages.SYLPHID,
            null,
            Will.AER.color,
            SoulMonsterParameters.SYLPHID,
            SoulMonsterAI::class,
            DropRelic(Relic.WILL_CRYSTAL_FLUORITE)
    ),

    NOMOS(
            500,
            Head.NOMOS,
            MonsterMessages.NOMOS,
            null,
            Will.TERRA.color,
            SoulMonsterParameters.NOMOS,
            SoulMonsterAI::class,
            DropRelic(Relic.WILL_CRYSTAL_ANDALUSITE)
    ),

    LOA(
            600,
            Head.LOA,
            MonsterMessages.LOA,
            null,
            Will.NATURA.color,
            SoulMonsterParameters.LOA,
            SoulMonsterAI::class,
            DropRelic(Relic.WILL_CRYSTAL_JADE)
    )

    ;

    fun getIcon() = icon?.toItemStack() ?: ItemStack(Material.ZOMBIE_HEAD)

    fun getName(locale: Locale) = localizedName.asSafety(locale)

    fun getLore(locale: Locale) = localizedLore?.map { it.asSafety(locale) }

    val dropRelicSet = dropRelic.toSet()

    fun createAIInstance() = aiClass.createInstance()

    fun isDefeatedBy(player: Player) = Keys.SOUL_MONSTER[this]?.let { player.getOrPut(it) > 0 } ?: false

    fun defeatedBy(player: Player) {
        player.transform(Keys.SOUL_MONSTER[this] ?: return) { it + 1 }
    }

    data class DropRelic(
            val relic: Relic,
            val probability: Double = 1.0
    )
}