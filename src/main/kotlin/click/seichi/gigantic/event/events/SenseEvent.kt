package click.seichi.gigantic.event.events

import click.seichi.gigantic.event.CustomEvent
import click.seichi.gigantic.will.Will
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class SenseEvent(
        val will: Will,
        val player: Player,
        val amount: Long
) : CustomEvent() {
}