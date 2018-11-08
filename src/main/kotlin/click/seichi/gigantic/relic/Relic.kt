package click.seichi.gigantic.relic

import click.seichi.gigantic.head.Head
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.messages.RelicMessages
import org.bukkit.inventory.ItemStack

/**
 * @author tar0ss
 */
enum class Relic(
        val id: Int,
        val localizedName: LocalizedText,
        val localizedLore: List<LocalizedText>?,
        val maxAmount: Int = Int.MAX_VALUE,
        val icon: ItemStack = Head.RUBY_JEWELLERY.toItemStack()
) {
    SPELL_BOOK_EXPLOSION(100, RelicMessages.SPELL_BOOK_EXPLOSION, null, 1),
    GOLDEN_APPLE(150, RelicMessages.GOLDEN_APPLE, null),
    SPELL_BOOK_AQUA_LINEA(200, RelicMessages.SPELL_BOOK_AQUA_LINEA, null, 1),
    WILL_CRYSTAL_SAPPHIRE(250, RelicMessages.WILL_CRYSTAL_SAPPHIRE, null),
    SPELL_BOOK_IGNIS_VOLCANO(300, RelicMessages.SPELL_BOOK_IGNIS_VOLCANO, null, 1),
    WILL_CRYSTAL_RUBY(350, RelicMessages.WILL_CRYSTAL_RUBY, null),
    SPELL_BOOK_AER_SLASH(400, RelicMessages.SPELL_BOOK_AER_SLASH, null, 1),
    WILL_CRYSTAL_FLUORITE(450, RelicMessages.WILL_CRYSTAL_FLUORITE, null),
    SPELL_BOOK_TERRA_DRAIN(500, RelicMessages.SPELL_BOOK_TERRA_DRAIN, null, 1),
    WILL_CRYSTAL_ANDALUSITE(550, RelicMessages.WILL_CRYSTAL_ANDALUSITE, null),
    SPELL_BOOK_GRAND_NATURA(600, RelicMessages.SPELL_BOOK_GRAND_NATURA, null, 1),
    WILL_CRYSTAL_JADE(650, RelicMessages.WILL_CRYSTAL_JADE, null),
    ;

    companion object {
        private val idMap = values().map { it.id to it }.toMap()
        fun findById(id: Int) = idMap[id]
    }
}