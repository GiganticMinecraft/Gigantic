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
    ;

    companion object {
        private val idMap = values().map { it.id to it }.toMap()
        fun findById(id: Int) = idMap[id]
    }
}