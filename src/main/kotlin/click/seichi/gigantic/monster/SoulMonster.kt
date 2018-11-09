package click.seichi.gigantic.monster

import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.head.Head
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.messages.MonsterMessages
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
        private val localizedLore: List<LocalizedText>?
) {
    LADON(100, Head.LADON, MonsterMessages.LADON, null),
    UNDINE(200, Head.UNDINE, MonsterMessages.UNDINE, null),
    SALAMANDRA(300, Head.SALAMANDRA, MonsterMessages.SALAMANDRA, null),
    SYLPHID(400, Head.SYLPHID, MonsterMessages.SYLPHID, null),
    NOMOS(500, Head.NOMOS, MonsterMessages.NOMOS, null),
    LOA(600, Head.LOA, MonsterMessages.LOA, null)
    ;

    fun getIcon(player: Player) = icon?.toItemStack() ?: ItemStack(Material.ZOMBIE_HEAD)

    fun getName(player: Player) = localizedName.asSafety(player.wrappedLocale)

    fun getLore(player: Player) = localizedLore?.map { it.asSafety(player.wrappedLocale) }

}