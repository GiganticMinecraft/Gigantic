package click.seichi.gigantic.monster

import click.seichi.gigantic.cache.key.Keys
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

    fun defeatedBy(player: Player) {
        player.transform(Keys.SOUL_MONSTER[this] ?: return) { it + 1 }
    }

    data class DropRelic(
            val relic: Relic,
            val probability: Double = 1.0
    )
}