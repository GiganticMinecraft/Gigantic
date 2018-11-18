package click.seichi.gigantic.player.spell

import click.seichi.gigantic.acheivement.Achievement
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.messages.SpellMessages
import click.seichi.gigantic.player.Invokable
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*

/**
 * @author tar0ss
 */
enum class Spell(
        val id: Int,
        val slot: Int,
        private val icon: ItemStack,
        private val localizedName: LocalizedText,
        private val localizedLore: List<LocalizedText>?,
        private val achievement: Achievement?,
        private val invoker: Invokable
) {
    STELLA_CLAIR(
            0,
            0,
            ItemStack(Material.LAPIS_LAZULI),
            SpellMessages.STELLA_CLAIR,
            SpellMessages.STELLA_CLAIR_LORE,
            Achievement.SPELL_STELLA_CLAIR,
            Spells.STELLA_CLAIR
    ),
    ;

    fun tryCast(player: Player) = invoker.tryInvoke(player)

    fun isGranted(player: Player) = achievement?.isGranted(player) ?: true

    // 解禁条件を表示するためにplayerを引数に
    fun getIcon(player: Player) = icon.clone()

    fun getName(locale: Locale) = localizedName.asSafety(locale)

    fun getLore(locale: Locale) = localizedLore?.map { it.asSafety(locale) }

}