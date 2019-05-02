package click.seichi.gigantic.event.events

import click.seichi.gigantic.event.CustomEvent
import click.seichi.gigantic.relic.Relic
import click.seichi.gigantic.will.Will
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class RelicGenerateEvent(
        val player: Player,
        val generated: Relic,
        val useWill: Will,
        val useAmount: Long
) : CustomEvent() {
}