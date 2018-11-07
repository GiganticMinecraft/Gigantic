package click.seichi.gigantic.relic

import click.seichi.gigantic.head.Head
import click.seichi.gigantic.message.LocalizedText
import org.bukkit.inventory.ItemStack
import java.util.*

/**
 * @author tar0ss
 */
enum class Relic(
        val id: Int,
        val localizedName: LocalizedText,
        val maxAmount: Int = Int.MAX_VALUE,
        val icon: ItemStack = Head.RUBY_JEWELLERY.toItemStack()
) {
    SPELL_BOOK_EXPLOSION(
            100,
            LocalizedText(
                    Locale.JAPANESE to "魔導書-エクスプロージョン-"
            ),
            1
    ),
    GOLDEN_APPLE(
            150,
            LocalizedText(
                    Locale.JAPANESE to "黄金の林檎"
            )
    ),
    SPELL_BOOK_AQUA_LINEA(
            200,
            LocalizedText(
                    Locale.JAPANESE to "水の魔導書-アクア・リネア-"
            ),
            1
    ),
    WILL_CRYSTAL_SAPPHIRE(
            250,
            LocalizedText(
                    Locale.JAPANESE to "水の意志結晶-サファイア-"
            )
    ),
    SPELL_BOOK_IGNIS_VOLCANO(
            300,
            LocalizedText(
                    Locale.JAPANESE to "火の魔導書-イグニス・ヴォルケーノ-"
            ),
            1
    ),
    WILL_CRYSTAL_RUBY(
            350,
            LocalizedText(
                    Locale.JAPANESE to "火の意志結晶-ルビー-"
            )
    ),
    SPELL_BOOK_AER_SLASH(
            400,
            LocalizedText(
                    Locale.JAPANESE to "空の魔導書-エアル・スラッシュ-"
            ),
            1
    ),
    WILL_CRYSTAL_FLUORITE(
            450,
            LocalizedText(
                    Locale.JAPANESE to "空の意志結晶-フローライト-"
            )
    ),
    SPELL_BOOK_TERRA_DRAIN(
            500,
            LocalizedText(
                    Locale.JAPANESE to "土の魔導書-テラ・ドレイン-"
            ),
            1
    ),
    WILL_CRYSTAL_ANDALUSITE(
            550,
            LocalizedText(
                    Locale.JAPANESE to "土の意志結晶-アンダルサイト-"
            )
    ),
    SPELL_BOOK_GRAND_NATURA(
            600,
            LocalizedText(
                    Locale.JAPANESE to "自然の魔導書-グランド・ナトラ-"
            ),
            1
    ),
    WILL_CRYSTAL_JADE(
            650,
            LocalizedText(
                    Locale.JAPANESE to "自然の意志結晶-ヒスイ-"
            )
    ),
//    // デバッグ用 1000番
//    SHELLS(1000, RelicRarity.NORMAL, RelicCategory.NONE, RelicMessages.SHELLS_NAME, RelicMessages.SHELLS_DESCRIPTION),
//    // ボスレリック用 1001番~1999番
//    PIG_TAILS(1001, RelicRarity.NORMAL, RelicCategory.BOSS_DROP, RelicMessages.PIG_TAILS_NAME, RelicMessages.PIG_TAILS_DESCRIPTION),
//    MOLE_FUR(1002, RelicRarity.NORMAL, RelicCategory.BOSS_DROP, RelicMessages.MOLE_FAR_NAME, RelicMessages.MOLE_FAR_DESCRIPTION),
//    FROG_OIL(1003, RelicRarity.NORMAL, RelicCategory.BOSS_DROP, RelicMessages.FROG_OIL_NAME, RelicMessages.FROG_OIL_DESCRIPTION),
//    EARTH_CORE(1004, RelicRarity.RARE, RelicCategory.BOSS_DROP, RelicMessages.EARTH_CORE_NAME, RelicMessages.EARTH_CORE_DESCRIPTION),
//    STEEL_INGOT(1005, RelicRarity.NORMAL, RelicCategory.BOSS_DROP, RelicMessages.STEEL_INGOT_NAME, RelicMessages.STEEL_INGOT_DESCRIPTION),
//    FEATHERS(1006, RelicRarity.NORMAL, RelicCategory.BOSS_DROP, RelicMessages.FEATHERS_NAME, RelicMessages.FEATHERS_DESCRIPTION),
//    BEAR_HAND(1007, RelicRarity.RARE, RelicCategory.BOSS_DROP, RelicMessages.BEAR_HAND_NAME, RelicMessages.BEAR_HAND_DESCRIPTION),
//    GRILLED_TURKEY(1008, RelicRarity.NORMAL, RelicCategory.BOSS_DROP, RelicMessages.GRILLED_TURKEY_NAME, RelicMessages.GRILLED_TURKEY_DESCRIPTION),
//    PINK_SPORES(1009, RelicRarity.NORMAL, RelicCategory.BOSS_DROP, RelicMessages.PINK_SPORES_NAME, RelicMessages.PINK_SPORES_DESCRIPTION),
//    A_PIECE_OF_RAINBOW(1010, RelicRarity.RARE, RelicCategory.BOSS_DROP, RelicMessages.A_PIECE_OF_RAINBOW_NAME, RelicMessages.A_PIECE_OF_RAINBOW_DESCRIPTION),
//    MERMAID_TEARS(1011, RelicRarity.RARE, RelicCategory.BOSS_DROP, RelicMessages.MERMAID_TEARS_NAME, RelicMessages.MERMAID_TEARS_DESCRIPTION),
//    MERMAN_SCALES(1012, RelicRarity.NORMAL, RelicCategory.BOSS_DROP, RelicMessages.FISHERMAN_SCALES_NAME, RelicMessages.FISHERMAN_SCALES_DESCRIPTION),
//    TRITON_TRIDENT(1013, RelicRarity.RARE, RelicCategory.BOSS_DROP, RelicMessages.TRITON_TRIDENT_NAME, RelicMessages.TRITON_TRIDENT_DESCRIPTION),
//    BISMARCK_IRON_KNUCKLE(1014, RelicRarity.NORMAL, RelicCategory.BOSS_DROP, RelicMessages.BISMARCK_IRON_KNUCKLE_NAME, RelicMessages.BISMARCK_IRON_KNUCKLE_DESCRIPTION),
//    COBBLE_STONE(1015, RelicRarity.RARE, RelicCategory.BOSS_DROP, RelicMessages.COBBLE_STONE_NAME, RelicMessages.COBBLE_STONE_DESCRIPTION),
//    LOVE_OF_MOTHER(1016, RelicRarity.NORMAL, RelicCategory.BOSS_DROP, RelicMessages.LOVE_OF_MOTHER_NAME, RelicMessages.LOVE_OF_MOTHER_DESCRIPTION),
    ;

    companion object {
        private val idMap = values().map { it.id to it }.toMap()
        fun findById(id: Int) = idMap[id]
    }
}