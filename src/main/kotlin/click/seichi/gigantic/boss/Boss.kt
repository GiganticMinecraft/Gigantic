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
        val maxHealth: Long,
        vararg dropRelic: DropRelic
) {
    PIG(0, Head.PIG, BossMessages.PIG_NAME, 100L, DropRelic(Relic.PIG_TAILS, 1.0)),
    MOLE(1, Head.MOLE, BossMessages.MOLE_NAME, 12000L, DropRelic(Relic.MOLE_FUR, 0.8)),
    FROG(2, Head.FROG, BossMessages.FROG_NAME, 12000L, DropRelic(Relic.FROG_OIL, 0.8)),
    THE_EARTH(3, Head.THE_EARTH, BossMessages.THE_EARTH_NAME, 7000000000L, DropRelic(Relic.EARTH_CORE, 0.4)),
    STEEL(4, Head.STEEL, BossMessages.STEEL_NAME, 100000L, DropRelic(Relic.STEEL_INGOT, 0.8)),
    BIRD(5, Head.BIRD, BossMessages.BIRD_NAME, 50L, DropRelic(Relic.FEATHERS, 1.0)),
    GUARDIAN_OF_THE_FOREST(6, Head.GUARDIAN_OF_THE_FOREST, BossMessages.GUARDIAN_OF_THE_FOREST_NAME, 4000L, DropRelic(Relic.BEAR_HAND, 0.3)),
    TURKEY(7, Head.TURKEY, BossMessages.TURKEY_NAME, 450L, DropRelic(Relic.GRILLED_TURKEY, 0.6)),
    PINK_MUSHROOM(8, Head.PINK_MUSHROOM, BossMessages.PINK_MUSHROOM_NAME, 200L, DropRelic(Relic.PINK_SPORES, 0.9)),
    RAINBOW(9, Head.RAINBOW, BossMessages.RAINBOW_NAME, 123456L, DropRelic(Relic.A_PIECE_OF_RAINBOW, 0.28)),
    MERMAID(10, Head.MERMAID, BossMessages.MERMAID_NAME, 136000L, DropRelic(Relic.MERMAID_TEARS, 0.33)),
    MERMAN(11, Head.MERMAN, BossMessages.MERMAN_NAME, 182000L, DropRelic(Relic.MERMAN_SCALES, 0.88)),
    TRITON(12, Head.TRITON, BossMessages.TRITON_NAME, 132000000L, DropRelic(Relic.TRITON_TRIDENT, 0.42)),
    ;

    val dropRelicSet = dropRelic.toSet()

    data class DropRelic(
            val relic: Relic,
            val probability: Double
    )

}