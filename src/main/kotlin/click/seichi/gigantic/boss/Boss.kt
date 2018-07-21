package click.seichi.gigantic.boss

import click.seichi.gigantic.message.LocalizedText
import click.seichi.gigantic.relic.Relic

/**
 * @author tar0ss
 */
enum class Boss(
        val id: Int,
        val localizedName: LocalizedText,
        val maxHealth: Long,
        val dropRelicSet: Set<DropRelic>
) {
    ;

    data class DropRelic(
            val relic: Relic,
            val probability: Double
    )
}