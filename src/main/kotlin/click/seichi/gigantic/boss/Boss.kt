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
    PIG(0, Head.PIG, BossMessages.PIG_NAME, 100.0, DropRelic(Relic.PIG_TAILS, 1.0)),
    MOLE(1, Head.MOLE, BossMessages.MOLE_NAME, 12000.0, DropRelic(Relic.MOLE_FUR, 0.8)),
    FROG(2, Head.FROG, BossMessages.FROG_NAME, 12000.0, DropRelic(Relic.FROG_OIL, 0.8)),
    THE_EARTH(3, Head.THE_EARTH, BossMessages.THE_EARTH_NAME, 7000000000.0, DropRelic(Relic.EARTH_CORE, 0.4)),
    STEEL(4, Head.STEEL, BossMessages.STEEL_NAME, 100000.0, DropRelic(Relic.STEEL_INGOT, 0.8)),
    BIRD(5, Head.BIRD, BossMessages.BIRD_NAME, 50.0, DropRelic(Relic.FEATHERS, 1.0)),
    GUARDIAN_OF_THE_FOREST(6, Head.GUARDIAN_OF_THE_FOREST, BossMessages.GUARDIAN_OF_THE_FOREST_NAME, 4000.0, DropRelic(Relic.BEAR_HAND, 0.3)),
    TURKEY(7, Head.TURKEY, BossMessages.TURKEY_NAME, 450.0, DropRelic(Relic.GRILLED_TURKEY, 0.6)),
    PINK_MUSHROOM(8, Head.PINK_MUSHROOM, BossMessages.PINK_MUSHROOM_NAME, 200.0, DropRelic(Relic.PINK_SPORES, 0.9)),
    RAINBOW(9, Head.RAINBOW, BossMessages.RAINBOW_NAME, 123456.0, DropRelic(Relic.A_PIECE_OF_RAINBOW, 0.28))
    ;

    val dropRelicSet = dropRelic.toSet()

    companion object {
        private val idMap = values().map { it.id to it }.toMap()

        fun findById(id: Int) = idMap[id]
    }

    data class DropRelic(
            val relic: Relic,
            val probability: Double
    )

}