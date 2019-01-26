package click.seichi.gigantic.event.events

import click.seichi.gigantic.event.CustomEvent
import click.seichi.gigantic.relic.WillRelic
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class RelicGenerateEvent(
        val player: Player,
        val generated: WillRelic
) : CustomEvent() {
}