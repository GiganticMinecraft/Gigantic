package click.seichi.gigantic.relic

import click.seichi.gigantic.extension.wrappedLocale
import click.seichi.gigantic.head.Head
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.messages.RelicMessages
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
enum class Relic(
        val id: Int,
        private val localizedName: LocalizedText,
        private val localizedLore: List<LocalizedText>?,
        val maxAmount: Int = Int.MAX_VALUE,
        private val icon: ItemStack = Head.RUBY_JEWELLERY.toItemStack()
) {
    PIGS_FEATHER(0, RelicMessages.PIGS_FEATHER, null, icon = ItemStack(Material.FEATHER)),
    SPELL_BOOK_EXPLOSION(100, RelicMessages.SPELL_BOOK_EXPLOSION, null, 1),
    GOLDEN_APPLE(150, RelicMessages.GOLDEN_APPLE, null),
    WILL_CRYSTAL_SAPPHIRE(200, RelicMessages.WILL_CRYSTAL_SAPPHIRE, null),
    WILL_CRYSTAL_RUBY(300, RelicMessages.WILL_CRYSTAL_RUBY, null),
    WILL_CRYSTAL_FLUORITE(400, RelicMessages.WILL_CRYSTAL_FLUORITE, null),
    WILL_CRYSTAL_ANDALUSITE(500, RelicMessages.WILL_CRYSTAL_ANDALUSITE, null),
    WILL_CRYSTAL_JADE(600, RelicMessages.WILL_CRYSTAL_JADE, null),
    ;

    companion object {
        private val idMap = values().map { it.id to it }.toMap()
        fun findById(id: Int) = idMap[id]
    }

    fun getName(player: Player) = localizedName.asSafety(player.wrappedLocale)

    fun getLore(player: Player) = localizedLore?.map { it.asSafety(player.wrappedLocale) }

    fun getIcon(player: Player) = icon.clone()

}