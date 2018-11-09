package click.seichi.gigantic.monster

import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.head.Head
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.messages.MonsterMessages
import click.seichi.gigantic.relic.Relic
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
enum class SoulMonster(
        val id: Int,
        private val icon: Head?,
        private val localizedName: LocalizedText,
        private val localizedLore: List<LocalizedText>?,
        vararg dropRelic: DropRelic
) {
    PIG(0, Head.PIG, MonsterMessages.PIG, null),
    PIG_WARRIOR(1, Head.PIG_WARRIOR, MonsterMessages.PIG_WARRIOR, null),
    MR_PIG(2, Head.MR_PIG, MonsterMessages.MR_PIG, null, DropRelic(Relic.PIGS_FEATHER)),
    LADON(100, Head.LADON, MonsterMessages.LADON, null, DropRelic(Relic.SPELL_BOOK_EXPLOSION), DropRelic(Relic.GOLDEN_APPLE)),
    UNDINE(200, Head.UNDINE, MonsterMessages.UNDINE, null, DropRelic(Relic.WILL_CRYSTAL_SAPPHIRE)),
    SALAMANDRA(300, Head.SALAMANDRA, MonsterMessages.SALAMANDRA, null, DropRelic(Relic.WILL_CRYSTAL_RUBY)),
    SYLPHID(400, Head.SYLPHID, MonsterMessages.SYLPHID, null, DropRelic(Relic.WILL_CRYSTAL_FLUORITE)),
    NOMOS(500, Head.NOMOS, MonsterMessages.NOMOS, null, DropRelic(Relic.WILL_CRYSTAL_ANDALUSITE)),
    LOA(600, Head.LOA, MonsterMessages.LOA, null, DropRelic(Relic.WILL_CRYSTAL_JADE))
    ;

    fun getIcon(player: Player) = icon?.toItemStack() ?: ItemStack(Material.ZOMBIE_HEAD)

    fun getName(player: Player) = localizedName.asSafety(player.wrappedLocale)

    fun getLore(player: Player) = localizedLore?.map { it.asSafety(player.wrappedLocale) }

    val dropRelicSet = dropRelic.toSet()

    data class DropRelic(
            val relic: Relic,
            val probability: Double = 1.0
    )
}