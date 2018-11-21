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
        private val localizedLore: List<LocalizedText>?,
        val maxAmount: Long = Long.MAX_VALUE,
        private val icon: ItemStack = Head.RUBY_JEWELLERY.toItemStack()
) {
    PIGS_FEATHER(0, RelicMessages.PIGS_FEATHER, RelicMessages.PIGS_FEATHER_LORE, icon = ItemStack(Material.FEATHER)),
    BLUE_BLAZE_POWDER(1, RelicMessages.BLUE_BLAZE_POWDER, RelicMessages.BLUE_BLAZE_POWDER_LORE, icon = ItemStack(Material.BLAZE_POWDER)),
    CHICKEN_KING_CROWN(2, RelicMessages.CHICKEN_KING_CROWN, RelicMessages.CHICKEN_KING_CROWN_LORE, icon = Head.CHICKEN_KING_CROWN.toItemStack()),
    WITHER_SKELETON_SKULL(3, RelicMessages.WITHER_SKELETON_SKULL, null, icon = ItemStack(Material.WITHER_SKELETON_SKULL)),
    MANA_STONE(4, RelicMessages.MANA_STONE, null, icon = ItemStack(Material.NETHER_STAR)),
    ROTTEN_FLESH(4, RelicMessages.ROTTEN_FLESH, null, icon = ItemStack(Material.ROTTEN_FLESH)),
    SPELL_BOOK_EXPLOSION(100, RelicMessages.SPELL_BOOK_EXPLOSION, null, 1),
    GOLDEN_APPLE(150, RelicMessages.GOLDEN_APPLE, null),
    WILL_CRYSTAL_SAPPHIRE(200, RelicMessages.WILL_CRYSTAL_SAPPHIRE, null),
    WILL_CRYSTAL_RUBY(300, RelicMessages.WILL_CRYSTAL_RUBY, null),
    WILL_CRYSTAL_FLUORITE(400, RelicMessages.WILL_CRYSTAL_FLUORITE, null),
    WILL_CRYSTAL_ANDALUSITE(500, RelicMessages.WILL_CRYSTAL_ANDALUSITE, null),
    WILL_CRYSTAL_JADE(600, RelicMessages.WILL_CRYSTAL_JADE, null),
    ;

    companion object {
        fun getDroppedList(player: Player): List<Relic> {
            return values().filter { it.has(player) }
        }
    }

    fun getName(locale: Locale) = localizedName.asSafety(locale)

    fun getLore(locale: Locale) = localizedLore?.map { it.asSafety(locale) }

    fun getIcon() = icon.clone()

    fun has(player: Player) = getDroppedNum(player) > 0

    fun getDroppedNum(player: Player) = Keys.RELIC_MAP[this]?.let { player.getOrPut(it) } ?: 0L

    fun dropTo(player: Player) {
        player.transform(Keys.RELIC_MAP[this] ?: return) { if (it < maxAmount) it + 1 else maxAmount }
    }

}