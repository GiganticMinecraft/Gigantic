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
    ;

}