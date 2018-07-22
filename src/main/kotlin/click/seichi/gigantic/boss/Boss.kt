package click.seichi.gigantic.boss

import click.seichi.gigantic.head.Head
import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.message.messages.BossMessages
import click.seichi.gigantic.relic.Relic

/**
 * @author tar0ss
 */
enum class Boss(
        val id: Int,
        val head: Head,
        val localizedName: LocalizedText,
        val maxHealth: Double,
        vararg dropRelic: DropRelic
) {
    PIG(0, Head.PIG, BossMessages.PIG_NAME, 100.0, DropRelic(Relic.PIG_TAILS, 0.1)),
    MOLE(1, Head.MOLE, BossMessages.MOLE_NAME, 12000.0, DropRelic(Relic.PIG_TAILS, 0.1)),
    FROG(2, Head.FROG, BossMessages.FROG_NAME, 12000.0, DropRelic(Relic.FROG_OIL, 0.1)),
    ;

    val dropRelicSet = dropRelic.toSet()

    data class DropRelic(
            val relic: Relic,
            val probability: Double
    )
}