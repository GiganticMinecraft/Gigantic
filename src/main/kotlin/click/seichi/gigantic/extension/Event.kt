package click.seichi.gigantic.extension

import click.seichi.gigantic.menu.TriggerMenuType
import click.seichi.gigantic.spirit.SpiritType
import org.bukkit.event.Event

/**
 * @author unicroak
 * @author tar0ss
 */
fun Event.summonSpirit() = SpiritType.values().forEach { it.summon(this) }

fun Event.openMenu() = TriggerMenuType.values().forEach { it.openIfNeeded(this) }