package click.seichi.gigantic.relic

import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.messages.RelicMessages

/**
 * @author tar0ss
 */
enum class Relic(
        val id: Int,
        val rarity: RelicRarity,
        val category: RelicCategory,
        val localizedName: LocalizedText,
        val description: List<LocalizedText>
) {
    // デバッグ用 1000番
    SHELLS(1000, RelicRarity.NORMAL, RelicCategory.NONE, RelicMessages.SHELLS_NAME, RelicMessages.SHELLS_DESCRIPTION),
    // ボスレリック用 1001番~1999番
    PIG_TAILS(1001, RelicRarity.NORMAL, RelicCategory.BOSS_DROP, RelicMessages.PIG_TAILS_NAME, RelicMessages.PIG_TAILS_DESCRIPTION),
    MOLE_FUR(1002, RelicRarity.NORMAL, RelicCategory.BOSS_DROP, RelicMessages.MOLE_FAR_NAME, RelicMessages.MOLE_FAR_DESCRIPTION),
    FROG_OIL(1003, RelicRarity.NORMAL, RelicCategory.BOSS_DROP, RelicMessages.FROG_OIL_NAME, RelicMessages.FROG_OIL_DESCRIPTION),
    EARTH_CORE(1004, RelicRarity.RARE, RelicCategory.BOSS_DROP, RelicMessages.EARTH_CORE_NAME, RelicMessages.EARTH_CORE_DESCRIPTION),
    STEEL_INGOT(1005, RelicRarity.NORMAL, RelicCategory.BOSS_DROP, RelicMessages.STEEL_INGOT_NAME, RelicMessages.STEEL_INGOT_DESCRIPTION),
    FEATHERS(1006, RelicRarity.NORMAL, RelicCategory.BOSS_DROP, RelicMessages.FEATHERS_NAME, RelicMessages.FEATHERS_DESCRIPTION),
    BEAR_HAND(1007, RelicRarity.RARE, RelicCategory.BOSS_DROP, RelicMessages.BEAR_HAND_NAME, RelicMessages.BEAR_HAND_DESCRIPTION),
    GRILLED_TURKEY(1008, RelicRarity.NORMAL, RelicCategory.BOSS_DROP, RelicMessages.GRILLED_TURKEY_NAME, RelicMessages.GRILLED_TURKEY_DESCRIPTION),
    PINK_SPORES(1009, RelicRarity.NORMAL, RelicCategory.BOSS_DROP, RelicMessages.PINK_SPORES_NAME, RelicMessages.PINK_SPORES_DESCRIPTION),
    A_PIECE_OF_RAINBOW(1010, RelicRarity.RARE, RelicCategory.BOSS_DROP, RelicMessages.A_PIECE_OF_RAINBOW_NAME, RelicMessages.A_PIECE_OF_RAINBOW_DESCRIPTION),
    MERMAID_TEARS(1011, RelicRarity.RARE, RelicCategory.BOSS_DROP, RelicMessages.MERMAID_TEARS_NAME, RelicMessages.MERMAID_TEARS_DESCRIPTION),
    MERMAN_SCALES(1012, RelicRarity.NORMAL, RelicCategory.BOSS_DROP, RelicMessages.FISHERMAN_SCALES_NAME, RelicMessages.FISHERMAN_SCALES_DESCRIPTION),
    TRITON_TRIDENT(1013, RelicRarity.RARE, RelicCategory.BOSS_DROP, RelicMessages.TRITON_TRIDENT_NAME, RelicMessages.TRITON_TRIDENT_DESCRIPTION),
    BISMARCK_IRON_KNUCKLE(1014, RelicRarity.NORMAL, RelicCategory.BOSS_DROP, RelicMessages.BISMARCK_IRON_KNUCKLE_NAME, RelicMessages.BISMARCK_IRON_KNUCKLE_DESCRIPTION),
    COBBLE_STONE(1015, RelicRarity.RARE, RelicCategory.BOSS_DROP, RelicMessages.COBBLE_STONE_NAME, RelicMessages.COBBLE_STONE_DESCRIPTION),
    LOVE_OF_MOTHER(1016, RelicRarity.NORMAL, RelicCategory.BOSS_DROP, RelicMessages.LOVE_OF_MOTHER_NAME, RelicMessages.LOVE_OF_MOTHER_DESCRIPTION),
    ;

    companion object {
        private val idMap = values().map { it.id to it }.toMap()
        fun findById(id: Int) = idMap[id]
    }
}