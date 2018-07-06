package click.seichi.gigantic.spirit.summoncase

import org.bukkit.event.Event

/**
 * @author unicroak
 */
class SimpleSummonCase<T : Event>(clazz: Class<T>, override val summoning: (T) -> Unit) : SummonCase<T>(clazz)