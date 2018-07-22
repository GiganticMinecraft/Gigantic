package click.seichi.gigantic.relic

import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.messages.RelicMessages

/**
 * @author tar0ss
 */
enum class Relic(
        val id: Int,
        val rarity: RelicRarity,
        val localizedName: LocalizedText,
        val description: List<LocalizedText>
) {
    SHELLS(1000, RelicRarity.NORMAL, RelicMessages.SHELLS_NAME, RelicMessages.SHELLS_DESCRIPTION),
    PIG_TAILS(1001, RelicRarity.NORMAL, RelicMessages.PIG_TAILS_NAME, RelicMessages.PIG_TAILS_DESCRIPTION),
    MOLE_FUR(1002, RelicRarity.NORMAL, RelicMessages.MOLE_FAR_NAME, RelicMessages.MOLE_FAR_DESCRIPTION),
    FROG_OIL(1003, RelicRarity.NORMAL, RelicMessages.FROG_OIL_NAME, RelicMessages.FROG_OIL_DESCRIPTION),
    EARTH_CORE(1004, RelicRarity.RARE, RelicMessages.EARTH_CORE_NAME, RelicMessages.EARTH_CORE_DESCRIPTION),
    STEEL_INGOT(1005, RelicRarity.NORMAL, RelicMessages.STEEL_INGOT_NAME, RelicMessages.STEEL_INGOT_DESCRIPTION),
    FEATHERS(1006, RelicRarity.NORMAL, RelicMessages.FEATHERS_NAME, RelicMessages.FEATHERS_DESCRIPTION),
    BEAR_HAND(1007, RelicRarity.RARE, RelicMessages.BEAR_HAND_NAME, RelicMessages.BEAR_HAND_DESCRIPTION),
    GRILLED_TURKEY(1008, RelicRarity.NORMAL, RelicMessages.GRILLED_TURKEY_NAME, RelicMessages.GRILLED_TURKEY_DESCRIPTION),
    PINK_SPORES(1009, RelicRarity.NORMAL, RelicMessages.PINK_SPORES_NAME, RelicMessages.PINK_SPORES_DESCRIPTION),
    A_PIECE_OF_RAINBOW(1010, RelicRarity.RARE, RelicMessages.A_PIECE_OF_RAINBOW_NAME, RelicMessages.A_PIECE_OF_RAINBOW_DESCRIPTION)
    ;

    companion object {
        private val idMap = values().map { it.id to it }.toMap()
        fun findById(id: Int) = idMap[id]
    }
}