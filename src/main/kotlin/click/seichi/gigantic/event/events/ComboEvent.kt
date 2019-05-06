package click.seichi.gigantic.event.events

import click.seichi.gigantic.event.CustomEvent
import org.bukkit.entity.Player

/**
 * @author tar0ss
 */
class ComboEvent(
        val combo: Long,
        val player: Player
) : CustomEvent()