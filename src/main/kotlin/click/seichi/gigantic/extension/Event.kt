package click.seichi.gigantic.extension

import click.seichi.gigantic.spirit.SpiritType
import org.bukkit.event.Event

/**
 * @author unicroak
 * @author tar0ss
 */
fun Event.summonSpirit() = SpiritType.values().forEach { it.summon(this) }
